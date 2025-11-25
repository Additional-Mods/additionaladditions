package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DepthMeterConfig(
        boolean enabled,
        boolean displayElevationAlways
) {
    public static final Codec<DepthMeterConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(DepthMeterConfig::enabled),
                    Codec.BOOL.fieldOf("display_elevation_always").forGetter(DepthMeterConfig::displayElevationAlways)
            ).apply(instance, DepthMeterConfig::new)
    );
}
