package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.config.io.Comment;

public record SnifferPlantsConfig(
        boolean enabled,

        @Comment("This property is intended for modpack makers. If set to true, the mods' sniffer plants will be added to the loot table of the sniffer, and if set to false, they will not be added.\n  // If you want to customize how the plants drop, disable this property and add the drops yourself using a datapack or a tool such as kubejs.")
        boolean addDropsToSnifferLootTable
) implements Toggleable {
    public static final Codec<SnifferPlantsConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("enabled").forGetter(SnifferPlantsConfig::enabled),
                    Codec.BOOL.fieldOf("add_drops_to_sniffer_loot_table").forGetter(SnifferPlantsConfig::addDropsToSnifferLootTable)
            ).apply(instance, SnifferPlantsConfig::new)
    );
}
