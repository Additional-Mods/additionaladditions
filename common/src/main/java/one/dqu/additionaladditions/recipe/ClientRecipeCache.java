package one.dqu.additionaladditions.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.crafting.*;

import java.util.*;

/**
 * Client-side cache of recipes synced from the server.
 * <p>
 * Since 1.21.2, client doesn't hold the full {@link net.minecraft.world.item.crafting.RecipeManager},
 * so recipe viewer integrations (like JEI) can't access the recipes on client.
 * <p>
 * Sync is handled on NeoForge in JEIBrewingRecipeSync.
 */
@Environment(EnvType.CLIENT)
public final class ClientRecipeCache {
    private static volatile Map<RecipeType<?>, List<RecipeHolder<?>>> cache = new HashMap<>();
    private static Collection<Runnable> listeners = new ArrayList<>();

    private ClientRecipeCache() {}

    public static void set(Map<RecipeType<?>, List<RecipeHolder<?>>> incoming) {
        cache = incoming;
        if (!listeners.isEmpty()) {
            listeners.forEach(Runnable::run);
            listeners.clear();
        }
    }

    public static <R extends RecipeInput, T extends Recipe<R>> List<RecipeHolder<T>> get(RecipeType<T> type) {
        //noinspection unchecked
        return (List<RecipeHolder<T>>) (List<?>) cache.getOrDefault(type, Collections.emptyList());
    }

    public static void onceAvailable(Runnable action) {
        if (!cache.isEmpty()) {
            action.run();
        } else {
            listeners.add(action);
        }
    }
}