package moe.skjsjhb.mc.fubuki.mixin.command;

import com.mojang.brigadier.ParseResults;
import moe.skjsjhb.mc.fubuki.command.CommandExecutionReceiver;
import net.minecraft.network.message.LastSeenMessageList;
import net.minecraft.network.packet.c2s.play.ChatCommandSignedC2SPacket;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class CommandInterceptMixin {
    @Shadow
    protected abstract void validateMessage(String message, Runnable callback);

    @Shadow
    protected abstract void checkForSpam();

    @Shadow
    protected abstract void executeCommand(String command);

    @Shadow
    protected abstract ParseResults<ServerCommandSource> parse(String command);

    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    protected abstract Optional<LastSeenMessageList> validateAcknowledgment(LastSeenMessageList.Acknowledgment acknowledgment);

    @Shadow
    protected abstract void handleCommandExecution(ChatCommandSignedC2SPacket packet, LastSeenMessageList lastSeenMessages);

    // Inject for handling commands from chat message
    // This method must be placed after message validation or the server will reject it
    @Inject(method = "onChatCommandSigned", at = @At("HEAD"), cancellable = true)
    public void interceptChatCommand(ChatCommandSignedC2SPacket packet, CallbackInfo ci) {
        Optional<LastSeenMessageList> lastSeenMessages = validateAcknowledgment(packet.lastSeenMessages());

        if (lastSeenMessages.isPresent()) {
            // Skip argument signature validation
            validateMessage(packet.command(), () -> {
                CommandExecutionReceiver.INSTANCE.onClientCommand(
                        player.server,
                        player.getCommandSource(),
                        packet.command(),
                        () -> handleCommandExecution(packet, lastSeenMessages.get())
                );
                checkForSpam();
            });
            ci.cancel();
        }
    }

    @Inject(method = "onCommandExecution", at = @At("HEAD"), cancellable = true)
    public void interceptNonChatCommand(CommandExecutionC2SPacket packet, CallbackInfo ci) {
        // Same as above
        validateMessage(packet.command(), () -> {
            CommandExecutionReceiver.INSTANCE.onClientCommand(
                    player.server,
                    player.getCommandSource(),
                    packet.command(),
                    () -> executeCommand(packet.command())
            );
            checkForSpam();
        });
        ci.cancel();
    }
}