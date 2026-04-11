package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.ArmorLikeConfig;
import one.dqu.additionaladditions.config.io.Comment;

public record BodyArmorItemConfig(
        @Comment("This config determines the protection of animal (horse, nautilus) armor.")
        int protection
) implements ArmorLikeConfig {
    public static final Codec<BodyArmorItemConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("protection").forGetter(BodyArmorItemConfig::protection)
            ).apply(instance, BodyArmorItemConfig::new)
    );
}
