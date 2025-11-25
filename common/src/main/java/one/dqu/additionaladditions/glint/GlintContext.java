package one.dqu.additionaladditions.glint;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import one.dqu.additionaladditions.registry.AAMisc;

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
        if (itemStack != null && itemStack.has(AAMisc.GLINT_COLOR_COMPONENT.get())) {
            return itemStack.get(AAMisc.GLINT_COLOR_COMPONENT.get()).color();
        }
        return null;
    }
}
