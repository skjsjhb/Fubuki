package moe.skjsjhb.mc.fubuki.server

import moe.skjsjhb.mc.fubuki.util.Slf4jBridgedLogger
import moe.skjsjhb.mc.fubuki.util.Versions
import net.minecraft.server.MinecraftServer
import org.bukkit.*
import org.bukkit.advancement.Advancement
import org.bukkit.block.data.BlockData
import org.bukkit.boss.*
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.PluginCommand
import org.bukkit.command.SimpleCommandMap
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityFactory
import org.bukkit.entity.Player
import org.bukkit.entity.SpawnCategory
import org.bukkit.event.inventory.InventoryType
import org.bukkit.generator.ChunkGenerator
import org.bukkit.help.HelpMap
import org.bukkit.inventory.*
import org.bukkit.loot.LootTable
import org.bukkit.map.MapView
import org.bukkit.packs.DataPackManager
import org.bukkit.packs.ResourcePack
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.ServicesManager
import org.bukkit.plugin.SimplePluginManager
import org.bukkit.plugin.messaging.Messenger
import org.bukkit.profile.PlayerProfile
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.ScoreboardManager
import org.bukkit.structure.StructureManager
import org.bukkit.util.CachedServerIcon
import java.awt.image.BufferedImage
import java.io.File
import java.net.InetAddress
import java.util.*
import java.util.function.Consumer
import java.util.logging.Logger

/**
 * Fubuki implementation of [Server].
 */
