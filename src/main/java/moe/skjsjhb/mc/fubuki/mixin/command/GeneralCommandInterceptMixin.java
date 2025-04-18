package moe.skjsjhb.mc.fubuki.mixin.command;

import moe.skjsjhb.mc.fubuki.command.CommandExecutionReceiver;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public abstract class GeneralCommandInterceptMixin {
    // We're using this inject point as it seems that no player command uses it (server only?)
    @Inject(method = "executeWithPrefix", at = @At("HEAD"), cancellable = true)
    public void interceptCommands(ServerCommandSource source, String command, CallbackInfo ci) {
        command = command.startsWith("/") ? command.substring(1) : command;
        CommandExecutionReceiver.INSTANCE.onGeneralCommand(source, command);
        ci.cancel();
    }
}
