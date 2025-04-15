package moe.skjsjhb.mc.fubuki.mixin.util;

import moe.skjsjhb.mc.fubuki.interop.BukkitRef;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * This mixin adds the capability of holding an arbitrary value via {@link BukkitRef}.
 */
@Mixin({
        MinecraftServer.class,
        Entity.class
})
public abstract class BukkitRefMixin implements BukkitRef {
    @Unique
    private Object fubukiBukkitRef = null;

    @Override
    public void fubuki$setRef(@Nullable Object obj) {
        fubukiBukkitRef = obj;
    }

    @Override
    public @Nullable Object fubuki$getRef() {
        return fubukiBukkitRef;
    }
}
