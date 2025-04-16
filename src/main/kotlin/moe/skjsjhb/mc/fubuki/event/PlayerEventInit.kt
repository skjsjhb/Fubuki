package moe.skjsjhb.mc.fubuki.event

import moe.skjsjhb.mc.fubuki.entity.toBukkit
import moe.skjsjhb.mc.fubuki.server.toFubuki
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * Registries events that can be fired directly from Fabric API.
 */
object PlayerEventInit {
    fun init() {
        ServerPlayConnectionEvents.JOIN.register { handler, _, server ->
            server.toFubuki().runOnMainThread {
                Bukkit.getPluginManager().callEvent(
                    PlayerJoinEvent(handler.player.toBukkit(), null)
                )
            }
        }

        ServerPlayConnectionEvents.DISCONNECT.register { handler, server ->
            server.toFubuki().runOnMainThread {
                Bukkit.getPluginManager().callEvent(
                    PlayerQuitEvent(handler.player.toBukkit(), null)
                )
            }
        }
    }
}