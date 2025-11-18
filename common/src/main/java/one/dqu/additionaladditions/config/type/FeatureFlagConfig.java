package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FeatureFlagConfig(
        boolean enabled
) {
    public static final Codec<FeatureFlagConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(FeatureFlagConfig::enabled)
            ).apply(instance, FeatureFlagConfig::new)
    );
}
