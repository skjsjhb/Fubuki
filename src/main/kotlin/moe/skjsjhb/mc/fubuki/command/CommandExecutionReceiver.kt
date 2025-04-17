package moe.skjsjhb.mc.fubuki.command

import moe.skjsjhb.mc.fubuki.entity.toBukkit
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.ServerCommandSource
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerCommandPreprocessEvent

object CommandExecutionReceiver {
    fun onClientCommand(
        server: MinecraftServer,
        source: ServerCommandSource,
        cmd: String,
        delegateCallback: Runnable, // Executed when there is no corresponding plugin command
        signatureFixupCallback: Runnable // Executed to sign the arguments in order to update the chat signature
    ) {
        source.player?.let {
            val ev = PlayerCommandPreprocessEvent(it.toBukkit(), cmd)
            // This method is already called on the main thread
            Bukkit.getPluginManager().callEvent(ev)
            if (!ev.isCancelled) {
                // Run command
                // TODO run command in Bukkit world

                // The following code is only executed when there is no corresponding plugin command
                if (ev.message == cmd) {
                    // Run command the native way to preserve signatures
                    delegateCallback.run()
                } else {
                    // Command changed, drop signatures and run it in raw format
                    signatureFixupCallback.run()
                    val parsed = server.commandManager.dispatcher.parse(ev.message, source)
                    server.commandManager.execute(parsed, ev.message)
                }
            } else {
                signatureFixupCallback.run()
            }
        } ?: delegateCallback.run() // Fallback to the handler
    }
}