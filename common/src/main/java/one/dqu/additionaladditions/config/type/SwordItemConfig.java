package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.ToolLikeConfig;

public record SwordItemConfig(
        float attackSpeed,
        int attack_damage,
        int durability
) implements ToolLikeConfig {
    public static final Codec<SwordItemConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.floatRange(0, Float.MAX_VALUE).fieldOf("attack_speed").forGetter(SwordItemConfig::rawAttackSpeed),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("attack_damage").forGetter(SwordItemConfig::attack_damage),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("durability").forGetter(SwordItemConfig::durability)
            ).apply(instance, SwordItemConfig::new)
    );

    private float rawAttackSpeed() {
        return attackSpeed;
    }

    // its 4.0f base or something and it ignores getSpeed() from tool material
    public float attackSpeed() {
        return attackSpeed - 4f;
    }

    // this is ugly but oh well
    @Override
    public float attackDamage() {
        return attack_damage;
    }
}
