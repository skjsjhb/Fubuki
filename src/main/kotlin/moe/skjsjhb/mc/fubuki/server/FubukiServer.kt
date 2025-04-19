package moe.skjsjhb.mc.fubuki.server

import com.mojang.authlib.GameProfile
import moe.skjsjhb.mc.fubuki.command.CommandRemovable
import moe.skjsjhb.mc.fubuki.command.FubukiCommand
import moe.skjsjhb.mc.fubuki.command.FubukiConsoleCommandSender
import moe.skjsjhb.mc.fubuki.entity.toBukkit
import moe.skjsjhb.mc.fubuki.interop.BukkitRef
import moe.skjsjhb.mc.fubuki.ipc.FubukiMessenger
import moe.skjsjhb.mc.fubuki.pm.PluginLifecycleManager
import moe.skjsjhb.mc.fubuki.schedule.FubukiScheduler
import moe.skjsjhb.mc.fubuki.services.FubukiServicesManager
import moe.skjsjhb.mc.fubuki.util.Slf4jBridgedLogger
import moe.skjsjhb.mc.fubuki.util.Versions
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ReloadCommand
import net.minecraft.server.dedicated.MinecraftDedicatedServer
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
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer
import java.util.logging.Logger
import kotlin.jvm.optionals.getOrNull
import kotlin.math.abs

/**
 * Fubuki implementation of [Server].
 */
@Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
class FubukiServer(
    private val nativeServer: MinecraftDedicatedServer
) : Server {
    init {
        (nativeServer as BukkitRef).`fubuki$setRef`(this)
    }

    fun toMojang() = nativeServer

    private val logger = Slf4jBridgedLogger("Bukkit")
    private val commandMap = SimpleCommandMap(this)
    private val pluginManager = SimplePluginManager(this, commandMap)
    private val scheduler = FubukiScheduler(nativeServer)
    private val servicesManager = FubukiServicesManager()
    private val messenger = FubukiMessenger()
    private val unsafeValues = FubukiUnsafeValues()
    private val serverTickManager = FubukiServerTickManager(nativeServer.tickManager)
    private val consoleCommandSender = FubukiConsoleCommandSender(nativeServer.commandSource)
    private val offlinePlayers = ConcurrentHashMap<UUID, FubukiOfflinePlayer>()

    private val playerListView by lazy {
        PlayerListView(Collections.unmodifiableList(nativeServer.playerManager.playerList))
    }

    val pluginLifecycleManager = PluginLifecycleManager(pluginManager)

    override fun sendPluginMessage(source: Plugin, channel: String, message: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun getListeningPluginChannels(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getName(): String = "Fubuki"

    override fun getVersion(): String = "${Versions.FUBUKI} (MC ${nativeServer.version})"

    override fun getBukkitVersion(): String = Versions.BUKKIT

    override fun getOnlinePlayers(): MutableCollection<out Player> = playerListView

    override fun getMaxPlayers(): Int = nativeServer.maxPlayerCount

    override fun setMaxPlayers(maxPlayers: Int) {
        nativeServer.playerManager.maxPlayers = maxPlayers
    }

    override fun getPort(): Int = nativeServer.serverPort

    override fun getViewDistance(): Int = nativeServer.playerManager.viewDistance

    override fun getSimulationDistance(): Int = nativeServer.playerManager.simulationDistance

    override fun getIp(): String = nativeServer.serverIp

    override fun getWorldType(): String =
        nativeServer.properties.worldGenProperties.levelType

    override fun getGenerateStructures(): Boolean =
        nativeServer.properties.generatorOptions.shouldGenerateStructures()

    override fun getMaxWorldSize(): Int =
        nativeServer.maxWorldBorderRadius

    override fun getAllowEnd(): Boolean = true // TODO support disabling end world

    override fun getAllowNether(): Boolean =
        nativeServer.properties.allowNether

    override fun isLoggingIPs(): Boolean =
        nativeServer.shouldLogIps()

    override fun getInitialEnabledPacks(): MutableList<String> =
        nativeServer.properties.dataPackSettings.enabled

    override fun getInitialDisabledPacks(): MutableList<String> =
        nativeServer.properties.dataPackSettings.disabled

    override fun getDataPackManager(): DataPackManager {
        TODO("Not yet implemented")
    }

    override fun getServerTickManager(): ServerTickManager = serverTickManager

    override fun getServerResourcePack(): ResourcePack? =
        nativeServer.resourcePackProperties.getOrNull()?.let { FubukiResourcePack(it) }

    override fun getResourcePack(): String =
        nativeServer.resourcePackProperties.getOrNull()?.url.orEmpty()

    override fun getResourcePackHash(): String =
        nativeServer.resourcePackProperties.getOrNull()?.hash.orEmpty()

    override fun getResourcePackPrompt(): String =
        nativeServer.resourcePackProperties.getOrNull()?.prompt?.literalString.orEmpty()

    override fun isResourcePackRequired(): Boolean =
        nativeServer.resourcePackProperties.getOrNull()?.isRequired ?: false

    override fun hasWhitelist(): Boolean =
        nativeServer.playerManager.isWhitelistEnabled

    override fun setWhitelist(value: Boolean) {
        nativeServer.playerManager.isWhitelistEnabled = value
    }

    override fun isWhitelistEnforced(): Boolean =
        nativeServer.isEnforceWhitelist

    override fun setWhitelistEnforced(value: Boolean) {
        nativeServer.isEnforceWhitelist = value
    }

    override fun getWhitelistedPlayers(): MutableSet<OfflinePlayer> {
        TODO("Not yet implemented")
    }

    override fun reloadWhitelist() {
        nativeServer.playerManager.reloadWhitelist()
    }

    override fun broadcastMessage(message: String): Int {
        TODO("Not yet implemented")
    }

    // Bukkit configuration begin
    // Bukkit configurations are not supported yet so default values are used

    override fun getUpdateFolder(): String = "update"

    override fun getUpdateFolderFile(): File = File(updateFolder)

    override fun getConnectionThrottle(): Long = 0

    override fun getTicksPerAnimalSpawns(): Int = 400

    override fun getTicksPerMonsterSpawns(): Int = 1

    override fun getTicksPerWaterSpawns(): Int = 1

    override fun getTicksPerWaterAmbientSpawns(): Int = 1

    override fun getTicksPerWaterUndergroundCreatureSpawns(): Int = 1

    override fun getTicksPerAmbientSpawns(): Int = 1

    override fun getTicksPerSpawns(spawnCategory: SpawnCategory): Int = 1

    // Bukkit configuration end

    override fun getPlayer(name: String): Player? {
        getPlayerExact(name)?.let { return it }

        return onlinePlayers.filter { it.name.lowercase().startsWith(name.lowercase()) }
            .minByOrNull { abs(it.name.length - name.length) }
    }

    override fun getPlayer(id: UUID): Player? =
        nativeServer.playerManager.getPlayer(id)?.toBukkit()

    override fun getPlayerExact(name: String): Player? =
        nativeServer.playerManager.getPlayer(name)?.toBukkit()

    override fun matchPlayer(name: String): MutableList<Player> {
        getPlayer(name)?.let { return mutableListOf(it) }

        return onlinePlayers.filter { it.name.lowercase().contains(name.lowercase()) }.toMutableList()
    }

    override fun getPluginManager(): PluginManager = pluginManager

    override fun getScheduler(): BukkitScheduler = scheduler

    override fun getServicesManager(): ServicesManager = servicesManager

    override fun getWorlds(): MutableList<World> {
        // TODO implement worlds
        return mutableListOf()
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
        // Fubuki does not support reloading
        throw UnsupportedOperationException("Server reloading is not supported due to mod compatibility concerns")
    }

    override fun reloadData() {
        val packs = ReloadCommand.findNewDataPacks(
            nativeServer.dataPackManager,
            nativeServer.saveProperties,
            nativeServer.dataPackManager.enabledIds
        )

        nativeServer.reloadResources(packs)
    }

    override fun getLogger(): Logger = logger

    override fun getPluginCommand(name: String): PluginCommand? =
        commandMap.getCommand(name) as? PluginCommand

    override fun savePlayers() {
        nativeServer.playerManager.saveAllPlayerData()
    }

    override fun dispatchCommand(sender: CommandSender, commandLine: String): Boolean =
        commandMap.dispatch(sender, commandLine)

    fun requestTabComplete(player: Player, message: String): List<String> {
        val cmd = if (message.startsWith("/")) message.drop(1) else message
        return commandMap.tabComplete(player, cmd) ?: emptyList() // TODO support location
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

    override fun getSpawnRadius(): Int = nativeServer.spawnProtectionRadius

    override fun setSpawnRadius(value: Int) {
        nativeServer.properties.spawnProtection = value
    }

    override fun shouldSendChatPreviews(): Boolean = false  // Seems already removed by Mojang

    override fun isEnforcingSecureProfiles(): Boolean = nativeServer.shouldEnforceSecureProfile()

    override fun isAcceptingTransfers(): Boolean =
        nativeServer.acceptsTransfers()

    override fun getHideOnlinePlayers(): Boolean =
        nativeServer.hideOnlinePlayers()

    override fun getOnlineMode(): Boolean = nativeServer.isOnlineMode

    override fun getAllowFlight(): Boolean = nativeServer.isFlightEnabled

    override fun isHardcore(): Boolean = nativeServer.isHardcore

    override fun shutdown() {
        nativeServer.stop(false)
    }

    override fun broadcast(message: String, permission: String): Int {
        TODO("Not yet implemented")
    }

    override fun getOfflinePlayer(name: String): OfflinePlayer {
        val rp = getPlayerExact(name)
        if (rp != null) {
            offlinePlayers.remove(rp.uniqueId)
            return rp
        }

        var profile: GameProfile? = null
        if (onlineMode) {
            profile = nativeServer.userCache?.findByName(name)?.getOrNull()
        }

        val p = if (profile == null) {
            // Make a fake profile
            val uuid = UUID.nameUUIDFromBytes("OfflinePlayer:$name".toByteArray())
            FubukiOfflinePlayer(GameProfile(uuid, name), this)
        } else {
            FubukiOfflinePlayer(profile, this)
        }

        offlinePlayers[p.uniqueId] = p
        return p
    }

    override fun getOfflinePlayer(id: UUID): OfflinePlayer {
        val rp = getPlayer(id)
        if (rp != null) {
            offlinePlayers.remove(id)
            return rp
        }

        return FubukiOfflinePlayer(GameProfile(id, ""), this).also {
            offlinePlayers[it.uniqueId] = it
        }
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

    override fun getIPBans(): MutableSet<String> =
        nativeServer.playerManager.ipBanList.map.keys.toMutableSet()

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

    override fun getOperators(): MutableSet<OfflinePlayer> =
        nativeServer.playerManager.opList.values().mapNotNull {
            it.key?.let {
                FubukiOfflinePlayer(it, this).also {
                    offlinePlayers[it.uniqueId] = it
                }
            }
        }.toMutableSet()

    override fun getDefaultGameMode(): GameMode = nativeServer.defaultGameMode.toBukkit()

    override fun setDefaultGameMode(mode: GameMode) {
        nativeServer.defaultGameMode = mode.toMojang()
    }

    override fun getConsoleSender(): ConsoleCommandSender = consoleCommandSender

    override fun getWorldContainer(): File = File(".")

    override fun getOfflinePlayers(): Array<OfflinePlayer> {
        TODO("Not yet implemented")
    }

    override fun getMessenger(): Messenger = messenger

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

    override fun getMaxChainedNeighborUpdates(): Int = nativeServer.maxChainedNeighborUpdates

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

    override fun isPrimaryThread(): Boolean = nativeServer.isOnThread

    override fun getMotd(): String = nativeServer.serverMotd

    override fun setMotd(motd: String) {
        nativeServer.motd = motd
    }

    override fun getServerLinks(): ServerLinks {
        TODO("Not yet implemented")
    }

    override fun getShutdownMessage(): String? {
        // This should be loaded from Bukkit configuration
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

    override fun getIdleTimeout(): Int = nativeServer.playerIdleTimeout

    override fun setIdleTimeout(threshold: Int) {
        nativeServer.playerIdleTimeout = threshold
    }

    override fun getPauseWhenEmptyTime(): Int = nativeServer.pauseWhenEmptySeconds

    override fun setPauseWhenEmptyTime(seconds: Int) {
        nativeServer.properties.pauseWhenEmptySeconds = seconds
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
        nativeServer.worlds.forEach {
            it.getEntity(uuid)?.let { return it.toBukkit() }
        }

        return null
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

    override fun getUnsafe(): UnsafeValues = unsafeValues

    fun postSetup() {
        commandMap.setFallbackCommands()

        val disp = nativeServer.commandManager.dispatcher

        disp.root.children.toSet().forEach {
            disp.register(
                CommandManager.literal("minecraft:" + it.name)
                    .redirect(it)   // Redirect arguments
                    .executes(it.command)   // No-arg variant
            )
        }

        commandMap.knownCommands.forEach { (name, cmd) ->
            val fc = FubukiCommand(this, cmd)
            (disp.root as CommandRemovable).`fubuki$removeCommand`(name)
            fc.register(disp, name)
        }
    }

    /**
     * Executes the specified function on the main server thread.
     */
    fun runOnMainThread(fn: () -> Unit) {
        if (isPrimaryThread) {
            fn()
        } else {
            scheduler.runOnMainThread(fn)
        }
    }
}