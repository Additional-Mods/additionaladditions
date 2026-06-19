package one.dqu.additionaladditions.feature.album;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAItem;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.registry.AATags;

import java.util.function.Supplier;

public class AlbumContent {
    private static AAItem<AlbumItem> album() {
        return AAReg.item(AlbumItem::new)
                .config(Config.ALBUM)
                .creative(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.BEFORE)
                .props(p -> p
                        .stacksTo(1)
                        .component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY))
                .tags(AATags.ALBUMS);
    }

    public static Supplier<AlbumItem> album(String id) {
        return album().make(id);
    }

    public static Supplier<AlbumItem> album(String id, DyeColor color) {
        return album().tags(AATags.dyed(color)).make(id);
    }
}
