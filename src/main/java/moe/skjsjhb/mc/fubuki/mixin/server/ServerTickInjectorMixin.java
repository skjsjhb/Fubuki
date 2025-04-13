package moe.skjsjhb.mc.fubuki.mixin.server;

import moe.skjsjhb.mc.fubuki.server.ServerTickInjector;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class ServerTickInjectorMixin {
    @Inject(method = "runTasksTillTickEnd", at = @At("HEAD"))
    public void onBeginTick(CallbackInfo ci) {
        ServerTickInjector.onBeginTick((MinecraftServer) (Object) this);
    }
}