class FubukiServer(
    private val nativeServer: MinecraftServer
) : Server {
    private val logger = Slf4jBridgedLogger("Bukkit")
    private val commandMap = SimpleCommandMap(this)
    private val pluginManager = SimplePluginManager(this, commandMap)
    private val scheduler = FubukiScheduler(nativeServer)

    override fun sendPluginMessage(source: Plugin, channel: String, message: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun getListeningPluginChannels(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getName(): String = "Fubuki"

    override fun getVersion(): String = "${Versions.FUBUKI} (MC ${nativeServer.version})"

    override fun getBukkitVersion(): String = Versions.BUKKIT

    override fun getOnlinePlayers(): MutableCollection<out Player> {
        TODO("Not yet implemented")
    }

    override fun getMaxPlayers(): Int = nativeServer.maxPlayerCount

    override fun setMaxPlayers(maxPlayers: Int) {
        TODO("Not yet implemented")
    }

    override fun getPort(): Int = nativeServer.serverPort

    override fun getViewDistance(): Int {
        TODO("Not yet implemented")
    }

    override fun getSimulationDistance(): Int {
        TODO("Not yet implemented")
    }

    override fun getIp(): String = nativeServer.serverIp

    override fun getWorldType(): String {
        TODO("Not yet implemented")
    }

    override fun getGenerateStructures(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getMaxWorldSize(): Int {
        TODO("Not yet implemented")
    }

    override fun getAllowEnd(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAllowNether(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isLoggingIPs(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getInitialEnabledPacks(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun getInitialDisabledPacks(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun getDataPackManager(): DataPackManager {
        TODO("Not yet implemented")
    }

    override fun getServerTickManager(): ServerTickManager {
        TODO("Not yet implemented")
    }

    override fun getServerResourcePack(): ResourcePack? {
        TODO("Not yet implemented")
    }

    override fun getResourcePack(): String {
        TODO("Not yet implemented")
    }

    override fun getResourcePackHash(): String {
        TODO("Not yet implemented")
    }

    override fun getResourcePackPrompt(): String {
        TODO("Not yet implemented")
    }

    override fun isResourcePackRequired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasWhitelist(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setWhitelist(value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isWhitelistEnforced(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setWhitelistEnforced(value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getWhitelistedPlayers(): MutableSet<OfflinePlayer> {
        TODO("Not yet implemented")
    }

    override fun reloadWhitelist() {
        TODO("Not yet implemented")
    }

    override fun broadcastMessage(message: String): Int {
        TODO("Not yet implemented")
    }

    override fun getUpdateFolder(): String = "update"

    override fun getUpdateFolderFile(): File {
        TODO("Not yet implemented")
    }

    override fun getConnectionThrottle(): Long {
        TODO("Not yet implemented")
    }

    override fun getTicksPerAnimalSpawns(): Int {
        TODO("Not yet implemented")
    }

    override fun getTicksPerMonsterSpawns(): Int {
        TODO("Not yet implemented")
    }

    override fun getTicksPerWaterSpawns(): Int {
        TODO("Not yet implemented")
    }

    override fun getTicksPerWaterAmbientSpawns(): Int {
        TODO("Not yet implemented")
    }

    override fun getTicksPerWaterUndergroundCreatureSpawns(): Int {
        TODO("Not yet implemented")
    }

    override fun getTicksPerAmbientSpawns(): Int {
        TODO("Not yet implemented")
    }

    override fun getTicksPerSpawns(spawnCategory: SpawnCategory): Int {
        TODO("Not yet implemented")
    }

    override fun getPlayer(name: String): Player? {
        TODO("Not yet implemented")
    }

    override fun getPlayer(id: UUID): Player? {
        TODO("Not yet implemented")
    }

    override fun getPlayerExact(name: String): Player? {
        TODO("Not yet implemented")
    }

    override fun matchPlayer(name: String): MutableList<Player> {
        TODO("Not yet implemented")
    }

    override fun getPluginManager(): PluginManager = pluginManager

    override fun getScheduler(): BukkitScheduler = scheduler

    override fun getServicesManager(): ServicesManager {
        TODO("Not yet implemented")
    }

    override fun getWorlds(): MutableList<World> {
        TODO("Not yet implemented")
    }

    override fun createWorld(creator: WorldCreator): World? {
        TODO("Not yet implemented")
    }

    override fun unloadWorld(name: String, save: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun unloadWorld(world: World, save: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun getWorld(name: String): World? {
        TODO("Not yet implemented")
    }

    override fun getWorld(uid: UUID): World? {
        TODO("Not yet implemented")
    }

    override fun createWorldBorder(): WorldBorder {
        TODO("Not yet implemented")
    }

    override fun getMap(id: Int): MapView? {
        TODO("Not yet implemented")
    }

    override fun createMap(world: World): MapView {
        TODO("Not yet implemented")
    }

    override fun createExplorerMap(world: World, location: Location, structureType: StructureType): ItemStack {
        TODO("Not yet implemented")
    }

    override fun createExplorerMap(
        world: World,
        location: Location,
        structureType: StructureType,
        radius: Int,
        findUnexplored: Boolean
    ): ItemStack {
        TODO("Not yet implemented")
    }

    override fun reload() {
        TODO("Not yet implemented")
    }

    override fun reloadData() {
        TODO("Not yet implemented")
    }

    override fun getLogger(): Logger = logger

    override fun getPluginCommand(name: String): PluginCommand? {
        TODO("Not yet implemented")
    }

    override fun savePlayers() {
        TODO("Not yet implemented")
    }

    override fun dispatchCommand(sender: CommandSender, commandLine: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun addRecipe(recipe: Recipe?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRecipesFor(result: ItemStack): MutableList<Recipe> {
        TODO("Not yet implemented")
    }

    override fun getRecipe(recipeKey: NamespacedKey): Recipe? {
        TODO("Not yet implemented")
    }

    override fun getCraftingRecipe(craftingMatrix: Array<out ItemStack>, world: World): Recipe? {
        TODO("Not yet implemented")
    }

    override fun craftItem(craftingMatrix: Array<out ItemStack>, world: World, player: Player): ItemStack {
        TODO("Not yet implemented")
    }

    override fun craftItem(craftingMatrix: Array<out ItemStack>, world: World): ItemStack {
        TODO("Not yet implemented")
    }

    override fun craftItemResult(craftingMatrix: Array<out ItemStack>, world: World, player: Player): ItemCraftResult {
        TODO("Not yet implemented")
    }

    override fun craftItemResult(craftingMatrix: Array<out ItemStack>, world: World): ItemCraftResult {
        TODO("Not yet implemented")
    }

    override fun recipeIterator(): MutableIterator<Recipe> {
        TODO("Not yet implemented")
    }

    override fun clearRecipes() {
        TODO("Not yet implemented")
    }

    override fun resetRecipes() {
        TODO("Not yet implemented")
    }

    override fun removeRecipe(key: NamespacedKey): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCommandAliases(): MutableMap<String, Array<String>> {
        TODO("Not yet implemented")
    }

    override fun getSpawnRadius(): Int {
        TODO("Not yet implemented")
    }

    override fun setSpawnRadius(value: Int) {
        TODO("Not yet implemented")
    }

    override fun shouldSendChatPreviews(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnforcingSecureProfiles(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAcceptingTransfers(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getHideOnlinePlayers(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getOnlineMode(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAllowFlight(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isHardcore(): Boolean {
        TODO("Not yet implemented")
    }

    override fun shutdown() {
        nativeServer.stop(false)
    }

    override fun broadcast(message: String, permission: String): Int {
        TODO("Not yet implemented")
    }

    override fun getOfflinePlayer(name: String): OfflinePlayer {
        TODO("Not yet implemented")
    }

    override fun getOfflinePlayer(id: UUID): OfflinePlayer {
        TODO("Not yet implemented")
    }

    override fun createPlayerProfile(uniqueId: UUID?, name: String?): PlayerProfile {
        TODO("Not yet implemented")
    }

    override fun createPlayerProfile(uniqueId: UUID): PlayerProfile {
        TODO("Not yet implemented")
    }

    override fun createPlayerProfile(name: String): PlayerProfile {
        TODO("Not yet implemented")
    }

    override fun getIPBans(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun banIP(address: String) {
        TODO("Not yet implemented")
    }

    override fun banIP(address: InetAddress) {
        TODO("Not yet implemented")
    }

    override fun unbanIP(address: String) {
        TODO("Not yet implemented")
    }

    override fun unbanIP(address: InetAddress) {
        TODO("Not yet implemented")
    }

    override fun getBannedPlayers(): MutableSet<OfflinePlayer> {
        TODO("Not yet implemented")
    }

    override fun <T : BanList<*>?> getBanList(type: BanList.Type): T & Any {
        TODO("Not yet implemented")
    }

    override fun getOperators(): MutableSet<OfflinePlayer> {
        TODO("Not yet implemented")
    }

    override fun getDefaultGameMode(): GameMode {
        TODO("Not yet implemented")
    }

    override fun setDefaultGameMode(mode: GameMode) {
        TODO("Not yet implemented")
    }

    override fun getConsoleSender(): ConsoleCommandSender {
        TODO("Not yet implemented")
    }

    override fun getWorldContainer(): File {
        TODO("Not yet implemented")
    }

    override fun getOfflinePlayers(): Array<OfflinePlayer> {
        TODO("Not yet implemented")
    }

    override fun getMessenger(): Messenger {
        TODO("Not yet implemented")
    }

    override fun getHelpMap(): HelpMap {
        TODO("Not yet implemented")
    }

    override fun createInventory(owner: InventoryHolder?, type: InventoryType): Inventory {
        TODO("Not yet implemented")
    }

    override fun createInventory(owner: InventoryHolder?, type: InventoryType, title: String): Inventory {
        TODO("Not yet implemented")
    }

    override fun createInventory(owner: InventoryHolder?, size: Int): Inventory {
        TODO("Not yet implemented")
    }

    override fun createInventory(owner: InventoryHolder?, size: Int, title: String): Inventory {
        TODO("Not yet implemented")
    }

    override fun createMerchant(title: String?): Merchant {
        TODO("Not yet implemented")
    }

    override fun createMerchant(): Merchant {
        TODO("Not yet implemented")
    }

    override fun getMaxChainedNeighborUpdates(): Int {
        TODO("Not yet implemented")
    }

    override fun getMonsterSpawnLimit(): Int {
        TODO("Not yet implemented")
    }

    override fun getAnimalSpawnLimit(): Int {
        TODO("Not yet implemented")
    }

    override fun getWaterAnimalSpawnLimit(): Int {
        TODO("Not yet implemented")
    }

    override fun getWaterAmbientSpawnLimit(): Int {
        TODO("Not yet implemented")
    }

    override fun getWaterUndergroundCreatureSpawnLimit(): Int {
        TODO("Not yet implemented")
    }

    override fun getAmbientSpawnLimit(): Int {
        TODO("Not yet implemented")
    }

    override fun getSpawnLimit(spawnCategory: SpawnCategory): Int {
        TODO("Not yet implemented")
    }

    override fun isPrimaryThread(): Boolean = Thread.currentThread() == nativeServer.thread

    override fun getMotd(): String = nativeServer.serverMotd

    override fun setMotd(motd: String) {
        nativeServer.setMotd(motd)
    }

    override fun getServerLinks(): ServerLinks {
        TODO("Not yet implemented")
    }

    override fun getShutdownMessage(): String? {
        TODO("Not yet implemented")
    }

    override fun getWarningState(): Warning.WarningState {
        TODO("Not yet implemented")
    }

    override fun getItemFactory(): ItemFactory {
        TODO("Not yet implemented")
    }

    override fun getEntityFactory(): EntityFactory {
        TODO("Not yet implemented")
    }

    override fun getScoreboardManager(): ScoreboardManager? {
        TODO("Not yet implemented")
    }

    override fun getScoreboardCriteria(name: String): Criteria {
        TODO("Not yet implemented")
    }

    override fun getServerIcon(): CachedServerIcon? {
        TODO("Not yet implemented")
    }

    override fun loadServerIcon(file: File): CachedServerIcon {
        TODO("Not yet implemented")
    }

    override fun loadServerIcon(image: BufferedImage): CachedServerIcon {
        TODO("Not yet implemented")
    }

    override fun getIdleTimeout(): Int {
        TODO("Not yet implemented")
    }

    override fun setIdleTimeout(threshold: Int) {
        TODO("Not yet implemented")
    }

    override fun getPauseWhenEmptyTime(): Int {
        TODO("Not yet implemented")
    }

    override fun setPauseWhenEmptyTime(seconds: Int) {
        TODO("Not yet implemented")
    }

    override fun createChunkData(world: World): ChunkGenerator.ChunkData {
        TODO("Not yet implemented")
    }

    override fun createBossBar(title: String?, color: BarColor, style: BarStyle, vararg flags: BarFlag?): BossBar {
        TODO("Not yet implemented")
    }

    override fun createBossBar(
        key: NamespacedKey,
        title: String?,
        color: BarColor,
        style: BarStyle,
        vararg flags: BarFlag?
    ): KeyedBossBar {
        TODO("Not yet implemented")
    }

    override fun getBossBars(): MutableIterator<KeyedBossBar> {
        TODO("Not yet implemented")
    }

    override fun getBossBar(key: NamespacedKey): KeyedBossBar? {
        TODO("Not yet implemented")
    }

    override fun removeBossBar(key: NamespacedKey): Boolean {
        TODO("Not yet implemented")
    }

    override fun getEntity(uuid: UUID): Entity? {
        TODO("Not yet implemented")
    }

    override fun getAdvancement(key: NamespacedKey): Advancement? {
        TODO("Not yet implemented")
    }

    override fun advancementIterator(): MutableIterator<Advancement> {
        TODO("Not yet implemented")
    }

    override fun createBlockData(material: Material): BlockData {
        TODO("Not yet implemented")
    }

    override fun createBlockData(material: Material, consumer: Consumer<in BlockData>?): BlockData {
        TODO("Not yet implemented")
    }

    override fun createBlockData(data: String): BlockData {
        TODO("Not yet implemented")
    }

    override fun createBlockData(material: Material?, data: String?): BlockData {
        TODO("Not yet implemented")
    }

    override fun <T : Keyed?> getTag(registry: String, tag: NamespacedKey, clazz: Class<T>): Tag<T>? {
        TODO("Not yet implemented")
    }

    override fun <T : Keyed?> getTags(registry: String, clazz: Class<T>): MutableIterable<Tag<T>> {
        TODO("Not yet implemented")
    }

    override fun getLootTable(key: NamespacedKey): LootTable? {
        TODO("Not yet implemented")
    }

    override fun selectEntities(sender: CommandSender, selector: String): MutableList<Entity> {
        TODO("Not yet implemented")
    }

    override fun getStructureManager(): StructureManager {
        TODO("Not yet implemented")
    }

    override fun <T : Keyed?> getRegistry(tClass: Class<T>): Registry<T>? {
        TODO("Not yet implemented")
    }


    private val unsafeValues = FubukiUnsafeValues()

    @Deprecated("Deprecated in Java")
    override fun getUnsafe(): UnsafeValues = unsafeValues
}