package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.component.KineticWeapon;

import java.util.Optional;

public record KineticWeaponUnitConfig(
        int contactCooldownTicks,
        int delayTicks,
        Optional<KineticWeapon.Condition> dismountConditions,
        Optional<KineticWeapon.Condition> knockbackConditions,
        Optional<KineticWeapon.Condition> damageConditions,
        float forwardMovement,
        float damageMultiplier,
        Optional<String> sound,
        Optional<String> hitSound
) {
    private static final Codec<KineticWeaponUnitConfig> ICODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("contact_cooldown_ticks", 10).forGetter(KineticWeaponUnitConfig::contactCooldownTicks),
                    Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("delay_ticks", 0).forGetter(KineticWeaponUnitConfig::delayTicks),
                    KineticWeaponConditionUnitConfig.CODEC.optionalFieldOf("dismount_conditions").forGetter(KineticWeaponUnitConfig::dismountConditions),
                    KineticWeaponConditionUnitConfig.CODEC.optionalFieldOf("knockback_conditions").forGetter(KineticWeaponUnitConfig::knockbackConditions),
                    KineticWeaponConditionUnitConfig.CODEC.optionalFieldOf("damage_conditions").forGetter(KineticWeaponUnitConfig::damageConditions),
                    Codec.FLOAT.optionalFieldOf("forward_movement", 0.0F).forGetter(KineticWeaponUnitConfig::forwardMovement),
                    Codec.FLOAT.optionalFieldOf("damage_multiplier", 1.0F).forGetter(KineticWeaponUnitConfig::damageMultiplier),
                    Codec.STRING.optionalFieldOf("sound").forGetter(KineticWeaponUnitConfig::sound),
                    Codec.STRING.optionalFieldOf("hit_sound").forGetter(KineticWeaponUnitConfig::hitSound)
            ).apply(instance, KineticWeaponUnitConfig::new)
    );

    public static final Codec<KineticWeapon> CODEC = ICODEC.xmap(
            KineticWeaponUnitConfig::toKineticWeapon,
            KineticWeaponUnitConfig::fromKineticWeapon
    );

    private KineticWeapon toKineticWeapon() {
        return new KineticWeapon(
                contactCooldownTicks,
                delayTicks,
                dismountConditions,
                knockbackConditions,
                damageConditions,
                forwardMovement,
                damageMultiplier,
                sound.map(KineticWeaponUnitConfig::parseSoundEvent),
                hitSound.map(KineticWeaponUnitConfig::parseSoundEvent)
        );
    }

    private static KineticWeaponUnitConfig fromKineticWeapon(KineticWeapon weapon) {
        return new KineticWeaponUnitConfig(
                weapon.contactCooldownTicks(),
                weapon.delayTicks(),
                weapon.dismountConditions(),
                weapon.knockbackConditions(),
                weapon.damageConditions(),
                weapon.forwardMovement(),
                weapon.damageMultiplier(),
                weapon.sound().map(Holder::getRegisteredName),
                weapon.hitSound().map(Holder::getRegisteredName)
        );
    }

    private static Holder<SoundEvent> parseSoundEvent(String id) {
        return BuiltInRegistries.SOUND_EVENT.getOrThrow(
                ResourceKey.create(Registries.SOUND_EVENT, Identifier.parse(id))
        );
    }
}
