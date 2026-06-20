package one.dqu.additionaladditions.feature.food;

import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumables;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.type.FoodConfig;
import one.dqu.additionaladditions.core.builder.AAItem;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.registry.AATags;

import java.util.function.Supplier;

public class FoodContent {
    private static AAItem<Item> food(ConfigProperty<FoodConfig> config, Item anchor) {
        return AAReg.item()
                .config(config)
                .props(p -> p
                        .delayedComponent(DataComponents.FOOD, _ -> config.get().food())
                        .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_FOOD))
                .creative(anchor, CreativeModeTabs.FOOD_AND_DRINKS, CreativePosition.AFTER)
                .model(Models::flat);
    }

    public static Supplier<Item> friedEgg() {
        return food(Config.FRIED_EGG, Items.COOKED_RABBIT)
                .recipe(Recipes.foodCooking(AATags.C_EGGS))
                .make("fried_egg");
    }

    public static Supplier<Item> berryPie() {
        return food(Config.BERRY_PIE, Items.PUMPKIN_PIE)
                .tags(AATags.C_FOODS_PIE)
                .recipe(Recipes.shapeless(RecipeCategory.FOOD, Items.WHEAT, Items.SWEET_BERRIES, Items.SUGAR, Items.EGG).unlockedBy(Items.SWEET_BERRIES))
                .make("berry_pie");
    }

    public static Supplier<Item> honeyedApple() {
        return food(Config.HONEYED_APPLE, Items.APPLE)
                .tags(AATags.C_FOODS)
                .recipe(Recipes.shapeless(RecipeCategory.FOOD, Items.HONEY_BOTTLE, Items.APPLE).unlockedBy(Items.HONEY_BOTTLE))
                .make("honeyed_apple");
    }

    public static Supplier<Item> chickenNugget() {
        return food(Config.CHICKEN_NUGGET, Items.ROTTEN_FLESH)
                .tags(AATags.C_FOODS)
                .make("chicken_nugget");
    }
}
