package one.dqu.additionaladditions.core.datagen.template.recipe;

import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;

/**
 * Special recipe types don't have recipe book (so no unlocking / groups), so a builder is not needed.
 * This class wraps a recipe in a builder so it can be used in datagen.
 */
public final class SpecialRecipeBuilder implements RecipeBuilder {
    private final Recipe<?> recipe;

    public SpecialRecipeBuilder(Recipe<?> recipe) {
        this.recipe = recipe;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(String group) {
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> key) {
        output.accept(key, recipe, null);
    }
}
