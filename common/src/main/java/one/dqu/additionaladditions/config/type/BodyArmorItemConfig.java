package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BodyArmorItemConfig(
        int protection
) {
    public static final Codec<BodyArmorItemConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("protection").forGetter(BodyArmorItemConfig::protection)
            ).apply(instance, BodyArmorItemConfig::new)
    );
}
