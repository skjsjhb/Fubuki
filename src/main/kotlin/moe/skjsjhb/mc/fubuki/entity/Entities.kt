package moe.skjsjhb.mc.fubuki.entity

import moe.skjsjhb.mc.fubuki.interop.asBukkit
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.server.network.ServerPlayerEntity

fun Entity.toBukkit(): FubukiEntity = asBukkit { FubukiEntity(it) }
fun LivingEntity.toBukkit(): FubukiLivingEntity = asBukkit { FubukiLivingEntity(it) }
fun ServerPlayerEntity.toBukkit(): FubukiPlayer = asBukkit { FubukiPlayer(it) }