package one.dqu.additionaladditions.misc;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class REICompat implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        if (Config.ALBUM.get().enabled()) {
            registerAlbumRecipes(registry);
        }
        if (Config.ROSE_GOLD.get().enabled()) {
            roseGoldTransmuteRecipe(registry);
        }
        // couldnt figure out how to add suspicious dye recipe stuff
    }

    // copy pasted from JEICompat
    private void registerAlbumRecipes(DisplayRegistry registry) {
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();

        // copied from albumdyerecipe
        final Map<DyeColor, Item> dyeToAlbumMap = Map.ofEntries(
                Map.entry(DyeColor.WHITE, AAItems.WHITE_ALBUM.get()),
                Map.entry(DyeColor.LIGHT_GRAY, AAItems.LIGHT_GRAY_ALBUM.get()),
                Map.entry(DyeColor.GRAY, AAItems.GRAY_ALBUM.get()),
                Map.entry(DyeColor.BLACK, AAItems.BLACK_ALBUM.get()),
                Map.entry(DyeColor.BROWN, AAItems.BROWN_ALBUM.get()),
                Map.entry(DyeColor.RED, AAItems.RED_ALBUM.get()),
                Map.entry(DyeColor.ORANGE, AAItems.ORANGE_ALBUM.get()),
                Map.entry(DyeColor.YELLOW, AAItems.YELLOW_ALBUM.get()),
                Map.entry(DyeColor.LIME, AAItems.LIME_ALBUM.get()),
                Map.entry(DyeColor.GREEN, AAItems.GREEN_ALBUM.get()),
                Map.entry(DyeColor.CYAN, AAItems.CYAN_ALBUM.get()),
                Map.entry(DyeColor.LIGHT_BLUE, AAItems.LIGHT_BLUE_ALBUM.get()),
                Map.entry(DyeColor.BLUE, AAItems.BLUE_ALBUM.get()),
                Map.entry(DyeColor.PURPLE, AAItems.PURPLE_ALBUM.get()),
                Map.entry(DyeColor.MAGENTA, AAItems.MAGENTA_ALBUM.get()),
                Map.entry(DyeColor.PINK, AAItems.PINK_ALBUM.get())
        );

        for (Map.Entry<DyeColor, Item> entry : dyeToAlbumMap.entrySet()) {
            DyeColor color = entry.getKey();
            Item result = entry.getValue();

            Ingredient dyeIngredient = Ingredient.of(DyeItem.byColor(color));

            // list of albums excluding the result album (cant dye to same color)
            List<ItemStack> validAlbums = new ArrayList<>();
            var albums = BuiltInRegistries.ITEM.getTagOrEmpty(AAMisc.ALBUMS_TAG);
            for (Holder<Item> albumItem : albums) {
                if (albumItem.value() != result) {
                    validAlbums.add(new ItemStack(albumItem.value()));
                }
            }
            Ingredient albumIngredient = Ingredient.of(validAlbums.toArray(new ItemStack[0]));

            NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, albumIngredient, dyeIngredient);
            ShapelessRecipe recipe = new ShapelessRecipe(
                    "additionaladditions:jei_albums",
                    CraftingBookCategory.MISC,
                    new ItemStack(result),
                    inputs
            );

            ResourceLocation location = ResourceLocation.fromNamespaceAndPath("additionaladditions", "jei_album_" + color.getName());
            recipes.add(new RecipeHolder<>(location, recipe));
        }

        for (RecipeHolder<CraftingRecipe> recipeHolder : recipes) {
            registry.add(DefaultCraftingDisplay.of(recipeHolder));
        }
    }

    // copy pasted from JEICompat
    private void roseGoldTransmuteRecipe(DisplayRegistry registry) {
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();

        // copied from RoseGoldTransmuteRecipe
        final Map<Item, Item> transmuteMap = Map.of(
                Items.IRON_SWORD, AAItems.ROSE_GOLD_SWORD.get(),
                Items.IRON_SHOVEL, AAItems.ROSE_GOLD_SHOVEL.get(),
                Items.IRON_PICKAXE, AAItems.ROSE_GOLD_PICKAXE.get(),
                Items.IRON_AXE, AAItems.ROSE_GOLD_AXE.get(),
                Items.IRON_HOE, AAItems.ROSE_GOLD_HOE.get(),
                Items.IRON_HELMET, AAItems.ROSE_GOLD_HELMET.get(),
                Items.IRON_CHESTPLATE, AAItems.ROSE_GOLD_CHESTPLATE.get(),
                Items.IRON_LEGGINGS, AAItems.ROSE_GOLD_LEGGINGS.get(),
                Items.IRON_BOOTS, AAItems.ROSE_GOLD_BOOTS.get(),
                Items.IRON_HORSE_ARMOR, AAItems.ROSE_GOLD_HORSE_ARMOR.get()
        );

        for (Map.Entry<Item, Item> entry : transmuteMap.entrySet()) {
            Item ironItem = entry.getKey();
            String ironId = BuiltInRegistries.ITEM.getKey(ironItem).getPath();
            Item roseGoldItem = entry.getValue();

            Ingredient ironIngredient = Ingredient.of(ironItem);
            Ingredient roseGoldIngotIngredient = Ingredient.of(AAItems.ROSE_GOLD_INGOT.get());

            NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, ironIngredient, roseGoldIngotIngredient);
            ShapelessRecipe recipe = new ShapelessRecipe(
                    "additionaladditions:jei_rose_gold_transmute_" + ironId,
                    CraftingBookCategory.EQUIPMENT,
                    new ItemStack(roseGoldItem),
                    inputs
            );

            ResourceLocation location = ResourceLocation.fromNamespaceAndPath("additionaladditions", "jei_rose_gold_transmute_" + ironId);
            recipes.add(new RecipeHolder<>(location, recipe));
        }

        for (RecipeHolder<CraftingRecipe> recipeHolder : recipes) {
            registry.add(DefaultCraftingDisplay.of(recipeHolder));
        }
    }
}
