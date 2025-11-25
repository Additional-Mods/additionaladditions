package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AmethystLampConfig(
        boolean enabled,
        float chance
) {
    public static final Codec<AmethystLampConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(AmethystLampConfig::enabled),
                    Codec.floatRange(0.0f, 1.0f).fieldOf("despawn_chance").forGetter(AmethystLampConfig::chance)
            ).apply(instance, AmethystLampConfig::new)
    );
}
