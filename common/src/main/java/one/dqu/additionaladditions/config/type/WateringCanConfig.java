package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record WateringCanConfig(
        boolean enabled,
        int maxWaterLevel
) {
    public static final Codec<WateringCanConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(WateringCanConfig::enabled),
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("max_water_level").forGetter(WateringCanConfig::maxWaterLevel)
            ).apply(instance, WateringCanConfig::new)
    );
}

