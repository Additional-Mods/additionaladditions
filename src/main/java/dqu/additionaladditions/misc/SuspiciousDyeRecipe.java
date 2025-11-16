package dqu.additionaladditions.misc;

import dqu.additionaladditions.item.SuspiciousDyeItem;
import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class SuspiciousDyeRecipe extends CustomRecipe {
    public static final RecipeSerializer<SuspiciousDyeRecipe> SUSPICIOUS_DYE_RECIPE_SERIALIZER = new SimpleCraftingRecipeSerializer<>(SuspiciousDyeRecipe::new);

    public SuspiciousDyeRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        int glint = 0;
        int dye = 0;

        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack stack = recipeInput.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.hasFoil()) {
                    glint++;
                    continue;
                }

                if (stack.is(AdditionalItems.SUSPICIOUS_DYES_TAG)) {
                    dye++;
                    continue;
                }

                return false;
            }
        }

        return glint == 1 && dye == 1;
    }

    @Override
    public ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        ItemStack itemStack = recipeInput.items().stream().filter(item -> !item.isEmpty() && !item.is(AdditionalItems.SUSPICIOUS_DYES_TAG)).findFirst().orElse(ItemStack.EMPTY);
        ItemStack dye = recipeInput.items().stream().filter(item -> !item.isEmpty() && item.is(AdditionalItems.SUSPICIOUS_DYES_TAG)).findFirst().orElse(ItemStack.EMPTY);

        if (itemStack.isEmpty() || dye.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (dye.getItem() instanceof SuspiciousDyeItem suspiciousDyeItem) {
            DyeColor color = suspiciousDyeItem.getDyeColor();
            ItemStack copyStack = itemStack.copy();
            copyStack.set(AdditionalItems.GLINT_COLOR_COMPONENT, color);
            return copyStack;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i * j >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SuspiciousDyeRecipe.SUSPICIOUS_DYE_RECIPE_SERIALIZER;
    }
}
