package one.dqu.additionaladditions.feature.pocket_jukebox;

import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.TooltipDisplay;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Recipes;

import java.util.Map;
import java.util.function.Supplier;

public class PocketJukeboxContent {
    public static Supplier<PocketJukeboxItem> pocketJukebox() {
        return AAReg.item(PocketJukeboxItem::new)
                .config(Config.POCKET_JUKEBOX)
                .props(p -> p
                        .stacksTo(1)
                        .component(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT.withHidden(DataComponents.CONTAINER, true)))
                .creative(Items.SPYGLASS, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .recipe(Recipes.shaped("CRC", "CJC", "CCC", Map.of('C', Items.COPPER_INGOT, 'R', Items.REDSTONE, 'J', Items.JUKEBOX), RecipeCategory.TOOLS).unlockedBy(Items.JUKEBOX))
                .make("pocket_jukebox");
    }
}
