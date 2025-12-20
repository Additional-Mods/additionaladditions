package one.dqu.additionaladditions.misc;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;

import java.util.Map;

public class AlbumDyeRecipe extends CustomRecipe {
    public AlbumDyeRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        int albumCount = 0;
        int dyeCount = 0;

        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack stack = recipeInput.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(AAMisc.ALBUMS_TAG)) {
                    albumCount++;
                    continue;
                }

                if (stack.getItem() instanceof DyeItem) {
                    dyeCount++;
                    continue;
                }

                return false;
            }
        }

        return albumCount == 1 && dyeCount == 1;
    }

    @Override
    public ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        ItemStack albumStack = recipeInput.items().stream()
                .filter(item -> !item.isEmpty() && item.is(AAMisc.ALBUMS_TAG))
                .findFirst()
                .orElse(ItemStack.EMPTY);

        ItemStack dyeStack = recipeInput.items().stream()
                .filter(item -> !item.isEmpty() && item.getItem() instanceof DyeItem)
                .findFirst()
                .orElse(ItemStack.EMPTY);

        if (albumStack.isEmpty() || dyeStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (dyeStack.getItem() instanceof DyeItem dyeItem) {
            DyeColor color = dyeItem.getDyeColor();

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

            Item resultItem = dyeToAlbumMap.get(color);
            if (resultItem == null) {
                return ItemStack.EMPTY;
            }

            if (albumStack.is(resultItem)) {
                return ItemStack.EMPTY;
            }

            return albumStack.transmuteCopy(resultItem);
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i * j >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AAMisc.ALBUM_DYE_RECIPE_SERIALIZER.get();
    }
}

