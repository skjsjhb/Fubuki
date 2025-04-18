package moe.skjsjhb.mc.fubuki.mixin.command;

import com.mojang.brigadier.tree.CommandNode;
import moe.skjsjhb.mc.fubuki.command.CommandRemovable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

/**
 * Mixin to access internals of command nodes and remove the children.
 * <p>
 * Mixin is used as access wideners seem not to work with brigadier classes.
 */
@Mixin(CommandNode.class)
public class CommandRemovalMixin implements CommandRemovable {
    @Shadow
    @Final
    private Map<String, ?> literals;

    @Shadow
    @Final
    private Map<String, ?> arguments;

    @Shadow
    @Final
    private Map<String, ?> children;

    @Override
    public void fubuki$removeCommand(@NotNull CommandNode<?> node, @NotNull String cmd) {
        var child = node.getChild(cmd);
        literals.remove(cmd, child);
        arguments.remove(cmd, child);
        children.remove(cmd, child);
    }
}
