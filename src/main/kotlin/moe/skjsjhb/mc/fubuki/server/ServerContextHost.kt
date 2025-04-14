package moe.skjsjhb.mc.fubuki.server

import moe.skjsjhb.mc.fubuki.schedule.FubukiTask
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.server.MinecraftServer
import net.minecraft.server.dedicated.MinecraftDedicatedServer
import org.bukkit.Bukkit
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

object ServerContextHost {
    private val logger = LoggerFactory.getLogger("Fubuki")
    private val fubukiServerMap = ConcurrentHashMap<MinecraftServer, FubukiServer>()
    private var serverStarted = false

    fun getFubukiServer(sv: MinecraftServer): FubukiServer = fubukiServerMap.getValue(sv)

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

            // FIXME don't wait forever
            FubukiTask.waitAsyncTasks(Long.MAX_VALUE, TimeUnit.DAYS)
        }

        ServerLifecycleEvents.SERVER_STOPPED.register { sv ->
            fubukiServerMap.remove(sv)
        }

        ServerLifecycleEvents.SERVER_STARTED.register { sv ->
            fubukiServerMap[sv]?.pluginLifecycleManager?.run {
                onPostWorld()
                onStarted()
            }
        }
    }
}