package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.io.Comment;

public record VersionConfig(
        @Comment("Do not touch. You will break the config files.")
        int version
) {
    public static final Codec<VersionConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("version").forGetter(VersionConfig::version)
            ).apply(instance, VersionConfig::new)
    );
}
