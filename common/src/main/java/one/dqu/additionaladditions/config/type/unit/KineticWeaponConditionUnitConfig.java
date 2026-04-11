package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.component.KineticWeapon;

public record KineticWeaponConditionUnitConfig(
        int maxDurationTicks,
        float minSpeed,
        float minRelativeSpeed
) {
    private static final Codec<KineticWeaponConditionUnitConfig> ICODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("max_duration_ticks").forGetter(KineticWeaponConditionUnitConfig::maxDurationTicks),
                    Codec.FLOAT.optionalFieldOf("min_speed", 0.0F).forGetter(KineticWeaponConditionUnitConfig::minSpeed),
                    Codec.FLOAT.optionalFieldOf("min_relative_speed", 0.0F).forGetter(KineticWeaponConditionUnitConfig::minRelativeSpeed)
            ).apply(instance, KineticWeaponConditionUnitConfig::new)
    );

    public static final Codec<KineticWeapon.Condition> CODEC = ICODEC.xmap(
            KineticWeaponConditionUnitConfig::toCondition,
            KineticWeaponConditionUnitConfig::fromCondition
    );

    private KineticWeapon.Condition toCondition() {
        return new KineticWeapon.Condition(maxDurationTicks, minSpeed, minRelativeSpeed);
    }

    private static KineticWeaponConditionUnitConfig fromCondition(KineticWeapon.Condition condition) {
        return new KineticWeaponConditionUnitConfig(
                condition.maxDurationTicks(),
                condition.minSpeed(),
                condition.minRelativeSpeed()
        );
    }
}
