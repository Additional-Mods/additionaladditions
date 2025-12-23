package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ToolItemConfig(
        float attackSpeed,
        float attackDamage,
        int durability
) {
    public static final Codec<ToolItemConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.floatRange(0, Float.MAX_VALUE).xmap(
                            // its 4.0f base or something and it ignores getSpeed() from tool material
                            f -> f - 4.0f,
                            f -> f + 4.0f
                    ).fieldOf("attack_speed").forGetter(ToolItemConfig::attackSpeed),
                    // for attack damage it does not ignore attack damage bonus from tool material so no need to fiddle with it
                    Codec.floatRange(0, Integer.MAX_VALUE).fieldOf("attack_damage").forGetter(ToolItemConfig::attackDamage),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("durability").forGetter(ToolItemConfig::durability)
            ).apply(instance, ToolItemConfig::new)
    );
}
