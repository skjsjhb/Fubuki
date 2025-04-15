package moe.skjsjhb.mc.fubuki.math

import net.minecraft.util.math.Vec3d
import org.bukkit.util.Vector

fun Vec3d.toBukkitVector(v: Vector) {
    v.x = x
    v.y = y
    v.z = z
}

fun Vec3d.toBukkitVector(): Vector = Vector(x, y, z)

fun Vector.toMojangVec3d(): Vec3d = Vec3d(x, y, z)