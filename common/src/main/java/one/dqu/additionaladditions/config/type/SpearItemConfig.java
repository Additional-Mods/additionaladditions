package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.component.KineticWeapon;
import net.minecraft.world.item.component.PiercingWeapon;
import one.dqu.additionaladditions.config.ToolLikeConfig;
import one.dqu.additionaladditions.config.io.Comment;
import one.dqu.additionaladditions.config.type.unit.KineticWeaponUnitConfig;
import one.dqu.additionaladditions.config.type.unit.PiercingWeaponUnitConfig;

public record SpearItemConfig(
        float attackSpeed,
        float attackDamage,
        int durability,

        @Comment("Properties for \"kinetic_weapon\" item component. If you don't know what this is, please refer to the Minecraft Wiki page on Data Component Format.")
        KineticWeapon kineticWeapon,

        @Comment("Properties for \"piercing_weapon\" item component. If you don't know what this is, please refer to the Minecraft Wiki page on Data Component Format.")
        PiercingWeapon piercingWeapon
) implements ToolLikeConfig {
    public static final Codec<SpearItemConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.floatRange(0, Float.MAX_VALUE).fieldOf("attack_speed").forGetter(SpearItemConfig::rawAttackSpeed),
                    Codec.floatRange(0, Float.MAX_VALUE).fieldOf("attack_damage").forGetter(SpearItemConfig::attackDamage),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("durability").forGetter(SpearItemConfig::durability),
                    KineticWeaponUnitConfig.CODEC.fieldOf("kinetic_weapon").forGetter(SpearItemConfig::kineticWeapon),
                    PiercingWeaponUnitConfig.CODEC.fieldOf("piercing_weapon").forGetter(SpearItemConfig::piercingWeapon)
            ).apply(instance, SpearItemConfig::new)
    );

    private float rawAttackSpeed() {
        return attackSpeed;
    }

    @Override
    public float attackSpeed() {
        return attackSpeed - 4f;
    }

    public int swingAnimationTicks() {
        return (int)(20.0F / attackSpeed);
    }
}
