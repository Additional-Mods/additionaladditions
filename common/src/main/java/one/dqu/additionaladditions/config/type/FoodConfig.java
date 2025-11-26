package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.food.FoodProperties;
import one.dqu.additionaladditions.config.type.unit.FoodUnitConfig;

public record FoodConfig(
        boolean enabled,
        FoodProperties food
) {
    public static final Codec<FoodConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(FoodConfig::enabled),
                    FoodUnitConfig.CODEC.xmap(
                            FoodUnitConfig::toFoodProperties,
                            FoodUnitConfig::fromFoodProperties
                    ).fieldOf("food").forGetter(FoodConfig::food)
            ).apply(instance, FoodConfig::new)
    );
}
