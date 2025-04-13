package moe.skjsjhb.mc.fubuki

import moe.skjsjhb.mc.fubuki.server.FubukiServer
import moe.skjsjhb.mc.fubuki.server.ServerMixinReceivers
import moe.skjsjhb.mc.fubuki.util.Slf4jBridgedLogger
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import org.bukkit.plugin.PluginLoadOrder
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import org.slf4j.LoggerFactory
import java.io.File

/**
 * Main mod entry.
 */
object Fubuki : ModInitializer {
    private val logger = LoggerFactory.getLogger("Fubuki")

    override fun onInitialize() {
        logger.info("This is Fubuki, a Bukkit API translator for Fabric.")

        ServerLifecycleEvents.SERVER_STARTING.register {
            val fs = FubukiServer(it)
            fs.pluginManager.registerInterface(JavaPluginLoader::class.java)
            val plugins = fs.pluginManager.loadPlugins(File("plugins"))


            logger.info("Found plugins:")
            plugins.forEach {
                logger.info("- {} ({})", it.name, it.description.version)

                if (it is JavaPlugin) {
                    // Redirect logs to the Fabric SLF4J logger
                    it.logger = Slf4jBridgedLogger("Fubuki/" + (it.description.prefix ?: it.name))
                }
            }

            fun canLoadAtStartup(name: String): Boolean {
                val p = fs.pluginManager.getPlugin(name) ?: return true // Soft depend

                return p.description.load == PluginLoadOrder.STARTUP
                        && p.description.depend.all { canLoadAtStartup(it) }
                        && p.description.softDepend.all { canLoadAtStartup(it) }
            }

            val pluginsMap = plugins.groupBy {
                if (canLoadAtStartup(it.name)) PluginLoadOrder.STARTUP else PluginLoadOrder.POSTWORLD
            }

            pluginsMap[PluginLoadOrder.STARTUP]?.forEach {
                logger.info("Loading plugin {} (STARTUP)", it.name)
                it.onLoad()
            }

            ServerMixinReceivers.postWorldFuture.whenComplete { _, _ ->
                pluginsMap[PluginLoadOrder.POSTWORLD]?.forEach {
                    logger.info("Loading plugin {} (POSTWORLD)", it.name)
                    it.onLoad()
                }
            }

            ServerLifecycleEvents.SERVER_STARTED.register {
                plugins.forEach {
                    fs.pluginManager.enablePlugin(it)
                }
            }

            ServerLifecycleEvents.SERVER_STOPPING.register {
                // TODO disable plugins with plugin manager
                // fs.pluginManager.disablePlugins()
                plugins.forEach {
                    if (fs.pluginManager.isPluginEnabled(it)) {
                        it.onDisable()
                    }
                }
            }
        }
    }
}