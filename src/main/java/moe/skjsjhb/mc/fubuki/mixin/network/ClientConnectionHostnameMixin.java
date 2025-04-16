package moe.skjsjhb.mc.fubuki.mixin.network;

import moe.skjsjhb.mc.fubuki.network.HostnameContainer;
import net.minecraft.network.ClientConnection;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * Makes {@link ClientConnection} capable for holding the hostname.
 */
@Mixin(ClientConnection.class)
public class ClientConnectionHostnameMixin implements HostnameContainer {
    @Unique
    private String hostname;

    @Override
    public void fubuki$setHostname(@NotNull String s) {
        hostname = s;
    }

    @Override
    public @NotNull String fubuki$getHostname() {
        return hostname;
    }
}
