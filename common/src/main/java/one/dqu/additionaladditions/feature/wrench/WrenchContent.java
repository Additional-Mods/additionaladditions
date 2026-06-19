package one.dqu.additionaladditions.feature.wrench;

import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.registry.AATags;

import java.util.Map;
import java.util.function.Supplier;

public class WrenchContent {
    public static Supplier<WrenchItem> wrench() {
        return AAReg.item(WrenchItem::new)
                .config(Config.WRENCH)
                .props(p -> p
                        .stacksTo(1)
                        .delayedComponent(DataComponents.MAX_DAMAGE, _ -> Config.WRENCH.get().durability())
                        .component(DataComponents.DAMAGE, 0))
                .creative(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .creative(Items.TARGET, CreativeModeTabs.REDSTONE_BLOCKS, CreativePosition.AFTER)
                .model(Models::handheld)
                .tags(AATags.C_TOOLS_WRENCH)
                .recipe(Recipes.shaped("C C", " C ", " C ", Map.of('C', Items.COPPER_INGOT), RecipeCategory.TOOLS).unlockedBy(Items.COPPER_INGOT))
                .make("wrench");
    }
}
