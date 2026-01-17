package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.config.io.Comment;

import java.util.EnumMap;
import java.util.Map;

public record SuspiciousDyeConfig(
        boolean enabled,

        @Comment("Determines how much brighter each color will be compared to the base brightness. Some colors are naturally darker than others, so this allows you to tweak the brightness of each color individually.")
        Map<DyeColor, Float> brightnessMultipliers,

        @Comment("Determines how much brighter the armor glint will be compared to the item brightness.")
        float armorBrightnessMultiplier
) implements Toggleable {
    public static final Codec<SuspiciousDyeConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(SuspiciousDyeConfig::enabled),
                    Codec.unboundedMap(DyeColor.CODEC, Codec.FLOAT).fieldOf("brightness_multipliers").forGetter(SuspiciousDyeConfig::brightnessMultipliers),
                    Codec.floatRange(0.0f, 8.0f).fieldOf("armor_brightness_multiplier").forGetter(SuspiciousDyeConfig::armorBrightnessMultiplier)
            ).apply(instance, SuspiciousDyeConfig::new)
    );

    // force the map to be complete with all dye colors, 1.5f as default
    public SuspiciousDyeConfig {
        EnumMap<DyeColor, Float> multipliers = new EnumMap<>(DyeColor.class);
        for (DyeColor color : DyeColor.values()) {
            multipliers.put(color, brightnessMultipliers.getOrDefault(color, 1.5f));
        }
        brightnessMultipliers = multipliers;
    }
}
