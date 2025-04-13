package moe.skjsjhb.mc.fubuki

import moe.skjsjhb.mc.fubuki.pm.PluginLifecycleManager
import moe.skjsjhb.mc.fubuki.server.FubukiServer
import moe.skjsjhb.mc.fubuki.server.ServerMixinReceivers
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import org.slf4j.LoggerFactory

/**
 * Main mod entry.
 */
object Fubuki : ModInitializer {
    private val logger = LoggerFactory.getLogger("Fubuki")

    override fun onInitialize() {
        logger.info("This is Fubuki, a Bukkit API translator for Fabric.")

        ServerLifecycleEvents.SERVER_STARTING.register {
            val fs = FubukiServer(it)
            val pm = PluginLifecycleManager(fs.pluginManager)

            pm.loadPlugins()

            ServerMixinReceivers.postWorldFuture.whenComplete { sv, _ ->
                if (sv == it) {
                    pm.onPostWorld()
                }
            }

            ServerLifecycleEvents.SERVER_STARTED.register { sv ->
                if (sv == it) {
                    pm.onStarted()
                }
            }

            ServerLifecycleEvents.SERVER_STOPPING.register { sv ->
                if (sv == it) {
                    pm.onShutdown()
                }
            }
        }
    }
}