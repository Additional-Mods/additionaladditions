package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.config.io.Comment;

public record WateringCanConfig(
        boolean enabled,

        @Comment("Maximum amount of water the watering can can hold. One level is equal to one use, and takes 100 millibuckets.")
        int maxWaterLevel
) implements Toggleable {
    public static final Codec<WateringCanConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(WateringCanConfig::enabled),
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("max_water_level").forGetter(WateringCanConfig::maxWaterLevel)
            ).apply(instance, WateringCanConfig::new)
    );
}

