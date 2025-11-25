package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FeatureConfig(
        boolean enabled
) {
    public static final Codec<FeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(FeatureConfig::enabled)
            ).apply(instance, FeatureConfig::new)
    );
}
