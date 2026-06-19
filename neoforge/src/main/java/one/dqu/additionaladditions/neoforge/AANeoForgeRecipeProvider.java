package one.dqu.additionaladditions.neoforge;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.core.datagen.AARecipeDatagenProvider;

import java.util.concurrent.CompletableFuture;

public class AANeoForgeRecipeProvider extends RecipeProvider.Runner {
    public AANeoForgeRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new AARecipeDatagenProvider(registries, output);
    }

    @Override
    public String getName() {
        return AdditionalAdditions.NAMESPACE + " Recipes";
    }
}
