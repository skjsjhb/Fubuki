package moe.skjsjhb.mc.fubuki.server

import org.bukkit.GameMode

object GameModeCaster {
    fun toBukkit(gameMode: net.minecraft.world.GameMode): GameMode =
        when (gameMode) {
            net.minecraft.world.GameMode.SURVIVAL -> GameMode.SURVIVAL
            net.minecraft.world.GameMode.CREATIVE -> GameMode.CREATIVE
            net.minecraft.world.GameMode.ADVENTURE -> GameMode.ADVENTURE
            net.minecraft.world.GameMode.SPECTATOR -> GameMode.SPECTATOR
        }

    fun toMojang(gameMode: GameMode): net.minecraft.world.GameMode =
        when (gameMode) {
            GameMode.SURVIVAL -> net.minecraft.world.GameMode.SURVIVAL
            GameMode.CREATIVE -> net.minecraft.world.GameMode.CREATIVE
            GameMode.ADVENTURE -> net.minecraft.world.GameMode.ADVENTURE
            GameMode.SPECTATOR -> net.minecraft.world.GameMode.SPECTATOR
        }
}