package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.food.FoodProperties;

import java.util.List;
import java.util.Optional;

public record FoodUnitConfig(
        int nutrition,
        float saturation,
        boolean canAlwaysEat,
        float eatSeconds
) {
    public static final Codec<FoodUnitConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("nutrition").forGetter(FoodUnitConfig::nutrition),
                    Codec.floatRange(0f, Float.MAX_VALUE).fieldOf("saturation").forGetter(FoodUnitConfig::saturation),
                    Codec.BOOL.optionalFieldOf("can_always_eat", false).forGetter(FoodUnitConfig::canAlwaysEat),
                    Codec.floatRange(0f, Float.MAX_VALUE).optionalFieldOf("eat_seconds", 1.6f).forGetter(FoodUnitConfig::eatSeconds)
            ).apply(instance, FoodUnitConfig::new)
    );

    public FoodProperties toFoodProperties() {
        return new FoodProperties(
                nutrition, saturation, canAlwaysEat,
                eatSeconds, Optional.empty(), List.of()
        );
    }

    public static FoodUnitConfig fromFoodProperties(FoodProperties foodProperties) {
        return new FoodUnitConfig(
                foodProperties.nutrition(),
                foodProperties.saturation(),
                foodProperties.canAlwaysEat(),
                foodProperties.eatSeconds()
        );
    }
}
