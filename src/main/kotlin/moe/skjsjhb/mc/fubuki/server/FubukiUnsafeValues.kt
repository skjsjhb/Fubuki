package moe.skjsjhb.mc.fubuki.server

import com.google.common.collect.Multimap
import org.bukkit.*
import org.bukkit.advancement.Advancement
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.block.Biome
import org.bukkit.block.data.BlockData
import org.bukkit.damage.DamageEffect
import org.bukkit.damage.DamageSource
import org.bukkit.damage.DamageType
import org.bukkit.entity.EntityType
import org.bukkit.entity.Villager
import org.bukkit.inventory.CreativeCategory
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.potion.PotionType

/**
 * Fubuki implementation of [UnsafeValues].
 */
class FubukiUnsafeValues : UnsafeValues {
    override fun toLegacy(material: Material?): Material {
        TODO("Not yet implemented")
    }

    override fun fromLegacy(material: Material?): Material {
        TODO("Not yet implemented")
    }

    override fun fromLegacy(material: MaterialData?): Material {
        TODO("Not yet implemented")
    }

    override fun fromLegacy(material: MaterialData?, itemPriority: Boolean): Material {
        TODO("Not yet implemented")
    }

    override fun fromLegacy(material: Material?, data: Byte): BlockData {
        TODO("Not yet implemented")
    }

    override fun getMaterial(material: String?, version: Int): Material {
        TODO("Not yet implemented")
    }

    override fun getDataVersion(): Int {
        TODO("Not yet implemented")
    }

    override fun modifyItemStack(stack: ItemStack?, arguments: String?): ItemStack {
        TODO("Not yet implemented")
    }

    override fun checkSupported(pdf: PluginDescriptionFile?) {
        // TODO perform checks to filter out API-incompatible plugins
    }

    override fun processClass(pdf: PluginDescriptionFile?, path: String?, clazz: ByteArray?): ByteArray {
        // TODO figure out how to process classes
        return clazz ?: ByteArray(0)
    }

    override fun loadAdvancement(key: NamespacedKey?, advancement: String?): Advancement {
        TODO("Not yet implemented")
    }

    override fun removeAdvancement(key: NamespacedKey?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDefaultAttributeModifiers(
        material: Material?,
        slot: EquipmentSlot?
    ): Multimap<Attribute, AttributeModifier> {
        TODO("Not yet implemented")
    }

    override fun getCreativeCategory(material: Material?): CreativeCategory {
        TODO("Not yet implemented")
    }

    override fun getBlockTranslationKey(material: Material?): String {
        TODO("Not yet implemented")
    }

    override fun getItemTranslationKey(material: Material?): String {
        TODO("Not yet implemented")
    }

    override fun getTranslationKey(entityType: EntityType?): String {
        TODO("Not yet implemented")
    }

    override fun getTranslationKey(itemStack: ItemStack?): String {
        TODO("Not yet implemented")
    }

    override fun getTranslationKey(attribute: Attribute?): String {
        TODO("Not yet implemented")
    }

    override fun getFeatureFlag(key: NamespacedKey): FeatureFlag? {
        TODO("Not yet implemented")
    }

    override fun getInternalPotionData(key: NamespacedKey?): PotionType.InternalPotionData {
        TODO("Not yet implemented")
    }

    override fun getDamageEffect(key: String): DamageEffect? {
        TODO("Not yet implemented")
    }

    override fun createDamageSourceBuilder(damageType: DamageType): DamageSource.Builder {
        TODO("Not yet implemented")
    }

    override fun get(aClass: Class<*>?, value: String?): String {
        TODO("Not yet implemented")
    }

    override fun <B : Keyed?> get(registry: Registry<B>?, key: NamespacedKey?): B {
        TODO("Not yet implemented")
    }

    override fun getCustomBiome(): Biome {
        TODO("Not yet implemented")
    }

    override fun createReputationType(key: String?): Villager.ReputationType {
        TODO("Not yet implemented")
    }

    override fun createReputationEvent(key: String?): Villager.ReputationEvent {
        TODO("Not yet implemented")
    }
}