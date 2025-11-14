package dqu.additionaladditions.render;

import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.world.item.ItemStack;

public class GlintColorRenderer {
    private static int current = -1;

    public static int getGlintColor() {
        return current;
    }

    public static void setCurrentItem(ItemStack item) {
        if (item != null && item.has(AdditionalItems.GLINT_COLOR_COMPONENT)) {
            current = item.get(AdditionalItems.GLINT_COLOR_COMPONENT);
        } else {
            current = -1;
        }
    }
}
