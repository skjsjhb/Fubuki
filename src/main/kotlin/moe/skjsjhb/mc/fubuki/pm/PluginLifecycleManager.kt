package moe.skjsjhb.mc.fubuki.pm

import moe.skjsjhb.mc.fubuki.util.Slf4jBridgedLogger
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginLoadOrder
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import org.slf4j.LoggerFactory
import java.io.File

class PluginLifecycleManager(private val pluginManager: PluginManager) {
    private val logger = LoggerFactory.getLogger("Fubuki")
    private val plugins = mutableMapOf<String, Plugin>()
    private val pluginLoadOrders = HashMap<String, PluginLoadOrder>()

    fun loadPlugins() {
        plugins.clear()

        pluginManager.registerInterface(JavaPluginLoader::class.java)
        val ps = pluginManager.loadPlugins(File("plugins"))
        plugins.putAll(ps.associateBy { it.name })
        sortPluginLoadOrder()

        logger.info("Found plugins:")
        ps.forEach {
            logger.info("- {} ({})", it.name, it.description.version)
            patchPluginLogger(it)
        }

        plugins.values
            .filter { pluginLoadOrders[it.name] == PluginLoadOrder.STARTUP }
            .forEach {
                logger.info("Loading plugin {} (STARTUP)", it.name)
                it.onLoad()
            }
    }

    fun onPostWorld() {
        plugins.values
            .filter { pluginLoadOrders[it.name] == PluginLoadOrder.POSTWORLD }
            .forEach {
                logger.info("Loading plugin {} (POSTWORLD)", it.name)
                it.onLoad()
            }
    }

    fun onStarted() {
        plugins.values.forEach { pluginManager.enablePlugin(it) }
    }

    fun onShutdown() {
        pluginManager.disablePlugins()
    }

    private fun patchPluginLogger(p: Plugin) {
        if (p is JavaPlugin) {
            // Redirect logs to the Fabric SLF4J logger
            p.logger = Slf4jBridgedLogger("Fubuki/" + (p.description.prefix ?: p.name))
        }
    }

    private fun sortPluginLoadOrder() {
        pluginLoadOrders.clear()
        plugins.values.forEach { updatePluginLoadOrder(it) }
    }

    private fun updatePluginLoadOrder(p: Plugin): PluginLoadOrder {
        pluginLoadOrders[p.name]?.let { return it }

        // Set an initial value to prevent recursive calls
        pluginLoadOrders[p.name] = p.description.load

        val loadAtStartup = p.description.load == PluginLoadOrder.STARTUP &&
                p.description.depend.plus(p.description.softDepend)
                    .mapNotNull { getRealPlugin(it) }
                    .all { updatePluginLoadOrder(it) == PluginLoadOrder.STARTUP }

        val newOrder = if (loadAtStartup) PluginLoadOrder.STARTUP else PluginLoadOrder.POSTWORLD
        pluginLoadOrders[p.name] = newOrder

        return newOrder
    }

    /**
     * Gets the plugin or the one provides it by the name.
     */
    private fun getRealPlugin(name: String): Plugin? =
        plugins[name] ?: plugins.values.find { it.description.provides.contains(name) }
}