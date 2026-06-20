package one.dqu.additionaladditions.feature.album;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAItem;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.registry.AATags;

import java.util.Map;
import java.util.function.Supplier;

public class AlbumContent {
    private static AAItem<AlbumItem> album() {
        return AAReg.item(AlbumItem::new)
                .config(Config.ALBUM)
                .creative(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.BEFORE)
                .props(p -> p
                        .stacksTo(1)
                        .component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY))
                .model(Models::album)
                .tags(AATags.ALBUMS);
    }

    public static Supplier<AlbumItem> album(String id) {
        return album()
                .recipe(Recipes.shaped("SS", "LL", Map.of('S', Items.STRING, 'L', Items.LEATHER), RecipeCategory.MISC).unlockedBy(Items.LEATHER, Items.JUKEBOX))
                .make(id);
    }

    public static Supplier<AlbumItem> album(String id, DyeColor color) {
        //noinspection Convert2MethodRef - otherwise would crash - item is null at this point
        ItemLike albumItem = () -> AAItems.ALBUM.get();
        ItemLike dyeItem = switch (color) {
            case RED -> Items.RED_DYE;
            case ORANGE -> Items.ORANGE_DYE;
            case YELLOW -> Items.YELLOW_DYE;
            case GREEN -> Items.GREEN_DYE;
            case BLUE -> Items.BLUE_DYE;
            case PURPLE -> Items.PURPLE_DYE;
            case PINK -> Items.PINK_DYE;
            case BROWN -> Items.BROWN_DYE;
            case BLACK -> Items.BLACK_DYE;
            case WHITE -> Items.WHITE_DYE;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
            case LIME -> Items.LIME_DYE;
            case CYAN -> Items.CYAN_DYE;
            case MAGENTA -> Items.MAGENTA_DYE;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
            case GRAY -> Items.GRAY_DYE;
        };

        return album()
                .tags(AATags.dyed(color))
                .recipe(Recipes.transmute(RecipeCategory.MISC, AATags.ALBUMS, dyeItem).unlockedBy(albumItem).group("album_dye"))
                .make(id);
    }
}
