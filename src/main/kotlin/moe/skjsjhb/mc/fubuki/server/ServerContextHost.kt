package moe.skjsjhb.mc.fubuki.server

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.server.MinecraftServer
import java.util.concurrent.ConcurrentHashMap

object ServerContextHost {
    private val fubukiServerMap = ConcurrentHashMap<MinecraftServer, FubukiServer>()

    fun init() {
        ServerLifecycleEvents.SERVER_STARTING.register {
            val fs = FubukiServer(it)
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