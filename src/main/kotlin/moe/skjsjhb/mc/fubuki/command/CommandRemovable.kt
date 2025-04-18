package moe.skjsjhb.mc.fubuki.command

import com.mojang.brigadier.tree.CommandNode

@Suppress("FunctionName")
interface CommandRemovable {
    fun `fubuki$removeCommand`(node: CommandNode<*>, cmd: String)
}