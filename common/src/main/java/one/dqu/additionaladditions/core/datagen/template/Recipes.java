package one.dqu.additionaladditions.core.datagen.template;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Recipes {
    private Recipes() {
    }

    public static final class RecipeEntry {
        private final BiFunction<HolderGetter<Item>, ItemLike, RecipeBuilder> factory;
        private final List<ItemLike> unlocks = new ArrayList<>();

        private RecipeEntry(BiFunction<HolderGetter<Item>, ItemLike, RecipeBuilder> factory) {
            this.factory = factory;
        }

        // recipe book unlocks
        public RecipeEntry unlockedBy(ItemLike... items) {
            Collections.addAll(unlocks, items);
            return this;
        }

        public RecipeBuilder builder(HolderGetter<Item> registries, ItemLike result) {
            return factory.apply(registries, result);
        }

        public List<ItemLike> unlocks() {
            return unlocks;
        }
    }

    // Shapeless

    public static RecipeEntry shapeless(List<ItemLike> ingredients, RecipeCategory category, int count) {
        return new RecipeEntry((registries, result) -> {
            ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(registries, category, result, count);
            ingredients.forEach(builder::requires);
            return builder;
        });
    }

    public static RecipeEntry shapeless(RecipeCategory category, int count, ItemLike... ingredients) {
        return shapeless(List.of(ingredients), category, count);
    }

    public static RecipeEntry shapeless(RecipeCategory category, ItemLike... ingredients) {
        return shapeless(List.of(ingredients), category, 1);
    }

    // Shaped

    public static RecipeEntry shaped(List<String> pattern, Map<Character, ItemLike> key, RecipeCategory category, int count) {
        return new RecipeEntry((registries, result) -> {
            ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(registries, category, result, count);
            pattern.forEach(builder::pattern);
            key.forEach(builder::define);
            return builder;
        });
    }

    public static RecipeEntry shaped(String row1, String row2, String row3, Map<Character, ItemLike> key, RecipeCategory category, int count) {
        return shaped(List.of(row1, row2, row3), key, category, count);
    }

    public static RecipeEntry shaped(String row1, String row2, String row3, Map<Character, ItemLike> key, RecipeCategory category) {
        return shaped(List.of(row1, row2, row3), key, category, 1);
    }

    public static RecipeEntry shaped(String row1, String row2, Map<Character, ItemLike> key, RecipeCategory category, int count) {
        return shaped(List.of(row1, row2), key, category, count);
    }

    public static RecipeEntry shaped(String row1, String row2, Map<Character, ItemLike> key, RecipeCategory category) {
        return shaped(List.of(row1, row2), key, category, 1);
    }
}
