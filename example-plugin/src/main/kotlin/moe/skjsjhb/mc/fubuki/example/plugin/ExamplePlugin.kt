package moe.skjsjhb.mc.fubuki.example.plugin

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
    }

    override fun onDisable() {
        logger.info("OK I'm disabling.")
    }
}