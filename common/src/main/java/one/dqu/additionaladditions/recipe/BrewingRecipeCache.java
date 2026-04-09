package one.dqu.additionaladditions.recipe;

import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collection;
import java.util.List;

/**
 * Client-side cache of {@link BrewingRecipe}s synced from the server.
 * <p>
 * Since 1.21.2, client doesn't hold the full {@link net.minecraft.world.item.crafting.RecipeManager},
 * so recipe viewer integrations (like JEI) can't access the recipes on client.
 * <p>
 * Sync is handled on NeoForge in JEIBrewingRecipeSync.
 */
public final class BrewingRecipeCache {
    private static volatile List<RecipeHolder<BrewingRecipe>> recipes = List.of();

    private BrewingRecipeCache() {}

    public static void set(Collection<RecipeHolder<BrewingRecipe>> incoming) {
        recipes = List.copyOf(incoming);
    }

    public static List<RecipeHolder<BrewingRecipe>> get() {
        return recipes;
    }
}