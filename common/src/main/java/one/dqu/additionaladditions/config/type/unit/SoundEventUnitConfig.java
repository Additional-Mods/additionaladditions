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
        Holder<SoundEvent> soundEvent
) {
    private static final Codec<SoundEventUnitConfig> ICODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.xmap(
                            SoundEventUnitConfig::toSoundEvent,
                            SoundEventUnitConfig::fromSoundEvent
                    ).fieldOf("sound_event").forGetter(SoundEventUnitConfig::soundEvent)
            ).apply(instance, SoundEventUnitConfig::new)
    );

    public static final Codec<Holder<SoundEvent>> CODEC = ICODEC.xmap(
            SoundEventUnitConfig::soundEvent,
            SoundEventUnitConfig::new
    );

    private static Holder<SoundEvent> toSoundEvent(String id) {
        return BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(
                ResourceKey.create(Registries.SOUND_EVENT, ResourceLocation.parse(id))
        );
    }

    private static String fromSoundEvent(Holder<SoundEvent> soundEvent) {
        return soundEvent.getRegisteredName();
    }
}
