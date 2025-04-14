package moe.skjsjhb.mc.fubuki.player

/**
 * Duck interface for carrying a [FubukiPlayer].
 */
@Suppress("FunctionName")
interface PlayerRef {
    fun `fubuki$getPlayer`(): FubukiPlayer
}