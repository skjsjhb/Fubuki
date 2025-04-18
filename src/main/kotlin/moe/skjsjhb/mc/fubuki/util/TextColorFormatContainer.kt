package moe.skjsjhb.mc.fubuki.util

import net.minecraft.util.Formatting

@Suppress("FunctionName")
interface TextColorFormatContainer {
    fun `fubuki$setFormat`(fmt: Formatting?)

    fun `fubuki$getFormat`(): Formatting?
}