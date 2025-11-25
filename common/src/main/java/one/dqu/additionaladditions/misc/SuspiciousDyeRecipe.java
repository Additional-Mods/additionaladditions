package one.dqu.additionaladditions.misc;

import one.dqu.additionaladditions.glint.GlintColor;
import one.dqu.additionaladditions.item.SuspiciousDyeItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.registry.AAMisc;

public class SuspiciousDyeRecipe extends CustomRecipe {
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
                if (stack.is(AAMisc.SUSPICIOUS_DYES_TAG)) {
                    dye++;
                    continue;
                }

                if (stack.hasFoil()) {
                    glint++;
                    continue;
                }

                return false;
            }
        }

        return glint == 1 && dye == 1;
    }

    @Override
    public ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        ItemStack itemStack = recipeInput.items().stream().filter(item -> !item.isEmpty() && !item.is(AAMisc.SUSPICIOUS_DYES_TAG)).findFirst().orElse(ItemStack.EMPTY);
        ItemStack dye = recipeInput.items().stream().filter(item -> !item.isEmpty() && item.is(AAMisc.SUSPICIOUS_DYES_TAG)).findFirst().orElse(ItemStack.EMPTY);

        if (itemStack.isEmpty() || dye.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (dye.getItem() instanceof SuspiciousDyeItem suspiciousDyeItem) {
            DyeColor color = suspiciousDyeItem.getDyeColor();
            ItemStack copyStack = itemStack.copy();
            copyStack.set(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(color));
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
        return AAMisc.SUSPICIOUS_DYE_RECIPE_SERIALIZER.get();
    }
}
