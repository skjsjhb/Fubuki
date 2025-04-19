package moe.skjsjhb.mc.fubuki.entity

import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Hand
import org.bukkit.FluidCollisionMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeInstance
import org.bukkit.block.Block
import org.bukkit.damage.DamageSource
import org.bukkit.entity.*
import org.bukkit.entity.memory.MemoryKey
import org.bukkit.inventory.EntityEquipment
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.RayTraceResult
import org.bukkit.util.Vector
import java.util.*

@Suppress("OVERRIDE_DEPRECATION")
open class FubukiLivingEntity(
    override val delegate: net.minecraft.entity.LivingEntity
) : FubukiEntity(delegate), LivingEntity {

    override fun toMojang(): net.minecraft.entity.LivingEntity = delegate

    override fun getAttribute(attribute: Attribute): AttributeInstance? {
        TODO("Not yet implemented")
    }

    override fun damage(amount: Double) {
        delegate.damage(delegate.world as ServerWorld, delegate.damageSources.generic(), amount.toFloat())
    }

    override fun damage(amount: Double, source: Entity?) {
        val reason = if (source is FubukiPlayer) {
            delegate.damageSources.playerAttack(source.toMojang())
        } else if (source is FubukiLivingEntity) {
            delegate.damageSources.mobAttack(source.toMojang())
        } else {
            delegate.damageSources.generic()
        }

        delegate.damage(delegate.world as ServerWorld, reason, amount.toFloat())
    }

    override fun damage(amount: Double, damageSource: DamageSource) {
        TODO("Not yet implemented")
    }

    override fun getHealth(): Double = delegate.health.toDouble()

    override fun setHealth(health: Double) {
        if (health < 0 || health > maxHealth) return

        delegate.health = health.toFloat()

        if (health <= 0) {
            delegate.onDeath(delegate.damageSources.generic())
        }
    }

    override fun getAbsorptionAmount(): Double {
        TODO("Not yet implemented")
    }

    override fun setAbsorptionAmount(amount: Double) {
        TODO("Not yet implemented")
    }

    override fun getMaxHealth(): Double = delegate.maxHealth.toDouble()

    override fun setMaxHealth(health: Double) {
        delegate.getAttributeInstance(EntityAttributes.MAX_HEALTH)?.baseValue = health

        if (health > getHealth()) {
            setHealth(health)
        }
    }

    override fun resetMaxHealth() {
        delegate.getAttributeInstance(EntityAttributes.MAX_HEALTH)?.attribute?.value()?.defaultValue
            ?.let { maxHealth = it }
    }

    override fun <T : Projectile?> launchProjectile(projectile: Class<out T>): T & Any {
        TODO("Not yet implemented")
    }

    override fun <T : Projectile?> launchProjectile(projectile: Class<out T>, velocity: Vector?): T & Any {
        TODO("Not yet implemented")
    }

    override fun getEyeHeight(): Double {
        TODO("Not yet implemented")
    }

    override fun getEyeHeight(ignorePose: Boolean): Double {
        TODO("Not yet implemented")
    }

    override fun getEyeLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun getLineOfSight(transparent: MutableSet<Material>?, maxDistance: Int): MutableList<Block> {
        TODO("Not yet implemented")
    }

    override fun getTargetBlock(transparent: MutableSet<Material>?, maxDistance: Int): Block {
        TODO("Not yet implemented")
    }

    override fun getLastTwoTargetBlocks(transparent: MutableSet<Material>?, maxDistance: Int): MutableList<Block> {
        TODO("Not yet implemented")
    }

    override fun getTargetBlockExact(maxDistance: Int): Block? {
        TODO("Not yet implemented")
    }

    override fun getTargetBlockExact(maxDistance: Int, fluidCollisionMode: FluidCollisionMode): Block? {
        TODO("Not yet implemented")
    }

    override fun rayTraceBlocks(maxDistance: Double): RayTraceResult? {
        TODO("Not yet implemented")
    }

    override fun rayTraceBlocks(maxDistance: Double, fluidCollisionMode: FluidCollisionMode): RayTraceResult? {
        TODO("Not yet implemented")
    }

    override fun getRemainingAir(): Int =
        delegate.air

    override fun setRemainingAir(ticks: Int) {
        delegate.air = ticks
    }

    override fun getMaximumAir(): Int = delegate.maxAir

    override fun setMaximumAir(ticks: Int) {
        // This needs a mixin to implement
        TODO("Not yet implemented")
    }

    override fun getItemInUse(): ItemStack? {
        TODO("Not yet implemented")
    }

    override fun getItemInUseTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setItemInUseTicks(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getArrowCooldown(): Int {
        TODO("Not yet implemented")
    }

    override fun setArrowCooldown(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getArrowsInBody(): Int {
        TODO("Not yet implemented")
    }

    override fun setArrowsInBody(count: Int) {
        TODO("Not yet implemented")
    }

    override fun getMaximumNoDamageTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setMaximumNoDamageTicks(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getLastDamage(): Double {
        TODO("Not yet implemented")
    }

    override fun setLastDamage(damage: Double) {
        TODO("Not yet implemented")
    }

    override fun getNoDamageTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setNoDamageTicks(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getNoActionTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setNoActionTicks(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getKiller(): Player? {
        TODO("Not yet implemented")
    }

    override fun addPotionEffect(effect: PotionEffect): Boolean {
        TODO("Not yet implemented")
    }

    override fun addPotionEffect(effect: PotionEffect, force: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun addPotionEffects(effects: MutableCollection<PotionEffect>): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPotionEffect(type: PotionEffectType): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPotionEffect(type: PotionEffectType): PotionEffect? {
        TODO("Not yet implemented")
    }

    override fun removePotionEffect(type: PotionEffectType) {
        TODO("Not yet implemented")
    }

    override fun getActivePotionEffects(): MutableCollection<PotionEffect> {
        TODO("Not yet implemented")
    }

    override fun hasLineOfSight(other: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRemoveWhenFarAway(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setRemoveWhenFarAway(remove: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getEquipment(): EntityEquipment? {
        TODO("Not yet implemented")
    }

    override fun getCanPickupItems(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setCanPickupItems(pickup: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isLeashed(): Boolean =
        (delegate as? MobEntity)?.isLeashed ?: false

    override fun getLeashHolder(): Entity {
        (delegate as? MobEntity)?.leashHolder?.let {
            return it.toBukkit()
        }

        throw IllegalStateException("Entity is not leashed")
    }

    override fun setLeashHolder(holder: Entity?): Boolean {
        (delegate as? MobEntity)?.leashData?.let {
            it.leashHolder = (holder as FubukiEntity).toMojang()
            return true
        }
        return false
    }

    override fun isGliding(): Boolean = delegate.isGliding

    override fun setGliding(gliding: Boolean) {
        delegate.setFlag(net.minecraft.entity.Entity.GLIDING_FLAG_INDEX, gliding)
    }

    override fun isSwimming(): Boolean = delegate.isSwimming

    override fun setSwimming(swimming: Boolean) {
        delegate.isSwimming = swimming
    }

    override fun isRiptiding(): Boolean =
        delegate.isUsingRiptide

    override fun setRiptiding(riptiding: Boolean) {
        delegate.setFlag(net.minecraft.entity.LivingEntity.USING_RIPTIDE_FLAG, riptiding)
    }

    override fun isSleeping(): Boolean = delegate.isSleeping

    override fun isClimbing(): Boolean = delegate.isClimbing

    override fun setAI(ai: Boolean) {
        (delegate as? MobEntity)?.isAiDisabled = !ai
    }

    override fun hasAI(): Boolean = if (delegate is MobEntity) !(delegate as MobEntity).isAiDisabled else false

    override fun attack(target: Entity) {
        if (delegate is PlayerEntity) {
            (delegate as PlayerEntity).attack((target as FubukiEntity).toMojang())
        } else {
            delegate.tryAttack(delegate.world as ServerWorld, (target as FubukiEntity).toMojang())
        }
    }

    override fun swingMainHand() {
        delegate.swingHand(Hand.MAIN_HAND)
    }

    override fun swingOffHand() {
        delegate.swingHand(Hand.OFF_HAND)
    }

    override fun playHurtAnimation(yaw: Float) {
        TODO("Not yet implemented")
    }

    override fun isCollidable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setCollidable(collidable: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getCollidableExemptions(): MutableSet<UUID> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> getMemory(memoryKey: MemoryKey<T>): T? {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> setMemory(memoryKey: MemoryKey<T>, memoryValue: T?) {
        TODO("Not yet implemented")
    }

    override fun getHurtSound(): Sound? {
        TODO("Not yet implemented")
    }

    override fun getDeathSound(): Sound? {
        TODO("Not yet implemented")
    }

    override fun getFallDamageSound(fallHeight: Int): Sound {
        TODO("Not yet implemented")
    }

    override fun getFallDamageSoundSmall(): Sound {
        TODO("Not yet implemented")
    }

    override fun getFallDamageSoundBig(): Sound {
        TODO("Not yet implemented")
    }

    override fun getDrinkingSound(itemStack: ItemStack): Sound {
        TODO("Not yet implemented")
    }

    override fun getEatingSound(itemStack: ItemStack): Sound {
        TODO("Not yet implemented")
    }

    override fun canBreatheUnderwater(): Boolean = delegate.canBreatheInWater()

    override fun getCategory(): EntityCategory {
        TODO("Not yet implemented")
    }

    override fun isInvisible(): Boolean = delegate.isInvisible

    override fun setInvisible(invisible: Boolean) {
        delegate.isInvisible = invisible
    }
}