package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.config.io.Comment;

public record TintedRedstoneLampConfig(
        boolean enabled,

        @Comment("Percentage chance (0.0 to 1.0) for monsters to not spawn within the range of the lamp.")
        float despawnChance,

        @Comment("Range in blocks from the lamp within which monsters may despawn.")
        int despawnRange
) implements Toggleable {
    public static final Codec<TintedRedstoneLampConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(TintedRedstoneLampConfig::enabled),
                    Codec.floatRange(0.0f, 1.0f).fieldOf("despawn_chance").forGetter(TintedRedstoneLampConfig::despawnChance),
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("despawn_range").forGetter(TintedRedstoneLampConfig::despawnRange)
            ).apply(instance, TintedRedstoneLampConfig::new)
    );
}
