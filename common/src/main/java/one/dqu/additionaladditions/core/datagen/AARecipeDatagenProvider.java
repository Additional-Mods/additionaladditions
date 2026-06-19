package one.dqu.additionaladditions.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.registry.AAItems;

public class AARecipeDatagenProvider extends RecipeProvider {
    public AARecipeDatagenProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        for (AAItemDatagen.Entry entry : AAItemDatagen.entries()) {
            Recipes.RecipeEntry recipe = entry.recipe();
            if (recipe == null) {
                continue;
            }

            Item result = BuiltInRegistries.ITEM.getValue(entry.id());
            RecipeBuilder builder = recipe.builder(this.items, result);
            for (ItemLike unlock : recipe.unlocks()) {
                builder.unlockedBy(getHasName(unlock), has(unlock));
            }
            builder.save(this.output, recipeKey(entry.id(), result));
        }
    }

    // need <feature>/<item> path for RecipeManagerMixin, so that recipes can be disabled with config
    private static ResourceKey<Recipe<?>> recipeKey(Identifier itemId, Item result) {
        Identifier configPath = AAItems.configPathFor(result);
        Identifier id = configPath != null
                ? Identifier.tryBuild(AdditionalAdditions.NAMESPACE, configPath.getPath().split("/")[0] + "/" + itemId.getPath())
                : itemId;
        return ResourceKey.create(Registries.RECIPE, id);
    }
}
