package moe.skjsjhb.mc.fubuki.example.plugin

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class ExamplePlugin : JavaPlugin() {
    override fun onLoad() {
        logger.info("Hello from example plugin!")
        logger.info("This server is running ${server.version}")
    }

    override fun onEnable() {
        saveDefaultConfig()

        if (config.getBoolean("enabled")) {
            object : BukkitRunnable() {
                override fun run() {
                    logger.info("I'm going to sleep for 2s.")
                    Thread.sleep(2000)
                    logger.info("Awaken from sleep without blocking!")
                }
            }.runTaskAsynchronously(this)
        }
    }

    override fun onDisable() {
        logger.info("OK I'm disabling.")
    }
}