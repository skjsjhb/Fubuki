package moe.skjsjhb.mc.fubuki.example.plugin

import org.bukkit.Bukkit
import org.bukkit.metadata.FixedMetadataValue
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
        var found = false
        server.scheduler.runTaskTimer(this, Runnable {
            logger.info("Online players: ${server.onlinePlayers}")

            Bukkit.getPlayer("ThatRarityEG")?.let {
                found = true
                it.setMetadata("ciallo", FixedMetadataValue(this, "world"))
                logger.info(it.getMetadata("ciallo").joinToString { ", " })
                it.removeMetadata("ciallo", this)
                logger.info(it.getMetadata("ciallo").joinToString { ", " })
            }
        }, 0, 20)
    }

    override fun onDisable() {
        logger.info("OK I'm disabling.")
    }
}