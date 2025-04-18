package moe.skjsjhb.mc.fubuki.server

import moe.skjsjhb.mc.fubuki.schedule.FubukiTask
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.server.dedicated.MinecraftDedicatedServer
import org.bukkit.Bukkit
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

object ServerInit {
    private val logger = LoggerFactory.getLogger("Fubuki")
    private var serverStarted = false

    fun init() {
        ServerLifecycleEvents.SERVER_STARTING.register {
            if (serverStarted) {
                logger.warn("Redefining server instance is not currently supported.")
                logger.warn("The newly started server (aka. ${it}) will not have plugin-loading capabilities.")
                return@register
            }

            if (it !is MinecraftDedicatedServer) {
                logger.warn("Only dedicated server is supported.")
                return@register
            }

            serverStarted = true

            val fsv = FubukiServer(it)

            Bukkit.setServer(fsv)

            fsv.pluginLifecycleManager.run {
                loadPlugins()
                onStartup()
            }
        }

        ServerLifecycleEvents.SERVER_STOPPING.register {
            it.toFubuki().pluginLifecycleManager.onShutdown()

            // FIXME don't wait forever
            FubukiTask.waitAsyncTasks(Long.MAX_VALUE, TimeUnit.DAYS)
        }

        ServerLifecycleEvents.SERVER_STARTED.register {
            val fs = it.toFubuki()

            fs.pluginLifecycleManager.run {
                onPostWorld()
                onStarted()
            }

            fs.postSetup()
        }
    }
}