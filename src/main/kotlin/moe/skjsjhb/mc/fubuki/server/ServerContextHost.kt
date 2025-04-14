package moe.skjsjhb.mc.fubuki.server

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.server.MinecraftServer
import org.bukkit.Bukkit
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

object ServerContextHost {
    private val logger = LoggerFactory.getLogger("Fubuki")
    private val fubukiServerMap = ConcurrentHashMap<MinecraftServer, FubukiServer>()
    private var serverStarted = false

    fun init() {
        ServerLifecycleEvents.SERVER_STARTING.register {
            if (serverStarted) {
                logger.warn("Redefining server instance is not currently supported.")
                logger.warn("The newly started server (aka. ${it}) will not have plugin-loading capabilities.")
                return@register
            }

            serverStarted = true
            val fs = FubukiServer(it)
            Bukkit.setServer(fs)

            fs.pluginLifecycleManager.run {
                loadPlugins()
                onStartup()
            }
            fubukiServerMap[it] = fs
        }

        ServerLifecycleEvents.SERVER_STOPPING.register { sv ->
            fubukiServerMap[sv]?.pluginLifecycleManager?.onShutdown()
        }

        ServerLifecycleEvents.SERVER_STARTED.register { sv ->
            fubukiServerMap[sv]?.pluginLifecycleManager?.run {
                onPostWorld()
                onStarted()
            }
        }
    }
}