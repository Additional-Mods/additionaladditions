package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.config.io.Comment;

public record AlbumConfig(
        boolean enabled,

        @Comment("Maximum amount of music discs an album can hold")
        int capacity
) implements Toggleable {
    public static final Codec<AlbumConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(AlbumConfig::enabled),
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("capacity").forGetter(AlbumConfig::capacity)
            ).apply(instance, AlbumConfig::new)
    );
}
