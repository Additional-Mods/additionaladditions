package one.dqu.additionaladditions.recipe.neoforge;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import one.dqu.additionaladditions.recipe.BrewingRecipe;
import one.dqu.additionaladditions.recipe.ClientRecipeCache;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.util.ModCompatibility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Syncs custom recipes from the server to the client so that JEI can display them.
 * @see ClientRecipeCache
 */
public final class JEIBrewingRecipeSync {
    private JEIBrewingRecipeSync() {}

    public static void register() {
        if (!ModCompatibility.isModPresent("jei")) return;

        NeoForge.EVENT_BUS.addListener(OnDatapackSyncEvent.class, JEIBrewingRecipeSync::onDatapackSync);
        if (FMLEnvironment.getDist().isClient()) {
            NeoForge.EVENT_BUS.addListener(RecipesReceivedEvent.class, JEIBrewingRecipeSync::onRecipesReceived);
        }
    }

    private static void onDatapackSync(OnDatapackSyncEvent event) {
        event.sendRecipes(AAMisc.BREWING_RECIPE_TYPE.get());
    }

    private static void onRecipesReceived(RecipesReceivedEvent event) {
        if (!event.getRecipeTypes().contains(AAMisc.BREWING_RECIPE_TYPE.get())) return;
        List<? extends RecipeHolder<?>> recipes = List.copyOf(event.getRecipeMap().byType(AAMisc.BREWING_RECIPE_TYPE.get()));
        Map<RecipeType<?>, List<RecipeHolder<?>>> map = new HashMap<>();
        //noinspection unchecked
        map.put(AAMisc.BREWING_RECIPE_TYPE.get(), (List<RecipeHolder<?>>) recipes);
        ClientRecipeCache.set(map);
    }
}
