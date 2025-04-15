package moe.skjsjhb.mc.fubuki.player

import moe.skjsjhb.mc.fubuki.interop.asBukkit
import moe.skjsjhb.mc.fubuki.server.FubukiServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import org.bukkit.*
import org.bukkit.advancement.Advancement
import org.bukkit.advancement.AdvancementProgress
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeInstance
import org.bukkit.block.*
import org.bukkit.block.data.BlockData
import org.bukkit.block.sign.Side
import org.bukkit.conversations.Conversation
import org.bukkit.conversations.ConversationAbandonedEvent
import org.bukkit.damage.DamageSource
import org.bukkit.entity.*
import org.bukkit.entity.memory.MemoryKey
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.inventory.*
import org.bukkit.map.MapView
import org.bukkit.metadata.MetadataValue
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.profile.PlayerProfile
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.util.BoundingBox
import org.bukkit.util.RayTraceResult
import org.bukkit.util.Vector
import java.net.InetAddress
import java.net.InetSocketAddress
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.CompletableFuture

@Suppress("OVERRIDE_DEPRECATION", "REMOVAL", "DEPRECATION")
class FubukiPlayer(
    private val delegate: ServerPlayerEntity
) : Player {
    private val server: FubukiServer = delegate.server.asBukkit()

    fun toMojang(): ServerPlayerEntity {
        return delegate
    }

    override fun getAttribute(attribute: Attribute): AttributeInstance? {
        TODO("Not yet implemented")
    }

    override fun setMetadata(metadataKey: String, newMetadataValue: MetadataValue) {
        TODO("Not yet implemented")
    }

    override fun getMetadata(metadataKey: String): MutableList<MetadataValue> {
        TODO("Not yet implemented")
    }

    override fun hasMetadata(metadataKey: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeMetadata(metadataKey: String, owningPlugin: Plugin) {
        TODO("Not yet implemented")
    }

    override fun isOp(): Boolean = server.nativeServer.playerManager.isOperator(delegate.gameProfile)

    override fun setOp(op: Boolean) {
        if (op) {
            server.nativeServer.playerManager.addToOperators(delegate.gameProfile)
        } else {
            server.nativeServer.playerManager.removeFromOperators(delegate.gameProfile)
        }
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
        // TODO check for conversation
        delegate.sendMessage(Text.of(message))
    }

    override fun sendMessage(vararg messages: String?) {
        messages.forEach {
            delegate.sendMessage(Text.of(it)) // Mojang text API supports null values
        }
    }

    override fun sendMessage(sender: UUID?, message: String) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(sender: UUID?, vararg messages: String?) {
        TODO("Not yet implemented")
    }

    override fun getServer(): Server = server

    override fun getName(): String = delegate.gameProfile.name

    override fun getCustomName(): String? = delegate.customName?.literalString

    override fun setCustomName(name: String?) {
        if (name == null) {
            delegate.customName = null
        } else {
            delegate.customName = Text.of(name)
        }
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

    override fun getHeight(): Double = delegate.height.toDouble()

    override fun getWidth(): Double = delegate.width.toDouble()

    override fun getBoundingBox(): BoundingBox {
        TODO("Not yet implemented")
    }

    override fun isOnGround(): Boolean = delegate.isOnGround

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

    override fun getNearbyEntities(x: Double, y: Double, z: Double): MutableList<Entity> {
        TODO("Not yet implemented")
    }

    override fun getEntityId(): Int = delegate.id

    override fun getFireTicks(): Int = delegate.fireTicks

    override fun setFireTicks(ticks: Int) {
        delegate.fireTicks = ticks
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

    override fun getFreezeTicks(): Int =
        delegate.frozenTicks

    override fun setFreezeTicks(ticks: Int) {
        delegate.frozenTicks = ticks
    }

    override fun getMaxFreezeTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun isFrozen(): Boolean = delegate.isFrozen

    override fun remove() {
        throw UnsupportedOperationException("Cannot remove a player entity directly")
    }

    override fun isDead(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isValid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPersistent(): Boolean {
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

    override fun getUniqueId(): UUID {
        TODO("Not yet implemented")
    }

    override fun getTicksLived(): Int {
        TODO("Not yet implemented")
    }

    override fun setTicksLived(value: Int) {
        TODO("Not yet implemented")
    }

    override fun playEffect(loc: Location, effect: Effect, data: Int) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> playEffect(loc: Location, effect: Effect, data: T?) {
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

    override fun damage(amount: Double) {
        TODO("Not yet implemented")
    }

    override fun damage(amount: Double, source: Entity?) {
        TODO("Not yet implemented")
    }

    override fun damage(amount: Double, damageSource: DamageSource) {
        TODO("Not yet implemented")
    }

    override fun getHealth(): Double {
        TODO("Not yet implemented")
    }

    override fun setHealth(health: Double) {
        TODO("Not yet implemented")
    }

    override fun getAbsorptionAmount(): Double {
        TODO("Not yet implemented")
    }

    override fun setAbsorptionAmount(amount: Double) {
        TODO("Not yet implemented")
    }

    override fun getMaxHealth(): Double {
        TODO("Not yet implemented")
    }

    override fun setMaxHealth(health: Double) {
        TODO("Not yet implemented")
    }

    override fun resetMaxHealth() {
        TODO("Not yet implemented")
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

    override fun getRemainingAir(): Int {
        TODO("Not yet implemented")
    }

    override fun setRemainingAir(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getMaximumAir(): Int {
        TODO("Not yet implemented")
    }

    override fun setMaximumAir(ticks: Int) {
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

    override fun isLeashed(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getLeashHolder(): Entity {
        TODO("Not yet implemented")
    }

    override fun setLeashHolder(holder: Entity?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isGliding(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setGliding(gliding: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSwimming(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSwimming(swimming: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isRiptiding(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setRiptiding(riptiding: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSleeping(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isClimbing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setAI(ai: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hasAI(): Boolean {
        TODO("Not yet implemented")
    }

    override fun attack(target: Entity) {
        TODO("Not yet implemented")
    }

    override fun swingMainHand() {
        TODO("Not yet implemented")
    }

    override fun swingOffHand() {
        TODO("Not yet implemented")
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

    override fun canBreatheUnderwater(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCategory(): EntityCategory {
        TODO("Not yet implemented")
    }

    override fun isInvisible(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setInvisible(invisible: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getInventory(): PlayerInventory {
        TODO("Not yet implemented")
    }

    override fun getEnderChest(): Inventory {
        TODO("Not yet implemented")
    }

    override fun getMainHand(): MainHand {
        TODO("Not yet implemented")
    }

    override fun setWindowProperty(prop: InventoryView.Property, value: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getEnchantmentSeed(): Int {
        TODO("Not yet implemented")
    }

    override fun setEnchantmentSeed(seed: Int) {
        TODO("Not yet implemented")
    }

    override fun getOpenInventory(): InventoryView {
        TODO("Not yet implemented")
    }

    override fun openInventory(inventory: Inventory): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openInventory(inventory: InventoryView) {
        TODO("Not yet implemented")
    }

    override fun openWorkbench(location: Location?, force: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openEnchanting(location: Location?, force: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openMerchant(trader: Villager, force: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openMerchant(merchant: Merchant, force: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun closeInventory() {
        TODO("Not yet implemented")
    }

    override fun getItemInHand(): ItemStack {
        TODO("Not yet implemented")
    }

    override fun setItemInHand(item: ItemStack?) {
        TODO("Not yet implemented")
    }

    override fun getItemOnCursor(): ItemStack {
        TODO("Not yet implemented")
    }

    override fun setItemOnCursor(item: ItemStack?) {
        TODO("Not yet implemented")
    }

    override fun hasCooldown(material: Material): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasCooldown(item: ItemStack): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCooldown(material: Material): Int {
        TODO("Not yet implemented")
    }

    override fun getCooldown(item: ItemStack): Int {
        TODO("Not yet implemented")
    }

    override fun setCooldown(material: Material, ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun setCooldown(item: ItemStack, ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getSleepTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun sleep(location: Location, force: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun wakeup(setSpawnLocation: Boolean) {
        TODO("Not yet implemented")
    }

    override fun startRiptideAttack(duration: Int, attackStrength: Float, attackItem: ItemStack?) {
        TODO("Not yet implemented")
    }

    override fun getBedLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun getGameMode(): GameMode {
        TODO("Not yet implemented")
    }

    override fun setGameMode(mode: GameMode) {
        TODO("Not yet implemented")
    }

    override fun isBlocking(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isHandRaised(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getExpToLevel(): Int {
        TODO("Not yet implemented")
    }

    override fun getAttackCooldown(): Float {
        TODO("Not yet implemented")
    }

    override fun discoverRecipe(recipe: NamespacedKey): Boolean {
        TODO("Not yet implemented")
    }

    override fun discoverRecipes(recipes: MutableCollection<NamespacedKey>): Int {
        TODO("Not yet implemented")
    }

    override fun undiscoverRecipe(recipe: NamespacedKey): Boolean {
        TODO("Not yet implemented")
    }

    override fun undiscoverRecipes(recipes: MutableCollection<NamespacedKey>): Int {
        TODO("Not yet implemented")
    }

    override fun hasDiscoveredRecipe(recipe: NamespacedKey): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDiscoveredRecipes(): MutableSet<NamespacedKey> {
        TODO("Not yet implemented")
    }

    override fun getShoulderEntityLeft(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setShoulderEntityLeft(entity: Entity?) {
        TODO("Not yet implemented")
    }

    override fun getShoulderEntityRight(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setShoulderEntityRight(entity: Entity?) {
        TODO("Not yet implemented")
    }

    override fun dropItem(dropAll: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun getExhaustion(): Float {
        TODO("Not yet implemented")
    }

    override fun setExhaustion(value: Float) {
        TODO("Not yet implemented")
    }

    override fun getSaturation(): Float {
        TODO("Not yet implemented")
    }

    override fun setSaturation(value: Float) {
        TODO("Not yet implemented")
    }

    override fun getFoodLevel(): Int {
        TODO("Not yet implemented")
    }

    override fun setFoodLevel(value: Int) {
        TODO("Not yet implemented")
    }

    override fun getSaturatedRegenRate(): Int {
        TODO("Not yet implemented")
    }

    override fun setSaturatedRegenRate(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getUnsaturatedRegenRate(): Int {
        TODO("Not yet implemented")
    }

    override fun setUnsaturatedRegenRate(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getStarvationRate(): Int {
        TODO("Not yet implemented")
    }

    override fun setStarvationRate(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun getLastDeathLocation(): Location? {
        TODO("Not yet implemented")
    }

    override fun setLastDeathLocation(location: Location?) {
        TODO("Not yet implemented")
    }

    override fun fireworkBoost(fireworkItemStack: ItemStack): Firework? {
        TODO("Not yet implemented")
    }

    override fun isConversing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun acceptConversationInput(input: String) {
        TODO("Not yet implemented")
    }

    override fun beginConversation(conversation: Conversation): Boolean {
        TODO("Not yet implemented")
    }

    override fun abandonConversation(conversation: Conversation) {
        TODO("Not yet implemented")
    }

    override fun abandonConversation(conversation: Conversation, details: ConversationAbandonedEvent) {
        TODO("Not yet implemented")
    }

    override fun sendRawMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun sendRawMessage(sender: UUID?, message: String) {
        TODO("Not yet implemented")
    }

    override fun serialize(): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun isOnline(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPlayerProfile(): PlayerProfile {
        TODO("Not yet implemented")
    }

    override fun isBanned(): Boolean {
        TODO("Not yet implemented")
    }

    override fun ban(reason: String?, expires: Date?, source: String?, kickPlayer: Boolean): BanEntry<PlayerProfile>? {
        TODO("Not yet implemented")
    }

    override fun ban(
        reason: String?,
        expires: Instant?,
        source: String?,
        kickPlayer: Boolean
    ): BanEntry<PlayerProfile>? {
        TODO("Not yet implemented")
    }

    override fun ban(
        reason: String?,
        duration: Duration?,
        source: String?,
        kickPlayer: Boolean
    ): BanEntry<PlayerProfile>? {
        TODO("Not yet implemented")
    }

    override fun ban(reason: String?, expires: Date?, source: String?): BanEntry<PlayerProfile>? {
        TODO("Not yet implemented")
    }

    override fun ban(reason: String?, expires: Instant?, source: String?): BanEntry<PlayerProfile>? {
        TODO("Not yet implemented")
    }

    override fun ban(reason: String?, duration: Duration?, source: String?): BanEntry<PlayerProfile>? {
        TODO("Not yet implemented")
    }

    override fun isWhitelisted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setWhitelisted(value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPlayer(): Player? {
        TODO("Not yet implemented")
    }

    override fun getFirstPlayed(): Long {
        TODO("Not yet implemented")
    }

    override fun getLastPlayed(): Long {
        TODO("Not yet implemented")
    }

    override fun hasPlayedBefore(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getBedSpawnLocation(): Location? {
        TODO("Not yet implemented")
    }

    override fun getRespawnLocation(): Location? {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(statistic: Statistic) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(statistic: Statistic, amount: Int) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(statistic: Statistic, material: Material) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(statistic: Statistic, material: Material, amount: Int) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(statistic: Statistic, entityType: EntityType) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(statistic: Statistic, entityType: EntityType, amount: Int) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(statistic: Statistic) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(statistic: Statistic, amount: Int) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(statistic: Statistic, material: Material) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(statistic: Statistic, material: Material, amount: Int) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(statistic: Statistic, entityType: EntityType) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(statistic: Statistic, entityType: EntityType, amount: Int) {
        TODO("Not yet implemented")
    }

    override fun setStatistic(statistic: Statistic, newValue: Int) {
        TODO("Not yet implemented")
    }

    override fun setStatistic(statistic: Statistic, material: Material, newValue: Int) {
        TODO("Not yet implemented")
    }

    override fun setStatistic(statistic: Statistic, entityType: EntityType, newValue: Int) {
        TODO("Not yet implemented")
    }

    override fun getStatistic(statistic: Statistic): Int {
        TODO("Not yet implemented")
    }

    override fun getStatistic(statistic: Statistic, material: Material): Int {
        TODO("Not yet implemented")
    }

    override fun getStatistic(statistic: Statistic, entityType: EntityType): Int {
        TODO("Not yet implemented")
    }

    override fun sendPluginMessage(source: Plugin, channel: String, message: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun getListeningPluginChannels(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getDisplayName(): String {
        TODO("Not yet implemented")
    }

    override fun setDisplayName(name: String?) {
        TODO("Not yet implemented")
    }

    override fun getPlayerListName(): String {
        TODO("Not yet implemented")
    }

    override fun setPlayerListName(name: String?) {
        TODO("Not yet implemented")
    }

    override fun getPlayerListOrder(): Int {
        TODO("Not yet implemented")
    }

    override fun setPlayerListOrder(order: Int) {
        TODO("Not yet implemented")
    }

    override fun getPlayerListHeader(): String? {
        TODO("Not yet implemented")
    }

    override fun setPlayerListHeader(header: String?) {
        TODO("Not yet implemented")
    }

    override fun getPlayerListFooter(): String? {
        TODO("Not yet implemented")
    }

    override fun setPlayerListFooter(footer: String?) {
        TODO("Not yet implemented")
    }

    override fun setPlayerListHeaderFooter(header: String?, footer: String?) {
        TODO("Not yet implemented")
    }

    override fun getCompassTarget(): Location {
        TODO("Not yet implemented")
    }

    override fun setCompassTarget(loc: Location) {
        TODO("Not yet implemented")
    }

    override fun getAddress(): InetSocketAddress? {
        TODO("Not yet implemented")
    }

    override fun isTransferred(): Boolean {
        TODO("Not yet implemented")
    }

    override fun retrieveCookie(key: NamespacedKey): CompletableFuture<ByteArray> {
        TODO("Not yet implemented")
    }

    override fun storeCookie(key: NamespacedKey, value: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun transfer(host: String, port: Int) {
        TODO("Not yet implemented")
    }

    override fun kickPlayer(message: String?) {
        TODO("Not yet implemented")
    }

    override fun banIp(reason: String?, expires: Date?, source: String?, kickPlayer: Boolean): BanEntry<InetAddress>? {
        TODO("Not yet implemented")
    }

    override fun banIp(
        reason: String?,
        expires: Instant?,
        source: String?,
        kickPlayer: Boolean
    ): BanEntry<InetAddress>? {
        TODO("Not yet implemented")
    }

    override fun banIp(
        reason: String?,
        duration: Duration?,
        source: String?,
        kickPlayer: Boolean
    ): BanEntry<InetAddress>? {
        TODO("Not yet implemented")
    }

    override fun chat(msg: String) {
        TODO("Not yet implemented")
    }

    override fun performCommand(command: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSneaking(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSneaking(sneak: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSprinting(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSprinting(sprinting: Boolean) {
        TODO("Not yet implemented")
    }

    override fun saveData() {
        TODO("Not yet implemented")
    }

    override fun loadData() {
        TODO("Not yet implemented")
    }

    override fun isSleepingIgnored(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSleepingIgnored(isSleeping: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setBedSpawnLocation(location: Location?) {
        TODO("Not yet implemented")
    }

    override fun setBedSpawnLocation(location: Location?, force: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setRespawnLocation(location: Location?) {
        TODO("Not yet implemented")
    }

    override fun setRespawnLocation(location: Location?, force: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getEnderPearls(): MutableCollection<EnderPearl> {
        TODO("Not yet implemented")
    }

    override fun getCurrentInput(): Input {
        TODO("Not yet implemented")
    }

    override fun playNote(loc: Location, instrument: Byte, note: Byte) {
        TODO("Not yet implemented")
    }

    override fun playNote(loc: Location, instrument: Instrument, note: Note) {
        TODO("Not yet implemented")
    }

    override fun playSound(location: Location, sound: Sound, volume: Float, pitch: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(location: Location, sound: String, volume: Float, pitch: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(location: Location, sound: Sound, category: SoundCategory, volume: Float, pitch: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(location: Location, sound: String, category: SoundCategory, volume: Float, pitch: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(
        location: Location,
        sound: Sound,
        category: SoundCategory,
        volume: Float,
        pitch: Float,
        seed: Long
    ) {
        TODO("Not yet implemented")
    }

    override fun playSound(
        location: Location,
        sound: String,
        category: SoundCategory,
        volume: Float,
        pitch: Float,
        seed: Long
    ) {
        TODO("Not yet implemented")
    }

    override fun playSound(entity: Entity, sound: Sound, volume: Float, pitch: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(entity: Entity, sound: String, volume: Float, pitch: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(entity: Entity, sound: Sound, category: SoundCategory, volume: Float, pitch: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(entity: Entity, sound: String, category: SoundCategory, volume: Float, pitch: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(
        entity: Entity,
        sound: Sound,
        category: SoundCategory,
        volume: Float,
        pitch: Float,
        seed: Long
    ) {
        TODO("Not yet implemented")
    }

    override fun playSound(
        entity: Entity,
        sound: String,
        category: SoundCategory,
        volume: Float,
        pitch: Float,
        seed: Long
    ) {
        TODO("Not yet implemented")
    }

    override fun stopSound(sound: Sound) {
        TODO("Not yet implemented")
    }

    override fun stopSound(sound: String) {
        TODO("Not yet implemented")
    }

    override fun stopSound(sound: Sound, category: SoundCategory?) {
        TODO("Not yet implemented")
    }

    override fun stopSound(sound: String, category: SoundCategory?) {
        TODO("Not yet implemented")
    }

    override fun stopSound(category: SoundCategory) {
        TODO("Not yet implemented")
    }

    override fun stopAllSounds() {
        TODO("Not yet implemented")
    }

    override fun breakBlock(block: Block): Boolean {
        TODO("Not yet implemented")
    }

    override fun sendBlockChange(loc: Location, material: Material, data: Byte) {
        TODO("Not yet implemented")
    }

    override fun sendBlockChange(loc: Location, block: BlockData) {
        TODO("Not yet implemented")
    }

    override fun sendBlockChanges(blocks: MutableCollection<BlockState>) {
        TODO("Not yet implemented")
    }

    override fun sendBlockChanges(blocks: MutableCollection<BlockState>, suppressLightUpdates: Boolean) {
        TODO("Not yet implemented")
    }

    override fun sendBlockDamage(loc: Location, progress: Float) {
        TODO("Not yet implemented")
    }

    override fun sendBlockDamage(loc: Location, progress: Float, source: Entity) {
        TODO("Not yet implemented")
    }

    override fun sendBlockDamage(loc: Location, progress: Float, sourceId: Int) {
        TODO("Not yet implemented")
    }

    override fun sendEquipmentChange(entity: LivingEntity, slot: EquipmentSlot, item: ItemStack?) {
        TODO("Not yet implemented")
    }

    override fun sendEquipmentChange(entity: LivingEntity, items: MutableMap<EquipmentSlot, ItemStack>) {
        TODO("Not yet implemented")
    }

    override fun sendSignChange(loc: Location, lines: Array<out String>?) {
        TODO("Not yet implemented")
    }

    override fun sendSignChange(loc: Location, lines: Array<out String>?, dyeColor: DyeColor) {
        TODO("Not yet implemented")
    }

    override fun sendSignChange(loc: Location, lines: Array<out String>?, dyeColor: DyeColor, hasGlowingText: Boolean) {
        TODO("Not yet implemented")
    }

    override fun sendBlockUpdate(loc: Location, tileState: TileState) {
        TODO("Not yet implemented")
    }

    override fun sendPotionEffectChange(entity: LivingEntity, effect: PotionEffect) {
        TODO("Not yet implemented")
    }

    override fun sendPotionEffectChangeRemove(entity: LivingEntity, type: PotionEffectType) {
        TODO("Not yet implemented")
    }

    override fun sendMap(map: MapView) {
        TODO("Not yet implemented")
    }

    override fun sendHurtAnimation(yaw: Float) {
        TODO("Not yet implemented")
    }

    override fun sendLinks(links: ServerLinks) {
        TODO("Not yet implemented")
    }

    override fun addCustomChatCompletions(completions: MutableCollection<String>) {
        TODO("Not yet implemented")
    }

    override fun removeCustomChatCompletions(completions: MutableCollection<String>) {
        TODO("Not yet implemented")
    }

    override fun setCustomChatCompletions(completions: MutableCollection<String>) {
        TODO("Not yet implemented")
    }

    override fun updateInventory() {
        TODO("Not yet implemented")
    }

    override fun getPreviousGameMode(): GameMode? {
        TODO("Not yet implemented")
    }

    override fun setPlayerTime(time: Long, relative: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPlayerTime(): Long {
        TODO("Not yet implemented")
    }

    override fun getPlayerTimeOffset(): Long {
        TODO("Not yet implemented")
    }

    override fun isPlayerTimeRelative(): Boolean {
        TODO("Not yet implemented")
    }

    override fun resetPlayerTime() {
        TODO("Not yet implemented")
    }

    override fun getPlayerWeather(): WeatherType? {
        TODO("Not yet implemented")
    }

    override fun setPlayerWeather(type: WeatherType) {
        TODO("Not yet implemented")
    }

    override fun resetPlayerWeather() {
        TODO("Not yet implemented")
    }

    override fun getExpCooldown(): Int {
        TODO("Not yet implemented")
    }

    override fun setExpCooldown(ticks: Int) {
        TODO("Not yet implemented")
    }

    override fun giveExp(amount: Int) {
        TODO("Not yet implemented")
    }

    override fun giveExpLevels(amount: Int) {
        TODO("Not yet implemented")
    }

    override fun getExp(): Float {
        TODO("Not yet implemented")
    }

    override fun setExp(exp: Float) {
        TODO("Not yet implemented")
    }

    override fun getLevel(): Int {
        TODO("Not yet implemented")
    }

    override fun setLevel(level: Int) {
        TODO("Not yet implemented")
    }

    override fun getTotalExperience(): Int {
        TODO("Not yet implemented")
    }

    override fun setTotalExperience(exp: Int) {
        TODO("Not yet implemented")
    }

    override fun sendExperienceChange(progress: Float) {
        TODO("Not yet implemented")
    }

    override fun sendExperienceChange(progress: Float, level: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllowFlight(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setAllowFlight(flight: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hidePlayer(player: Player) {
        TODO("Not yet implemented")
    }

    override fun hidePlayer(plugin: Plugin, player: Player) {
        TODO("Not yet implemented")
    }

    override fun showPlayer(player: Player) {
        TODO("Not yet implemented")
    }

    override fun showPlayer(plugin: Plugin, player: Player) {
        TODO("Not yet implemented")
    }

    override fun canSee(player: Player): Boolean {
        TODO("Not yet implemented")
    }

    override fun canSee(entity: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun hideEntity(plugin: Plugin, entity: Entity) {
        TODO("Not yet implemented")
    }

    override fun showEntity(plugin: Plugin, entity: Entity) {
        TODO("Not yet implemented")
    }

    override fun isFlying(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setFlying(value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getFlySpeed(): Float {
        TODO("Not yet implemented")
    }

    override fun setFlySpeed(value: Float) {
        TODO("Not yet implemented")
    }

    override fun getWalkSpeed(): Float {
        TODO("Not yet implemented")
    }

    override fun setWalkSpeed(value: Float) {
        TODO("Not yet implemented")
    }

    override fun setTexturePack(url: String) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(url: String) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(url: String, hash: ByteArray?) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(url: String, hash: ByteArray?, prompt: String?) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(url: String, hash: ByteArray?, force: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(url: String, hash: ByteArray?, prompt: String?, force: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(id: UUID, url: String, hash: ByteArray?, prompt: String?, force: Boolean) {
        TODO("Not yet implemented")
    }

    override fun addResourcePack(id: UUID, url: String, hash: ByteArray?, prompt: String?, force: Boolean) {
        TODO("Not yet implemented")
    }

    override fun removeResourcePack(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeResourcePacks() {
        TODO("Not yet implemented")
    }

    override fun getScoreboard(): Scoreboard {
        TODO("Not yet implemented")
    }

    override fun setScoreboard(scoreboard: Scoreboard) {
        TODO("Not yet implemented")
    }

    override fun getWorldBorder(): WorldBorder? {
        TODO("Not yet implemented")
    }

    override fun setWorldBorder(border: WorldBorder?) {
        TODO("Not yet implemented")
    }

    override fun sendHealthUpdate(health: Double, foodLevel: Int, saturation: Float) {
        TODO("Not yet implemented")
    }

    override fun sendHealthUpdate() {
        TODO("Not yet implemented")
    }

    override fun isHealthScaled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setHealthScaled(scale: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getHealthScale(): Double {
        TODO("Not yet implemented")
    }

    override fun setHealthScale(scale: Double) {
        TODO("Not yet implemented")
    }

    override fun getSpectatorTarget(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setSpectatorTarget(entity: Entity?) {
        TODO("Not yet implemented")
    }

    override fun sendTitle(title: String?, subtitle: String?) {
        TODO("Not yet implemented")
    }

    override fun sendTitle(title: String?, subtitle: String?, fadeIn: Int, stay: Int, fadeOut: Int) {
        TODO("Not yet implemented")
    }

    override fun resetTitle() {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(particle: Particle, location: Location, count: Int) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(particle: Particle, x: Double, y: Double, z: Double, count: Int) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(particle: Particle, location: Location, count: Int, data: T?) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(particle: Particle, x: Double, y: Double, z: Double, count: Int, data: T?) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(
        particle: Particle,
        location: Location,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(
        particle: Particle,
        x: Double,
        y: Double,
        z: Double,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(
        particle: Particle,
        location: Location,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        data: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(
        particle: Particle,
        x: Double,
        y: Double,
        z: Double,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        data: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(
        particle: Particle,
        location: Location,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        extra: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(
        particle: Particle,
        x: Double,
        y: Double,
        z: Double,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        extra: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(
        particle: Particle,
        location: Location,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        extra: Double,
        data: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(
        particle: Particle,
        x: Double,
        y: Double,
        z: Double,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        extra: Double,
        data: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(
        particle: Particle,
        location: Location,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        extra: Double,
        data: T?,
        force: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> spawnParticle(
        particle: Particle,
        x: Double,
        y: Double,
        z: Double,
        count: Int,
        offsetX: Double,
        offsetY: Double,
        offsetZ: Double,
        extra: Double,
        data: T?,
        force: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun getAdvancementProgress(advancement: Advancement): AdvancementProgress {
        TODO("Not yet implemented")
    }

    override fun getClientViewDistance(): Int {
        TODO("Not yet implemented")
    }

    override fun getPing(): Int {
        TODO("Not yet implemented")
    }

    override fun getLocale(): String {
        TODO("Not yet implemented")
    }

    override fun updateCommands() {
        TODO("Not yet implemented")
    }

    override fun openBook(book: ItemStack) {
        TODO("Not yet implemented")
    }

    override fun openSign(sign: Sign) {
        TODO("Not yet implemented")
    }

    override fun openSign(sign: Sign, side: Side) {
        TODO("Not yet implemented")
    }

    override fun showDemoScreen() {
        TODO("Not yet implemented")
    }

    override fun isAllowingServerListings(): Boolean {
        TODO("Not yet implemented")
    }
}