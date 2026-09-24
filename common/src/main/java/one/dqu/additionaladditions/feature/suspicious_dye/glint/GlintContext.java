package one.dqu.additionaladditions.feature.suspicious_dye.glint;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import one.dqu.additionaladditions.registry.AAMisc;
import org.jetbrains.annotations.Nullable;

/**
 * Glint color of the item stack currently being submitted for rendering.
 */
@Environment(EnvType.CLIENT)
public class GlintContext {
    private static @Nullable DyeColor current;

    public static @Nullable DyeColor get() {
        return current;
    }

    public static void set(@Nullable DyeColor color) {
        current = color;
    }

    public static @Nullable DyeColor colorOf(ItemStack stack) {
        GlintColor glintColor = stack.get(AAMisc.GLINT_COLOR_COMPONENT.get());
        return glintColor != null ? glintColor.color() : null;
    }
}
