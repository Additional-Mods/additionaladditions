package one.dqu.additionaladditions.core.datagen.template;

import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import one.dqu.additionaladditions.core.datagen.template.recipe.RecipeEntry;
import one.dqu.additionaladditions.core.datagen.template.recipe.RecipeInput;

import java.util.List;
import java.util.Map;

public class Recipes {
    private Recipes() {
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

    // Crafting Transmute

    public static RecipeEntry transmute(RecipeCategory category, RecipeInput input, ItemLike material) {
        return new RecipeEntry((registries, result) ->
                TransmuteRecipeBuilder.transmute(category, input.ingredient(registries), Ingredient.of(material), result.asItem()));
    }

    public static RecipeEntry transmute(RecipeCategory category, ItemLike input, ItemLike material) {
        return transmute(category, RecipeInput.of(input), material);
    }

    public static RecipeEntry transmute(RecipeCategory category, TagKey<Item> input, ItemLike material) {
        return transmute(category, RecipeInput.of(input), material);
    }

    // Smelting, Blasting, Smoking, Campfire

    private static CookingBookCategory cookingBookCategory(RecipeCategory category) {
        return switch (category) {
            case FOOD -> CookingBookCategory.FOOD;
            case BUILDING_BLOCKS, DECORATIONS -> CookingBookCategory.BLOCKS;
            default -> CookingBookCategory.MISC;
        };
    }

    public static RecipeEntry smelting(RecipeCategory category, RecipeInput ingredient, float experience) {
        return new RecipeEntry((registries, result) -> SimpleCookingRecipeBuilder.smelting(
                ingredient.ingredient(registries), category, cookingBookCategory(category), result, experience, 200));
    }

    public static RecipeEntry blasting(RecipeCategory category, RecipeInput ingredient, float experience) {
        return new RecipeEntry((registries, result) -> SimpleCookingRecipeBuilder.blasting(
                ingredient.ingredient(registries), category, cookingBookCategory(category), result, experience, 100));
    }

    public static RecipeEntry smoking(RecipeCategory category, RecipeInput ingredient, float experience) {
        return new RecipeEntry((registries, result) -> SimpleCookingRecipeBuilder.smoking(
                ingredient.ingredient(registries), category, result, experience, 100));
    }

    public static RecipeEntry campfire(RecipeCategory category, RecipeInput ingredient, float experience) {
        return new RecipeEntry((registries, result) -> SimpleCookingRecipeBuilder.campfireCooking(
                ingredient.ingredient(registries), category, result, experience, 600));
    }

    public static List<RecipeEntry> foodCooking(RecipeInput ingredient) {
        return List.of(
                smelting(RecipeCategory.FOOD, ingredient, 0.35f).unlockedBy(ingredient),
                smoking(RecipeCategory.FOOD, ingredient, 0.35f).named("%s_from_smoking").unlockedBy(ingredient),
                campfire(RecipeCategory.FOOD, ingredient, 0.35f).named("%s_from_campfire_cooking").unlockedBy(ingredient)
        );
    }

    public static List<RecipeEntry> foodCooking(ItemLike ingredient) {
        return foodCooking(RecipeInput.of(ingredient));
    }

    public static List<RecipeEntry> foodCooking(TagKey<Item> ingredient) {
        return foodCooking(RecipeInput.of(ingredient));
    }
}
