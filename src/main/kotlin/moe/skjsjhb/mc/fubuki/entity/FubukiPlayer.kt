package moe.skjsjhb.mc.fubuki.entity

import moe.skjsjhb.mc.fubuki.server.toBukkit
import moe.skjsjhb.mc.fubuki.server.toMojang
import moe.skjsjhb.mc.fubuki.util.CraftTextConversion
import net.minecraft.server.network.ServerPlayerEntity
import org.bukkit.*
import org.bukkit.advancement.Advancement
import org.bukkit.advancement.AdvancementProgress
import org.bukkit.block.Block
import org.bukkit.block.BlockState
import org.bukkit.block.Sign
import org.bukkit.block.TileState
import org.bukkit.block.data.BlockData
import org.bukkit.block.sign.Side
import org.bukkit.conversations.Conversation
import org.bukkit.conversations.ConversationAbandonedEvent
import org.bukkit.entity.*
import org.bukkit.inventory.*
import org.bukkit.map.MapView
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.profile.PlayerProfile
import org.bukkit.scoreboard.Scoreboard
import java.net.InetAddress
import java.net.InetSocketAddress
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.CompletableFuture

@Suppress("OVERRIDE_DEPRECATION", "REMOVAL", "DEPRECATION")
class FubukiPlayer(
    override val delegate: ServerPlayerEntity
) : FubukiLivingEntity(delegate), Player {
    override fun toMojang(): ServerPlayerEntity = delegate

    override fun playEffect(loc: Location, effect: Effect, data: Int) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(message: String) {
        // TODO
    }

    override fun sendMessage(vararg messages: String?) {
        // TODO
    }

    override fun sendMessage(sender: UUID?, message: String) {
        // TODO
    }

    override fun sendMessage(sender: UUID?, vararg messages: String?) {
        // TODO
    }

    override fun <T : Any?> playEffect(loc: Location, effect: Effect, data: T?) {
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

    override fun getGameMode(): GameMode = delegate.gameMode.toBukkit()

    override fun setGameMode(mode: GameMode) {
        delegate.changeGameMode(mode.toMojang())
    }

    override fun isBlocking(): Boolean = delegate.isBlocking

    override fun isHandRaised(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getExpToLevel(): Int = delegate.nextLevelExperience - delegate.totalExperience

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

    override fun getExhaustion(): Float = delegate.hungerManager.exhaustion

    override fun setExhaustion(value: Float) {
        delegate.hungerManager.exhaustion = value
    }

    override fun getSaturation(): Float = delegate.hungerManager.saturationLevel

    override fun setSaturation(value: Float) {
        delegate.hungerManager.saturationLevel = value
    }

    override fun getFoodLevel(): Int = delegate.hungerManager.foodLevel

    override fun setFoodLevel(value: Int) {
        delegate.hungerManager.foodLevel = value
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

    override fun sendRawMessage(message: String) = sendRawMessage(null, message)

    override fun sendRawMessage(sender: UUID?, message: String) {
        CraftTextConversion.fromString(message).forEach {
            delegate.sendMessage(it)
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun isOnline(): Boolean = !delegate.isDisconnected

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

    override fun isSneaking(): Boolean = delegate.isSneaking

    override fun setSneaking(sneak: Boolean) {
        delegate.isSneaking = sneak
    }

    override fun isSprinting(): Boolean = delegate.isSprinting

    override fun setSprinting(sprinting: Boolean) {
        delegate.isSprinting = sprinting
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
        // TODO filter it
        return true
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

    override fun remove() {
        throw UnsupportedOperationException("Cannot remove a player entity")
    }

    override fun copy(): Entity {
        throw UnsupportedOperationException("Cannot copy a player")
    }

    override fun copy(to: Location): Entity {
        throw UnsupportedOperationException("Cannot copy a player")
    }
}