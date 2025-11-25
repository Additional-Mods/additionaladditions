package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BarometerConfig(
        boolean enabled,
        boolean displayElevationAlways
) {
    public static final Codec<BarometerConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(BarometerConfig::enabled),
                    Codec.BOOL.fieldOf("display_elevation_always").forGetter(BarometerConfig::displayElevationAlways)
            ).apply(instance, BarometerConfig::new)
    );
}
