package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record VersionConfig(
        int version
) {
    public static final Codec<VersionConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("version").forGetter(VersionConfig::version)
            ).apply(instance, VersionConfig::new)
    );
}
