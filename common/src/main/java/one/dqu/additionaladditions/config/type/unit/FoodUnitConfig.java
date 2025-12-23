package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.food.FoodProperties;

import java.util.List;
import java.util.Optional;

public record FoodUnitConfig(
        FoodProperties foodProperties
) {
    private static final Codec<FoodUnitConfig> ICODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("nutrition").forGetter(f -> f.foodProperties().nutrition()),
                    Codec.floatRange(0f, Float.MAX_VALUE).fieldOf("saturation").forGetter(f -> f.foodProperties().saturation()),
                    Codec.BOOL.optionalFieldOf("can_always_eat", false).forGetter(f -> f.foodProperties().canAlwaysEat()),
                    Codec.floatRange(0f, Float.MAX_VALUE).optionalFieldOf("eat_seconds", 1.6f).forGetter(f -> f.foodProperties().eatSeconds())
            ).apply(instance, (nutrition, saturation, canAlwaysEat, eatSeconds) ->
                    new FoodUnitConfig(new FoodProperties(nutrition, saturation, canAlwaysEat, eatSeconds, Optional.empty(), List.of()))
            )
    );

    public static final Codec<FoodProperties> CODEC = ICODEC.xmap(
            FoodUnitConfig::foodProperties,
            FoodUnitConfig::new
    );
}