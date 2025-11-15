package dqu.additionaladditions.glint;

import dqu.additionaladditions.registry.AdditionalItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class GlintContext {
    private static final ThreadLocal<ItemStack> currentItem = new ThreadLocal<>();

    public static void setCurrentItem(ItemStack itemStack) {
        currentItem.set(itemStack);
    }

    public static ItemStack getCurrentItem() {
        return currentItem.get();
    }

    public static DyeColor getDyeColor() {
        ItemStack itemStack = getCurrentItem();
        if (itemStack != null && itemStack.has(AdditionalItems.GLINT_COLOR_COMPONENT)) {
            return itemStack.get(AdditionalItems.GLINT_COLOR_COMPONENT);
        }
        return null;
    }
}
