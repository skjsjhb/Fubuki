package moe.skjsjhb.mc.fubuki.mixin.data;

import moe.skjsjhb.mc.fubuki.data.FubukiPDC;
import moe.skjsjhb.mc.fubuki.data.PDCBinder;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class PDCEntityMixin implements PDCBinder {
    @Unique
    private FubukiPDC fubukiPDC = null;

    @Unique
    // Native entities are created earlier than PDC object
    // The NBT for loading is dropped once loaded
    // We should extract the data and ref it here
    private byte[] fubukiPDCData = null;

    @Inject(method = "writeNbt", at = @At("RETURN"))
    public void savePDCData(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (fubukiPDC != null && !fubukiPDC.isEmpty()) {
            fubukiPDCData = fubukiPDC.saveAsByteArray();
            if (fubukiPDCData != null) {
                nbt.putByteArray("FubukiPDC", fubukiPDCData);
            }
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    public void loadPDCData(NbtCompound nbt, CallbackInfo ci) {
        nbt.getByteArray("FubukiPDC").ifPresent(ba -> {
            fubukiPDCData = ba;
            if (fubukiPDC != null) {
                fubukiPDC.loadFromByteArray(ba);
            }
        });
    }

    @Override
    public void fubuki$bindPDC(@NotNull FubukiPDC pdc) {
        // As an entity may be associated with multiple Bukkit entities (e.g. accessing player features from an object
        // which has been associated with a regular entity earlier), PDC may get re-constructed in the lifecycle.
        // This reload ensures that data are kept.
        if (fubukiPDC != null) {
            // Forward previous data to keep changes
            fubukiPDC.copyTo(pdc, true);
        } else {
            if (fubukiPDCData != null) {
                pdc.loadFromByteArray(fubukiPDCData);
                fubukiPDCData = null; // The data can be dropped once it has been redeemed
            }
        }

        fubukiPDC = pdc;
    }
}
