package moe.skjsjhb.mc.fubuki.mixin.util;

import com.google.common.collect.ImmutableMap;
import moe.skjsjhb.mc.fubuki.util.TextColorFormatContainer;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A mixin for compatibility with conversions between text component and string from CraftBukkit.
 * <p>
 * CraftBukkit references the {@link Formatting} object within the converted {@link TextColor} object and it's get used
 * in the conversion code. This mixin brings the field back.
 */
@Mixin(TextColor.class)
public class TextColorFormatMixin implements TextColorFormatContainer {
    @Mutable
    @Shadow
    @Final
    private static Map<Formatting, TextColor> FORMATTING_TO_COLOR;

    @Unique
    private Formatting format = null;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void preserveFormatConversion(CallbackInfo ci) {
        FORMATTING_TO_COLOR = Stream.of(Formatting.values())
                .filter(Formatting::isColor)
                .collect(ImmutableMap.toImmutableMap(Function.identity(), formatting -> {
                    var colorInt = Objects.requireNonNullElse(formatting.getColorValue(), 0);
                    var color = new TextColor(colorInt, formatting.getName());
                    ((TextColorFormatContainer) (Object) color).fubuki$setFormat(formatting);
                    return color;
                }));
    }

    @Override
    public void fubuki$setFormat(Formatting fmt) {
        format = fmt;
    }

    @Override
    public Formatting fubuki$getFormat() {
        return format;
    }
}
