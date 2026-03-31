package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.ArmorLikeConfig;

public record ArmorItemConfig(
        int protection,
        int durability
) implements ArmorLikeConfig {
    public static final Codec<ArmorItemConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("protection").forGetter(ArmorItemConfig::protection),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("durability").forGetter(ArmorItemConfig::durability)
            ).apply(instance, ArmorItemConfig::new)
    );
}
