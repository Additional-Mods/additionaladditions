package one.dqu.additionaladditions.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.core.datagen.template.recipe.RecipeEntry;
import one.dqu.additionaladditions.core.datagen.template.recipe.RecipeInput;
import one.dqu.additionaladditions.registry.AAItems;
import org.jetbrains.annotations.Nullable;

public class AARecipeDatagenProvider extends RecipeProvider {
    public AARecipeDatagenProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        for (AAItemDatagen.Entry entry : AAItemDatagen.entries()) {
            Item owner = BuiltInRegistries.ITEM.getValue(entry.id());

            for (RecipeEntry recipe : entry.recipes()) {
                ItemLike result = recipe.result() != null ? recipe.result() : owner;

                RecipeBuilder builder = recipe.builder(this.items, result);
                if (recipe.group() != null) {
                    builder.group(recipe.group());
                }
                for (RecipeInput unlock : recipe.unlocks()) {
                    switch (unlock) {
                        case RecipeInput.OfItem(ItemLike item) -> builder.unlockedBy(getHasName(item), has(item));
                        case RecipeInput.OfTag(TagKey<Item> tag) ->
                                builder.unlockedBy("has_" + tag.location().getPath(), has(tag));
                    }
                }
                builder.save(this.output, recipeKey(owner, result, recipe.name()));
            }
        }
    }

    // need <feature>/<recipe>.json path for RecipeManagerMixin, so that recipes can be disabled with config
    // %s in the name is replaced with the result path (e.g. %s_smoking)
    private static ResourceKey<Recipe<?>> recipeKey(Item owner, ItemLike result, @Nullable String name) {
        String resultPath = BuiltInRegistries.ITEM.getKey(result.asItem()).getPath();
        String path = name != null ? name.replace("%s", resultPath) : resultPath;
        Identifier configPath = AAItems.configPathFor(owner);
        Identifier id = configPath != null
                ? Identifier.tryBuild(AdditionalAdditions.NAMESPACE, configPath.getPath().split("/")[0] + "/" + path)
                : Identifier.tryBuild(AdditionalAdditions.NAMESPACE, path);
        return ResourceKey.create(Registries.RECIPE, id);
    }
}
