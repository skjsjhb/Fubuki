package moe.skjsjhb.mc.fubuki.mixin.network;

import moe.skjsjhb.mc.fubuki.network.HostnameContainer;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.server.network.ServerHandshakeNetworkHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerHandshakeNetworkHandler.class)
public class HostnameInHandshakeMixin {
    @Shadow
    @Final
    private ClientConnection connection;

    @Inject(method = "onHandshake", at = @At("HEAD"))
    public void onHandshake(HandshakeC2SPacket packet, CallbackInfo ci) {
        ((HostnameContainer) connection).fubuki$setHostname(packet.address() + ":" + packet.port());
    }
}
