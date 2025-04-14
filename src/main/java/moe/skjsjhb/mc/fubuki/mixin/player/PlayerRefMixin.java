package moe.skjsjhb.mc.fubuki.mixin.player;

import com.google.common.base.Suppliers;
import moe.skjsjhb.mc.fubuki.player.FubukiPlayer;
import moe.skjsjhb.mc.fubuki.player.PlayerRef;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Supplier;

@Mixin(ServerPlayerEntity.class)
public class PlayerRefMixin implements PlayerRef {
    @Unique
    private final Supplier<FubukiPlayer> fubukiPlayer = Suppliers.memoize(
            () -> new FubukiPlayer((ServerPlayerEntity) (Object) this));

    @Override
    public @NotNull FubukiPlayer fubuki$getPlayer() {
        return fubukiPlayer.get();
    }
}
