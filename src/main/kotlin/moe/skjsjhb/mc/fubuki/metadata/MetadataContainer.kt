package moe.skjsjhb.mc.fubuki.metadata

import org.bukkit.metadata.MetadataValue
import org.bukkit.plugin.Plugin

interface MetadataContainer {
    fun `fubuki$setMetadata`(k: String, v: MetadataValue)

    fun `fubuki$getMetadata`(k: String): MutableList<MetadataValue>

    fun `fubuki$removeMetadata`(k: String, plugin: Plugin)

    fun `fubuki$hasMetadata`(k: String): Boolean
}