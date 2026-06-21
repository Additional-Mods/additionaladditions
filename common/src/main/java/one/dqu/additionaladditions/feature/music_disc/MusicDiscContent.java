package one.dqu.additionaladditions.feature.music_disc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.registry.AATags;

import java.util.function.Supplier;

public class MusicDiscContent {
    private static Supplier<Item> disc(String song, ConfigProperty<? extends Toggleable> config) {
        return AAReg.item()
                .config(config)
                .props(p -> p
                        .stacksTo(1)
                        .rarity(Rarity.RARE)
                        .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.tryBuild(AdditionalAdditions.NAMESPACE, song))))
                .creative(Items.MUSIC_DISC_WARD, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .model(Models::flat)
                .tags(AATags.C_MUSIC_DISCS)
                .make("music_disc_" + song);
    }

    public static Supplier<Item> disc0308() {
        return disc("0308", Config.MUSIC_DISC_0308);
    }

    public static Supplier<Item> disc1007() {
        return disc("1007", Config.MUSIC_DISC_1007);
    }

    public static Supplier<Item> disc1507() {
        return disc("1507", Config.MUSIC_DISC_1507);
    }
}
