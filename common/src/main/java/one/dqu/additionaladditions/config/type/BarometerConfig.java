package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.config.io.Comment;

public record BarometerConfig(
        boolean enabled,

        @Comment("If true, Y coordinate of the player will be displayed in the HUD (action bar) when the barometer is held.")
        boolean displayElevationHud
) implements Toggleable {
    public static final Codec<BarometerConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(BarometerConfig::enabled),
                    Codec.BOOL.fieldOf("display_elevation_hud").forGetter(BarometerConfig::displayElevationHud)
            ).apply(instance, BarometerConfig::new)
    );
}
