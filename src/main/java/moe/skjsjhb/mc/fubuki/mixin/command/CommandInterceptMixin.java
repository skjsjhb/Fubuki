package moe.skjsjhb.mc.fubuki.mixin.command;

import com.mojang.brigadier.ParseResults;
import moe.skjsjhb.mc.fubuki.command.CommandExecutionReceiver;
import net.minecraft.command.argument.SignedArgumentList;
import net.minecraft.network.message.LastSeenMessageList;
import net.minecraft.network.message.MessageChain;
import net.minecraft.network.message.SignedMessage;
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

import java.util.Map;
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
    public ServerPlayerEntity player;

    @Shadow
    protected abstract Optional<LastSeenMessageList> validateAcknowledgment(LastSeenMessageList.Acknowledgment acknowledgment);

    @Shadow
    protected abstract void handleCommandExecution(ChatCommandSignedC2SPacket packet, LastSeenMessageList lastSeenMessages);

    @Shadow
    protected abstract <S> Map<String, SignedMessage> collectArgumentMessages(ChatCommandSignedC2SPacket packet, SignedArgumentList<S> arguments, LastSeenMessageList lastSeenMessages) throws MessageChain.MessageChainException;

    @Shadow
    protected abstract ParseResults<ServerCommandSource> parse(String command);

    @Shadow
    protected abstract void handleMessageChainException(MessageChain.MessageChainException exception);

    // Inject for handling commands from chat message
    // This method must be placed after message validation or the server will reject it
    @Inject(method = "onChatCommandSigned", at = @At("HEAD"), cancellable = true)
    public void interceptChatCommand(ChatCommandSignedC2SPacket packet, CallbackInfo ci) {
        // Skip argument signature validation
        validateMessage(packet.command(), () -> {
            Optional<LastSeenMessageList> lastSeenMessages = validateAcknowledgment(packet.lastSeenMessages());
            if (lastSeenMessages.isPresent()) {
                CommandExecutionReceiver.INSTANCE.onClientCommand(
                        player.server,
                        player.getCommandSource(),
                        packet.command(),
                        () -> {
                            handleCommandExecution(packet, lastSeenMessages.get());
                        },
                        () -> {
                            ParseResults<ServerCommandSource> parseResults = parse(packet.command());

                            try {
                                collectArgumentMessages(packet, SignedArgumentList.of(parseResults), lastSeenMessages.get());
                            } catch (MessageChain.MessageChainException ex) {
                                handleMessageChainException(ex);
                            }
                        }
                );
                checkForSpam();
            }
        });
        ci.cancel();
    }

    @Inject(method = "onCommandExecution", at = @At("HEAD"), cancellable = true)
    public void interceptNonChatCommand(CommandExecutionC2SPacket packet, CallbackInfo ci) {
        // Same as above
        validateMessage(packet.command(), () -> {
            CommandExecutionReceiver.INSTANCE.onClientCommand(
                    player.server,
                    player.getCommandSource(),
                    packet.command(),
                    () -> executeCommand(packet.command()),
                    () -> {
                    }
            );
            checkForSpam();
        });
        ci.cancel();
    }
}