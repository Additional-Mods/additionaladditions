package dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ChanceConfig(
        float chance
) {
    public static final Codec<ChanceConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.floatRange(0.0f, 1.0f).fieldOf("chance").forGetter(ChanceConfig::chance)
            ).apply(instance, ChanceConfig::new)
    );
}
