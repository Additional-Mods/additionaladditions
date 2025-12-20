package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.Toggleable;

public record TintedRedstoneLampConfig(
        boolean enabled,
        float chance,
        int range
) implements Toggleable {
    public static final Codec<TintedRedstoneLampConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(TintedRedstoneLampConfig::enabled),
                    Codec.floatRange(0.0f, 1.0f).fieldOf("despawn_chance").forGetter(TintedRedstoneLampConfig::chance),
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("despawn_range").forGetter(TintedRedstoneLampConfig::range)
            ).apply(instance, TintedRedstoneLampConfig::new)
    );
}
