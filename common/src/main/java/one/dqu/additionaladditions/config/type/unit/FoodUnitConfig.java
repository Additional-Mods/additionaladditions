package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.food.FoodProperties;
import one.dqu.additionaladditions.config.io.Comment;

import java.util.List;
import java.util.Optional;

public record FoodUnitConfig(
        @Comment("Amount of hunger restored")
        int nutrition,

        @Comment("Amount of saturation restored")
        float saturation,

        @Comment("If true, can be eaten even when player has full hunger")
        boolean canAlwaysEat,

        @Comment("Eating duration in seconds")
        float eatSeconds
) {
    private static final Codec<FoodUnitConfig> ICODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("nutrition").forGetter(FoodUnitConfig::nutrition),
                    Codec.floatRange(0f, Float.MAX_VALUE).fieldOf("saturation").forGetter(FoodUnitConfig::saturation),
                    Codec.BOOL.optionalFieldOf("can_always_eat", false).forGetter(FoodUnitConfig::canAlwaysEat),
                    Codec.floatRange(0f, Float.MAX_VALUE).optionalFieldOf("eat_seconds", 1.6f).forGetter(FoodUnitConfig::eatSeconds)
            ).apply(instance, FoodUnitConfig::new)
    );

    public static final Codec<FoodProperties> CODEC = ICODEC.xmap(
            FoodUnitConfig::toProperties,
            FoodUnitConfig::fromProperties
    );

    private FoodProperties toProperties() {
        return new FoodProperties(nutrition, saturation, canAlwaysEat, eatSeconds, Optional.empty(), List.of());
    }

    private static FoodUnitConfig fromProperties(FoodProperties props) {
        return new FoodUnitConfig(props.nutrition(), props.saturation(), props.canAlwaysEat(), props.eatSeconds());
    }
}