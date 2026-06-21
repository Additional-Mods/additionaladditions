package one.dqu.additionaladditions.feature.watering_can;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.registry.AAMisc;

import java.util.Map;
import java.util.function.Supplier;

public class WateringCanContent {
    public static Supplier<WateringCanItem> wateringCan() {
        return AAReg.item(WateringCanItem::new)
                .config(Config.WATERING_CAN)
                .props(p -> p
                        .stacksTo(1)
                        .component(AAMisc.WATER_LEVEL_COMPONENT.get(), 0))
                .creative(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .recipe(Recipes.shaped("C  ", "CBC", " C ", Map.of('C', Items.COPPER_INGOT, 'B', Items.BUCKET), RecipeCategory.TOOLS).unlockedBy(Items.COPPER_INGOT))
                .make("watering_can");
    }
}
