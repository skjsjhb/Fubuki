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
            logger.info("The counter should be correct and get cancelled after 1s.")

            var ticks = 0
            val tk = server.scheduler.runTaskTimer(this, Runnable {
                ticks++
            }, 0, 0)

            object : BukkitRunnable() {
                override fun run() {
                    logger.info("Now I'm cancelling the counter.")
                    tk.cancel()
                }
            }.runTaskLater(this, 20)

            logger.info("I'm stopping the server in 40 ticks!")
            server.scheduler.runTaskLater(this, Runnable {
                assert(ticks <= 20)
                assert(tk.isCancelled)
                assert(server.isPrimaryThread)
                server.shutdown()
            }, 40)
        }
    }

    override fun onDisable() {
        logger.info("OK I'm disabling.")
    }
}