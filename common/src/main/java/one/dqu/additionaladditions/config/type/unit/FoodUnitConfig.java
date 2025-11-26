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
                    Codec.INT.fieldOf("nutrition").forGetter(FoodUnitConfig::nutrition),
                    Codec.FLOAT.fieldOf("saturation").forGetter(FoodUnitConfig::saturation),
                    Codec.BOOL.optionalFieldOf("can_always_eat", false).forGetter(FoodUnitConfig::canAlwaysEat),
                    Codec.FLOAT.optionalFieldOf("eat_duration", 1.6f).forGetter(FoodUnitConfig::eatSeconds)
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
