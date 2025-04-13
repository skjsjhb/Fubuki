package moe.skjsjhb.mc.fubuki.example.plugin

import org.bukkit.plugin.java.JavaPlugin

class ExamplePlugin : JavaPlugin() {
    override fun onLoad() {
        logger.info("Hello from example plugin!")
        logger.info("This server is running ${server.version}")
    }

    override fun onEnable() {
        saveDefaultConfig()

        if (config.getBoolean("enabled")) {
            logger.info("I'm stopping the server!")
            server.scheduler.runTask(this, Runnable {
                assert(server.isPrimaryThread) { "I suppose I should be on the main thread!" }
                server.shutdown()
            })
        }
    }

    override fun onDisable() {
        logger.info("OK I'm disabling.")
    }
}