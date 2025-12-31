package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public record SoundEventUnitConfig(
       String soundEvent
) {
    private static final Codec<SoundEventUnitConfig> ICODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("sound_event").forGetter(SoundEventUnitConfig::soundEvent)
            ).apply(instance, SoundEventUnitConfig::new)
    );

    public static final Codec<Holder<SoundEvent>> CODEC = ICODEC.xmap(
            SoundEventUnitConfig::toSoundEvent,
            SoundEventUnitConfig::fromSoundEvent
    );

    private Holder<SoundEvent> toSoundEvent() {
        return BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(
                ResourceKey.create(Registries.SOUND_EVENT, ResourceLocation.parse(this.soundEvent))
        );
    }

    private static SoundEventUnitConfig fromSoundEvent(Holder<SoundEvent> soundEvent) {
        return new SoundEventUnitConfig(soundEvent.getRegisteredName());
    }
}
