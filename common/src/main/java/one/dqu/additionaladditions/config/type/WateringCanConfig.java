package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.config.io.Comment;

public record WateringCanConfig(
        boolean enabled,

        @Comment("Maximum amount of water the watering can can hold. One level is equal to one use.")
        int maxWaterLevel,

        @Comment("Amount of water, in millibuckets, per water level of the watering can. (On Fabric, 81 droplets = 1 millibucket)")
        int volumeWaterLevel,

        @Comment("Chance (0.0 - 1.0) for the watering can to fertilize (bone meal) the crop when used.")
        float fertilizeChance
) implements Toggleable {
    public static final Codec<WateringCanConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(WateringCanConfig::enabled),
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("max_water_level").forGetter(WateringCanConfig::maxWaterLevel),
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("volume_water_level").forGetter(WateringCanConfig::volumeWaterLevel),
                    Codec.floatRange(0.0f, 1.0f).fieldOf("fertilize_chance").forGetter(WateringCanConfig::fertilizeChance)
            ).apply(instance, WateringCanConfig::new)
    );
}

