package moe.skjsjhb.mc.fubuki.data

import org.bukkit.metadata.MetadataValue
import org.bukkit.plugin.Plugin

@Suppress("FunctionName")
interface MetadataContainer {
    fun `fubuki$setMetadata`(k: String, v: MetadataValue)

    fun `fubuki$getMetadata`(k: String): MutableList<MetadataValue>

    fun `fubuki$removeMetadata`(k: String, plugin: Plugin)

    fun `fubuki$hasMetadata`(k: String): Boolean
}