package one.dqu.additionaladditions.feature.barometer;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Recipes;

import java.util.Map;
import java.util.function.Supplier;

public class BarometerContent {
    public static Supplier<Item> barometer() {
        return AAReg.item()
                .config(Config.BAROMETER)
                .creative(Items.CLOCK, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .recipe(Recipes.shaped(" C ", "CRC", "CCC", Map.of('C', Items.COPPER_INGOT, 'R', Items.REDSTONE), RecipeCategory.TOOLS).unlockedBy(Items.REDSTONE))
                .make("barometer");
    }
}
