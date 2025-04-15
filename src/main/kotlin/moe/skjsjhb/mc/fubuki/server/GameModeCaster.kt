package moe.skjsjhb.mc.fubuki.server

import org.bukkit.GameMode

fun net.minecraft.world.GameMode.toBukkit(): GameMode =
    when (this) {
        net.minecraft.world.GameMode.SURVIVAL -> GameMode.SURVIVAL
        net.minecraft.world.GameMode.CREATIVE -> GameMode.CREATIVE
        net.minecraft.world.GameMode.ADVENTURE -> GameMode.ADVENTURE
        net.minecraft.world.GameMode.SPECTATOR -> GameMode.SPECTATOR
    }

fun GameMode.toMojang(): net.minecraft.world.GameMode =
    when (this) {
        GameMode.SURVIVAL -> net.minecraft.world.GameMode.SURVIVAL
        GameMode.CREATIVE -> net.minecraft.world.GameMode.CREATIVE
        GameMode.ADVENTURE -> net.minecraft.world.GameMode.ADVENTURE
        GameMode.SPECTATOR -> net.minecraft.world.GameMode.SPECTATOR
    }