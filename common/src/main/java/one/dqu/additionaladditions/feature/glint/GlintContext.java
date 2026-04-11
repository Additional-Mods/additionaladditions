package one.dqu.additionaladditions.feature.glint;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import one.dqu.additionaladditions.registry.AAMisc;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class GlintContext {
    private static final ThreadLocal<DyeColor> currentDyeColor = new ThreadLocal<>();

    private static final Map<Object, DyeColor> pendingQuadColors = new IdentityHashMap<>();

    public static void setCurrentItem(@Nullable ItemStack itemStack) {
        if (itemStack != null && itemStack.has(AAMisc.GLINT_COLOR_COMPONENT.get())) {
            currentDyeColor.set(itemStack.get(AAMisc.GLINT_COLOR_COMPONENT.get()).color());
        } else {
            currentDyeColor.set(null);
        }
    }

    public static void setDyeColor(@Nullable DyeColor color) {
        currentDyeColor.set(color);
    }

    public static @Nullable DyeColor getDyeColor() {
        return currentDyeColor.get();
    }

    public static void setColorForQuads(Object quads, @Nullable DyeColor color) {
        if (color != null) {
            pendingQuadColors.put(quads, color);
        }
    }

    public static @Nullable DyeColor getAndClearColorForQuads(Object quads) {
        return pendingQuadColors.remove(quads);
    }
}
