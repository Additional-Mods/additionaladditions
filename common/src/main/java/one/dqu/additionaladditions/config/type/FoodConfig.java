package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.food.FoodProperties;

public record FoodConfig(
        boolean enabled,
        FoodProperties food
) {
    public static final Codec<FoodConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(FoodConfig::enabled),
                    FoodProperties.DIRECT_CODEC.fieldOf("food").forGetter(FoodConfig::food)
            ).apply(instance, FoodConfig::new)
    );
}
