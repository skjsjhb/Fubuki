package moe.skjsjhb.mc.fubuki.entity

import moe.skjsjhb.mc.fubuki.data.FubukiPDC
import moe.skjsjhb.mc.fubuki.data.MetadataContainer
import moe.skjsjhb.mc.fubuki.data.PDCBinder
import moe.skjsjhb.mc.fubuki.interop.assumeBukkit
import moe.skjsjhb.mc.fubuki.interop.toNamespacedKey
import moe.skjsjhb.mc.fubuki.math.toBukkitVector
import moe.skjsjhb.mc.fubuki.math.toMojangVec3d
import moe.skjsjhb.mc.fubuki.util.CraftTextConversion
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import org.bukkit.*
import org.bukkit.block.BlockFace
import org.bukkit.block.PistonMoveReaction
import org.bukkit.entity.*
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.metadata.MetadataValue
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.plugin.Plugin
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Suppress("OVERRIDE_DEPRECATION", "REMOVAL")
open class FubukiEntity(
    entity: net.minecraft.entity.Entity
) : Entity {
    protected open val delegate = entity
    private val persistentDataContainer = FubukiPDC()

    init {
        (entity as PDCBinder).`fubuki$bindPDC`(persistentDataContainer)
    }

    open fun toMojang(): net.minecraft.entity.Entity = delegate

    override fun setMetadata(metadataKey: String, newMetadataValue: MetadataValue) {
        (delegate as MetadataContainer).`fubuki$setMetadata`(metadataKey, newMetadataValue)
    }

    override fun getMetadata(metadataKey: String): MutableList<MetadataValue> =
        (delegate as MetadataContainer).`fubuki$getMetadata`(metadataKey)

    override fun hasMetadata(metadataKey: String): Boolean =
        (delegate as MetadataContainer).`fubuki$hasMetadata`(metadataKey)

    override fun removeMetadata(metadataKey: String, owningPlugin: Plugin) {
        (delegate as MetadataContainer).`fubuki$removeMetadata`(metadataKey, owningPlugin)
    }

    override fun isOp(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setOp(value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isPermissionSet(name: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPermissionSet(perm: Permission): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPermission(name: String): Boolean {
        // TODO implement permission
        return true
    }

    override fun hasPermission(perm: Permission): Boolean {
        // TODO implement permission
        return true
    }

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean): PermissionAttachment {
        TODO("Not yet implemented")
    }

    override fun addAttachment(plugin: Plugin): PermissionAttachment {
        TODO("Not yet implemented")
    }

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean, ticks: Int): PermissionAttachment? {
        TODO("Not yet implemented")
    }

    override fun addAttachment(plugin: Plugin, ticks: Int): PermissionAttachment? {
        TODO("Not yet implemented")
    }

    override fun removeAttachment(attachment: PermissionAttachment) {
        TODO("Not yet implemented")
    }

    override fun recalculatePermissions() {
        TODO("Not yet implemented")
    }

    override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo> {
        TODO("Not yet implemented")
    }

    override fun sendMessage(message: String) {}

    override fun sendMessage(vararg messages: String?) {}

    override fun sendMessage(sender: UUID?, message: String) {}

    override fun sendMessage(sender: UUID?, vararg messages: String?) {}

    override fun getServer(): Server = delegate.server!!.assumeBukkit()

    override fun getName(): String = delegate.nameForScoreboard

    override fun getCustomName(): String? =
        delegate.customName?.let { CraftTextConversion.fromComponent(it) }

    override fun setCustomName(name: String?) {
        delegate.customName = CraftTextConversion.fromStringOrNull(name)
    }

    override fun getPersistentDataContainer(): PersistentDataContainer = persistentDataContainer

    override fun getLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun getLocation(loc: Location?): Location? {
        TODO("Not yet implemented")
    }

    override fun getVelocity(): Vector = delegate.velocity.toBukkitVector()

    override fun setVelocity(velocity: Vector) {
        delegate.velocity = velocity.toMojangVec3d()
    }

    override fun getHeight(): Double = delegate.height.toDouble()

    override fun getWidth(): Double = delegate.width.toDouble()

    override fun getBoundingBox(): BoundingBox =
        delegate.boundingBox.run {
            BoundingBox(minX, minY, minZ, maxX, maxY, maxZ)
        }

    // CraftBukkit only checks arrows, while we check for all possible projectiles extending such class
    // (However, I still believe that this method is quite unreliable...)
    override fun isOnGround(): Boolean = (delegate as? PersistentProjectileEntity)?.isInGround ?: delegate.isOnGround

    override fun isInWater(): Boolean = delegate.isTouchingWater

    override fun getWorld(): World {
        TODO("Not yet implemented")
    }

    override fun setRotation(yaw: Float, pitch: Float) {
        TODO("Not yet implemented")
    }

    override fun teleport(location: Location): Boolean {
        TODO("Not yet implemented")
    }

    override fun teleport(location: Location, cause: PlayerTeleportEvent.TeleportCause): Boolean {
        TODO("Not yet implemented")
    }

    override fun teleport(destination: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun teleport(destination: Entity, cause: PlayerTeleportEvent.TeleportCause): Boolean {
        TODO("Not yet implemented")
    }

    override fun getNearbyEntities(x: Double, y: Double, z: Double): MutableList<Entity> =
        delegate.world.getOtherEntities(delegate, delegate.boundingBox.expand(x, y, z))
            .map { it.toBukkit() }
            .toMutableList()

    override fun getEntityId(): Int = delegate.id

    override fun getFireTicks(): Int = delegate.fireTicks

    override fun setFireTicks(ticks: Int) {
        delegate.fireTicks = ticks
    }

    override fun getMaxFireTicks(): Int = delegate.burningDuration

    override fun isVisualFire(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setVisualFire(fire: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getFreezeTicks(): Int = delegate.frozenTicks

    override fun setFreezeTicks(ticks: Int) {
        delegate.frozenTicks = ticks
    }

    override fun getMaxFreezeTicks(): Int = delegate.minFreezeDamageTicks

    override fun isFrozen(): Boolean = delegate.isFrozen

    override fun remove() {
        // TODO plugin remove (seems related to a leash bug)
        delegate.discard()
    }

    override fun isDead(): Boolean = !delegate.isAlive

    override fun isValid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPersistent(): Boolean {
        // This requires a mixin
        TODO("Not yet implemented")
    }

    override fun setPersistent(persistent: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPassenger(): Entity? = delegate.firstPassenger?.toBukkit()

    override fun setPassenger(passenger: Entity): Boolean {
        (passenger as FubukiEntity).delegate.let {
            // An entity cannot ride itself
            // Seems Mojang code does not check for this
            if (it == delegate) return false
            return it.startRiding(delegate)
        }
    }

    override fun getPassengers(): MutableList<Entity> =
        delegate.passengerList.map { it.toBukkit() }.toMutableList()

    // Such a weird method name...
    // It actually forces the passenger to ride on this entity
    override fun addPassenger(passenger: Entity): Boolean {
        (passenger as FubukiEntity).delegate.let {
            if (it == delegate) return false
            return it.startRiding(delegate, true)
        }
    }

    override fun removePassenger(passenger: Entity): Boolean {
        (passenger as FubukiEntity).delegate.stopRiding()
        return true
    }

    override fun isEmpty(): Boolean = delegate.passengerList.isEmpty()

    override fun eject(): Boolean =
        if (isEmpty()) true
        else {
            delegate.removeAllPassengers()
            false
        }


    override fun getFallDistance(): Float = delegate.fallDistance.toFloat()

    override fun setFallDistance(distance: Float) {
        delegate.fallDistance = distance.toDouble()
    }

    override fun getLastDamageCause(): EntityDamageEvent? {
        TODO("Not yet implemented")
    }

    override fun setLastDamageCause(event: EntityDamageEvent?) {
        TODO("Not yet implemented")
    }

    override fun getUniqueId(): UUID = delegate.uuid

    override fun getTicksLived(): Int = delegate.age // Weird field name

    override fun setTicksLived(value: Int) {
        delegate.age = value
    }

    override fun playEffect(type: EntityEffect) {
        TODO("Not yet implemented")
    }

    override fun getType(): EntityType =
        Registries.ENTITY_TYPE.getKey(delegate.type).getOrNull()?.let {
            // CraftBukkit throws an exception when the type could not be found in the Bukkit registry.
            // We'll utilize the UNKNOWN value to suppress such behavior.
            // This shall make plugins able to know about mod entity types.
            Registry.ENTITY_TYPE.get(it.value.toNamespacedKey())
        } ?: EntityType.UNKNOWN

    override fun getSwimSound(): Sound {
        TODO("Not yet implemented")
    }

    override fun getSwimSplashSound(): Sound {
        TODO("Not yet implemented")
    }

    override fun getSwimHighSpeedSplashSound(): Sound {
        TODO("Not yet implemented")
    }

    override fun isInsideVehicle(): Boolean = delegate.hasVehicle()

    override fun leaveVehicle(): Boolean {
        if (!isInsideVehicle()) return false

        delegate.stopRiding()
        return true
    }

    override fun getVehicle(): Entity? = delegate.vehicle?.toBukkit()

    override fun isCustomNameVisible(): Boolean = delegate.isCustomNameVisible

    override fun setCustomNameVisible(flag: Boolean) {
        delegate.isCustomNameVisible = flag
    }

    override fun isVisibleByDefault(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setVisibleByDefault(visible: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getTrackedBy(): MutableSet<Player> {
        TODO("Not yet implemented")
    }

    override fun isGlowing(): Boolean = delegate.isGlowing

    override fun setGlowing(flag: Boolean) {
        delegate.isGlowing = flag
    }

    // This method does not have a clear definition of "invulnerable" in the interface
    // CraftBukkit uses a more complex (stricter) logic to define it
    // We'll simply use this value unless incompatibility is found
    override fun isInvulnerable(): Boolean =
        delegate.isInvulnerable

    override fun setInvulnerable(flag: Boolean) {
        delegate.isInvulnerable = flag
    }

    override fun isSilent(): Boolean = delegate.isSilent

    override fun setSilent(flag: Boolean) {
        delegate.isSilent = flag
    }

    override fun hasGravity(): Boolean = !delegate.hasNoGravity()

    override fun setGravity(gravity: Boolean) {
        delegate.setNoGravity(!gravity)
    }

    override fun getPortalCooldown(): Int =
        delegate.portalCooldown

    override fun setPortalCooldown(cooldown: Int) {
        delegate.portalCooldown = cooldown
    }

    override fun getScoreboardTags(): MutableSet<String> = delegate.commandTags

    override fun addScoreboardTag(tag: String): Boolean =
        delegate.addCommandTag(tag)

    override fun removeScoreboardTag(tag: String): Boolean =
        delegate.removeCommandTag(tag)

    override fun getPistonMoveReaction(): PistonMoveReaction {
        TODO("Not yet implemented")
    }

    override fun getFacing(): BlockFace {
        TODO("Not yet implemented")
    }

    override fun getPose(): Pose {
        TODO("Not yet implemented")
    }

    override fun getSpawnCategory(): SpawnCategory {
        TODO("Not yet implemented")
    }

    override fun isInWorld(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAsString(): String? {
        val nbt = NbtCompound()
        if (!delegate.saveSelfNbt(nbt)) return null
        return nbt.toString()
    }

    override fun createSnapshot(): EntitySnapshot? {
        TODO("Not yet implemented")
    }

    override fun copy(): Entity {
        TODO("Not yet implemented")
    }

    override fun copy(to: Location): Entity {
        TODO("Not yet implemented")
    }
}