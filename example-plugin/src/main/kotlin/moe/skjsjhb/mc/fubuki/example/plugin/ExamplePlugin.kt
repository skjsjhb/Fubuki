package moe.skjsjhb.mc.fubuki.example.plugin

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.server.ServerCommandEvent
import org.bukkit.plugin.java.JavaPlugin

class ExamplePlugin : JavaPlugin() {
    override fun onLoad() {
        logger.info("Hello from example plugin!")
        logger.info("This server is running ${server.version}")
    }

    override fun onEnable() {
        saveDefaultConfig()

        server.maxPlayers = 100
        server.motd = "Fubuki is a Bukkit API translator"
        server.pluginManager.registerEvents(EventListener, this)
        server.getPluginCommand("fubuki")?.setExecutor { sender, command, label, args ->
            logger.info("Received command invocation: $label ${args.joinToString(" ")}")
            true
        }
        server.getPluginCommand("fubuki")?.setTabCompleter { sender, command, label, args ->
            listOf("version", "reload")
        }
    }

    override fun onDisable() {
        logger.info("OK I'm disabling.")
    }
}

private object EventListener : Listener {
    @EventHandler
    fun onPlayerJoin(ev: PlayerJoinEvent) {
        println("Player ${ev.player.name} joined!")
    }

    @EventHandler
    fun onPlayerQuit(ev: PlayerQuitEvent) {
        println("Player ${ev.player.name} left!")
    }

    @EventHandler
    fun onPlayerLogin(ev: PlayerLoginEvent) {
        println("Player ${ev.player.name} joined with host ${ev.hostname} and address ${ev.address}")
    }

    @EventHandler
    fun onPlayerCommand(ev: PlayerCommandPreprocessEvent) {
        if (ev.message == "say hello") {
            ev.isCancelled = true
        }
    }

    @EventHandler
    fun onServerCommand(ev: ServerCommandEvent) {
        if (ev.command == "say hello") {
            ev.isCancelled = true
        }
    }
}