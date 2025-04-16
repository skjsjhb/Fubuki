package moe.skjsjhb.mc.fubuki.server

import moe.skjsjhb.mc.fubuki.interop.assumeBukkit
import net.minecraft.server.MinecraftServer

fun MinecraftServer.toFubuki(): FubukiServer = assumeBukkit()