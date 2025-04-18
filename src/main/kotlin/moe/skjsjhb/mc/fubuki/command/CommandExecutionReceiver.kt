package moe.skjsjhb.mc.fubuki.command

import moe.skjsjhb.mc.fubuki.entity.toBukkit
import moe.skjsjhb.mc.fubuki.server.toFubuki
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.ServerCommandSource
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.server.ServerCommandEvent

object CommandExecutionReceiver {
    fun onGeneralCommand(source: ServerCommandSource, cmd: String) {
        val fs = source.server.toFubuki()

        val ev = ServerCommandEvent(fs.consoleSender, cmd)
        Bukkit.getPluginManager().callEvent(ev)

        if (!ev.isCancelled) {
            // Proxy such invocation
            val parsed = source.server.commandManager.dispatcher.parse(ev.command, source)
            source.server.commandManager.execute(parsed, ev.command)
        }
    }

    fun onClientCommand(
        server: MinecraftServer,
        source: ServerCommandSource,
        cmd: String,
        delegateCallback: Runnable // Executed when there is no corresponding plugin command
    ) {
        source.player?.let {
            val ev = PlayerCommandPreprocessEvent(it.toBukkit(), cmd)
            // This method is already called on the main thread
            Bukkit.getPluginManager().callEvent(ev)
            if (!ev.isCancelled) {
                if (ev.message == cmd) {
                    // Run command the native way to preserve signatures
                    delegateCallback.run()
                    return
                }

                // Command changed, drop signatures and run it in raw format
                val parsed = server.commandManager.dispatcher.parse(ev.message, source)
                server.commandManager.execute(parsed, ev.message)
            }
        } ?: delegateCallback.run() // Fallback to the handler
    }
}