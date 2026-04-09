package one.dqu.additionaladditions.recipe.neoforge;

import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import one.dqu.additionaladditions.recipe.BrewingRecipe;
import one.dqu.additionaladditions.recipe.BrewingRecipeCache;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.util.ModCompatibility;

/**
 * Syncs custom {@link BrewingRecipe}s from the server to the client so that JEI can display them.
 * @see BrewingRecipeCache
 */
public final class JEIBrewingRecipeSync {
    private JEIBrewingRecipeSync() {}

    public static void register() {
        if (!ModCompatibility.isModPresent("jei")) return;

        NeoForge.EVENT_BUS.addListener(OnDatapackSyncEvent.class, JEIBrewingRecipeSync::onDatapackSync);
        if (FMLEnvironment.dist.isClient()) {
            NeoForge.EVENT_BUS.addListener(RecipesReceivedEvent.class, JEIBrewingRecipeSync::onRecipesReceived);
        }
    }

    private static void onDatapackSync(OnDatapackSyncEvent event) {
        event.sendRecipes(AAMisc.BREWING_RECIPE_TYPE.get());
    }

    private static void onRecipesReceived(RecipesReceivedEvent event) {
        if (!event.getRecipeTypes().contains(AAMisc.BREWING_RECIPE_TYPE.get())) return;
        BrewingRecipeCache.set(event.getRecipeMap().byType(AAMisc.BREWING_RECIPE_TYPE.get()));
    }
}
