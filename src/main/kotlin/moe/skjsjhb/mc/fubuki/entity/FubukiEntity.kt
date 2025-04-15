package moe.skjsjhb.mc.fubuki.entity

import moe.skjsjhb.mc.fubuki.interop.asBukkit
import moe.skjsjhb.mc.fubuki.metadata.MetadataContainer
import moe.skjsjhb.mc.fubuki.server.FubukiServer
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

@Suppress("OVERRIDE_DEPRECATION", "REMOVAL")
open class FubukiEntity(
    private val entityDelegate: net.minecraft.entity.Entity
) : Entity {
    private val server: FubukiServer = entityDelegate.server!!.asBukkit() // This value is only nullable on client side

    fun asMojangEntity() = entityDelegate

    override fun setMetadata(metadataKey: String, newMetadataValue: MetadataValue) {
        (this as MetadataContainer).`fubuki$setMetadata`(metadataKey, newMetadataValue)
    }

    override fun getMetadata(metadataKey: String): MutableList<MetadataValue> =
        (this as MetadataContainer).`fubuki$getMetadata`(metadataKey)

    override fun hasMetadata(metadataKey: String): Boolean =
        (this as MetadataContainer).`fubuki$hasMetadata`(metadataKey)

    override fun removeMetadata(metadataKey: String, owningPlugin: Plugin) {
        (this as MetadataContainer).`fubuki$removeMetadata`(metadataKey, owningPlugin)
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
        TODO("Not yet implemented")
    }

    override fun hasPermission(perm: Permission): Boolean {
        TODO("Not yet implemented")
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

    override fun sendMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(vararg messages: String?) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(sender: UUID?, message: String) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(sender: UUID?, vararg messages: String?) {
        TODO("Not yet implemented")
    }

    override fun getServer(): Server = server

    override fun getName(): String = entityDelegate.nameForScoreboard

    override fun getCustomName(): String? {
        TODO("Not yet implemented")
    }

    override fun setCustomName(name: String?) {
        TODO("Not yet implemented")
    }

    override fun getPersistentDataContainer(): PersistentDataContainer {
        TODO("Not yet implemented")
    }

    override fun getLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun getLocation(loc: Location?): Location? {
        TODO("Not yet implemented")
    }

    override fun getVelocity(): Vector {
        TODO("Not yet implemented")
    }

    override fun setVelocity(velocity: Vector) {
        TODO("Not yet implemented")
    }

    override fun getHeight(): Double = entityDelegate.height.toDouble()

    override fun getWidth(): Double = entityDelegate.width.toDouble()

    override fun getBoundingBox(): BoundingBox {
        TODO("Not yet implemented")
    }

    override fun isOnGround(): Boolean =
        // TODO handle arrows
        entityDelegate.isOnGround

    override fun isInWater(): Boolean = entityDelegate.isTouchingWater

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

    override fun getNearbyEntities(x: Double, y: Double, z: Double): MutableList<Entity> {
        TODO("Not yet implemented")
    }

    override fun getEntityId(): Int = entityDelegate.id

    override fun getFireTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setFireTicks(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getMaxFireTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun isVisualFire(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setVisualFire(fire: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getFreezeTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setFreezeTicks(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getMaxFreezeTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun isFrozen(): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove() {
        // TODO plugin remove (seems related to a leash bug)
        entityDelegate.discard()
    }

    override fun isDead(): Boolean = !entityDelegate.isAlive

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

    override fun getPassenger(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setPassenger(passenger: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPassengers(): MutableList<Entity> {
        TODO("Not yet implemented")
    }

    override fun addPassenger(passenger: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun removePassenger(passenger: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun eject(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFallDistance(): Float {
        TODO("Not yet implemented")
    }

    override fun setFallDistance(distance: Float) {
        TODO("Not yet implemented")
    }

    override fun getLastDamageCause(): EntityDamageEvent? {
        TODO("Not yet implemented")
    }

    override fun setLastDamageCause(event: EntityDamageEvent?) {
        TODO("Not yet implemented")
    }

    override fun getUniqueId(): UUID = entityDelegate.uuid

    override fun getTicksLived(): Int {
        TODO("Not yet implemented")
    }

    override fun setTicksLived(value: Int) {
        TODO("Not yet implemented")
    }

    override fun playEffect(type: EntityEffect) {
        TODO("Not yet implemented")
    }

    override fun getType(): EntityType {
        TODO("Not yet implemented")
    }

    override fun getSwimSound(): Sound {
        TODO("Not yet implemented")
    }

    override fun getSwimSplashSound(): Sound {
        TODO("Not yet implemented")
    }

    override fun getSwimHighSpeedSplashSound(): Sound {
        TODO("Not yet implemented")
    }

    override fun isInsideVehicle(): Boolean {
        TODO("Not yet implemented")
    }

    override fun leaveVehicle(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getVehicle(): Entity? {
        TODO("Not yet implemented")
    }

    override fun isCustomNameVisible(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setCustomNameVisible(flag: Boolean) {
        TODO("Not yet implemented")
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

    override fun isGlowing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setGlowing(flag: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isInvulnerable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setInvulnerable(flag: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSilent(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSilent(flag: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hasGravity(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setGravity(gravity: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPortalCooldown(): Int {
        TODO("Not yet implemented")
    }

    override fun setPortalCooldown(cooldown: Int) {
        TODO("Not yet implemented")
    }

    override fun getScoreboardTags(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun addScoreboardTag(tag: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeScoreboardTag(tag: String): Boolean {
        TODO("Not yet implemented")
    }

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
        TODO("Not yet implemented")
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