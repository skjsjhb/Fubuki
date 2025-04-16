package moe.skjsjhb.mc.fubuki.event

import moe.skjsjhb.mc.fubuki.entity.toBukkit
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent

/**
 * Registries events that can be fired directly from Fabric API.
 */
object PlayerEventInit {
    fun init() {
        ServerPlayConnectionEvents.JOIN.register { handler, _, _ ->
            Bukkit.getPluginManager().callEvent(
                PlayerJoinEvent(handler.player.toBukkit(), null)
            )
        }
    }
}