package moe.skjsjhb.mc.fubuki.mixin.data;

import moe.skjsjhb.mc.fubuki.data.MetadataContainer;
import net.minecraft.entity.Entity;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Mixin({Entity.class})
public class MetadataContainerMixin implements MetadataContainer {
    @Unique
    private final Map<String, Map<Plugin, MetadataValue>> fubukiMetadataValues = new ConcurrentHashMap<>();

    @Override
    public synchronized void fubuki$setMetadata(@NotNull String k, @NotNull MetadataValue v) {
        var p = v.getOwningPlugin();
        var ent = fubukiMetadataValues.computeIfAbsent(k, (ignored) -> new WeakHashMap<>());
        ent.put(p, v);
    }

    @Override
    public synchronized @NotNull List<MetadataValue> fubuki$getMetadata(@NotNull String k) {
        var ent = fubukiMetadataValues.get(k);
        if (ent == null) return Collections.emptyList();
        return new ArrayList<>(ent.values());
    }

    @Override
    public synchronized void fubuki$removeMetadata(@NotNull String k, @NotNull Plugin plugin) {
        var ent = fubukiMetadataValues.get(k);
        if (ent == null) return;
        ent.remove(plugin);

        if (ent.isEmpty()) {
            fubukiMetadataValues.remove(k);
        }
    }

    @Override
    public synchronized boolean fubuki$hasMetadata(@NotNull String k) {
        return fubukiMetadataValues.containsKey(k);
    }
}
