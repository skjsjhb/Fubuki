package moe.skjsjhb.mc.fubuki.interop

import net.minecraft.util.Identifier
import org.bukkit.NamespacedKey

fun Identifier.toNamespacedKey(): NamespacedKey = NamespacedKey(namespace, path)

fun NamespacedKey.toIdentifier(): Identifier = Identifier.of(namespace, key)