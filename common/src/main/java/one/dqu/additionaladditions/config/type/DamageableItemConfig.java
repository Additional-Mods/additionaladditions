package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DamageableItemConfig(
        boolean enabled,
        int durability
) {
    public static final Codec<DamageableItemConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(DamageableItemConfig::enabled),
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("durability").forGetter(DamageableItemConfig::durability)
            ).apply(instance, DamageableItemConfig::new)
    );
}
