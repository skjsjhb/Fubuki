package moe.skjsjhb.mc.fubuki

import moe.skjsjhb.mc.fubuki.event.PlayerEventInit
import moe.skjsjhb.mc.fubuki.schedule.ServerThreadExecutor
import moe.skjsjhb.mc.fubuki.server.ServerInit
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

/**
 * Main mod entry.
 */
object Fubuki : ModInitializer {
    private val logger = LoggerFactory.getLogger("Fubuki")

    override fun onInitialize() {
        logger.info("This is Fubuki, a Bukkit API translator for Fabric.")

        ServerThreadExecutor.init()
        ServerInit.init()
        PlayerEventInit.init()
    }
}