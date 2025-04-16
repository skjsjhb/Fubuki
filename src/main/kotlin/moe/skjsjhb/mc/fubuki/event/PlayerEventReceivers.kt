package moe.skjsjhb.mc.fubuki.event

import moe.skjsjhb.mc.fubuki.entity.toBukkit
import moe.skjsjhb.mc.fubuki.network.HostnameContainer
import moe.skjsjhb.mc.fubuki.server.toFubuki
import net.minecraft.network.ClientConnection
import net.minecraft.server.PlayerManager
import net.minecraft.server.network.ServerPlayerEntity
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerLoginEvent
import java.net.InetSocketAddress

object PlayerEventReceivers {
    fun onPlayerLogin(conn: ClientConnection, pm: PlayerManager, player: ServerPlayerEntity) {
        pm.server.toFubuki().runOnMainThread {
            Bukkit.getPluginManager().callEvent(
                PlayerLoginEvent(
                    player.toBukkit(),
                    (conn as HostnameContainer).`fubuki$getHostname`(),
                    (conn.address as InetSocketAddress).address
                )
            )
        }
    }
}