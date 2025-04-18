package moe.skjsjhb.mc.fubuki.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import moe.skjsjhb.mc.fubuki.entity.toBukkit
import moe.skjsjhb.mc.fubuki.server.FubukiServer
import net.minecraft.server.command.ServerCommandSource
import org.bukkit.command.Command
import org.bukkit.command.CommandException
import java.util.concurrent.CompletableFuture
import java.util.function.Predicate

class FubukiCommand(
    private val server: FubukiServer,
    private val command: Command
) : com.mojang.brigadier.Command<ServerCommandSource>,
    Predicate<ServerCommandSource>,
    SuggestionProvider<ServerCommandSource> {

    fun register(
        dispatcher: CommandDispatcher<ServerCommandSource?>,
        label: String?
    ): LiteralCommandNode<ServerCommandSource?> =
        dispatcher.register(
            LiteralArgumentBuilder
                .literal<ServerCommandSource>(label)
                .requires(this)
                .executes(this)
                .then(
                    RequiredArgumentBuilder
                        .argument<ServerCommandSource, String>("args", StringArgumentType.greedyString())
                        .suggests(this)
                        .executes(this)
                )
        )

    override fun test(source: ServerCommandSource): Boolean =
        command.testPermissionSilent(source.player?.toBukkit() ?: server.consoleSender)

    override fun run(context: CommandContext<ServerCommandSource>): Int {
        val sender = context.source.player?.toBukkit() ?: server.consoleSender

        return try {
            if (server.dispatchCommand(sender, context.input)) 1 else 0
        } catch (ex: CommandException) {
            // TODO log and tell such failure
            0
        }
    }

    override fun getSuggestions(
        context: CommandContext<ServerCommandSource>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        val player = context.source.player ?: return builder.buildFuture()
        val results = server.requestTabComplete(player.toBukkit(), builder.input)
        val nb = builder.createOffset(builder.input.lastIndexOf(' ') + 1)

        results.forEach {
            nb.suggest(it)
        }

        return nb.buildFuture()
    }
}