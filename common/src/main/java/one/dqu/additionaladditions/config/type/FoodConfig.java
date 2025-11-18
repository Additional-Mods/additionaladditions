package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FoodConfig(
        int nutrition,
        float saturation
) {
    public static final Codec<FoodConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("nutrition").forGetter(FoodConfig::nutrition),
                    Codec.floatRange(0, Integer.MAX_VALUE).fieldOf("saturation").forGetter(FoodConfig::saturation)
            ).apply(instance, FoodConfig::new)
    );
}
