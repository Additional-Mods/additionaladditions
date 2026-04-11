package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.component.PiercingWeapon;

import java.util.Optional;

public record PiercingWeaponUnitConfig(
        boolean dealsKnockback,
        boolean dismounts,
        Optional<String> sound,
        Optional<String> hitSound
) {
    private static final Codec<PiercingWeaponUnitConfig> ICODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.optionalFieldOf("deals_knockback", true).forGetter(PiercingWeaponUnitConfig::dealsKnockback),
                    Codec.BOOL.optionalFieldOf("dismounts", false).forGetter(PiercingWeaponUnitConfig::dismounts),
                    Codec.STRING.optionalFieldOf("sound").forGetter(PiercingWeaponUnitConfig::sound),
                    Codec.STRING.optionalFieldOf("hit_sound").forGetter(PiercingWeaponUnitConfig::hitSound)
            ).apply(instance, PiercingWeaponUnitConfig::new)
    );

    public static final Codec<PiercingWeapon> CODEC = ICODEC.xmap(
            PiercingWeaponUnitConfig::toPiercingWeapon,
            PiercingWeaponUnitConfig::fromPiercingWeapon
    );

    private PiercingWeapon toPiercingWeapon() {
        return new PiercingWeapon(
                dealsKnockback,
                dismounts,
                sound.map(PiercingWeaponUnitConfig::parseSoundEvent),
                hitSound.map(PiercingWeaponUnitConfig::parseSoundEvent)
        );
    }

    private static PiercingWeaponUnitConfig fromPiercingWeapon(PiercingWeapon weapon) {
        return new PiercingWeaponUnitConfig(
                weapon.dealsKnockback(),
                weapon.dismounts(),
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
