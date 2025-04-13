package moe.skjsjhb.mc.fubuki.mixin.server;


import moe.skjsjhb.mc.fubuki.server.ServerMixinReceivers;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class ServerPostWorldDetectionMixin {
    @Inject(method = "loadWorld", at = @At(value = "RETURN"))
    private void onAllWorldsLoaded(CallbackInfo ci) {
        ServerMixinReceivers.INSTANCE.getPostWorldFuture().complete(null);
    }
}
