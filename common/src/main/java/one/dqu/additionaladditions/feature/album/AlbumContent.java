package one.dqu.additionaladditions.feature.album;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAItem;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.registry.AATags;

import java.util.function.Supplier;

public class AlbumContent {
    public static final AAItem<AlbumItem> TEMPLATE = new AAItem<AlbumItem>()
            .config(Config.ALBUM)
            .factory(AlbumItem::new)
            .creative(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.BEFORE)
            .props(p -> p
                    .stacksTo(1)
                    .component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY))
            .tags(AATags.ALBUMS);

    public static Supplier<AlbumItem> album(String id) {
        return new AAItem<>(TEMPLATE).make(id);
    }

    public static Supplier<AlbumItem> album(String id, DyeColor color) {
        return new AAItem<>(TEMPLATE).tags(AATags.dyed(color)).make(id);
    }
}
