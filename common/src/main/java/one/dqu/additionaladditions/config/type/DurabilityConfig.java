package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DurabilityConfig(int durability) {
    public static final Codec<DurabilityConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("durability").forGetter(DurabilityConfig::durability)
            ).apply(instance, DurabilityConfig::new)
    );
}
