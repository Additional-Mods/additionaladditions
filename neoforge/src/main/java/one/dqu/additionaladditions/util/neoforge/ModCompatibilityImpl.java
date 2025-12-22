package one.dqu.additionaladditions.util.neoforge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModList;
import one.dqu.additionaladditions.AdditionalAdditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModCompatibilityImpl {
    private static final List<Supplier<SystemToast>> toasts = new ArrayList<>();

    public static void add(Supplier<Boolean> condition, String description, String... modids) {
        if (!condition.get()) {
            return;
        }

        boolean found = false;
        for (String modid : modids) {
            if (ModList.get().isLoaded(modid)) {
                found = true;
                break;
            }
        }

        if (!found) {
            return;
        }

        AdditionalAdditions.LOGGER.warn("[{}] ⚠️ IMPORTANT", AdditionalAdditions.NAMESPACE);
        AdditionalAdditions.LOGGER.warn("[{}] Mod Incompatibility: {}", AdditionalAdditions.NAMESPACE, description);
        toasts.add(() -> SystemToast.multiline(
                Minecraft.getInstance(),
                SystemToast.SystemToastId.PACK_LOAD_FAILURE,
                Component.literal("Additional Additions"),
                Component.literal(description)
        ));
    }

    public static boolean isModPresent(String... modids) {
        for (String modid : modids) {
            if (ModList.get().isLoaded(modid)) {
                return true;
            }
        }
        return false;
    }

    public static void showToasts() {
        for (Supplier<SystemToast> toastSupplier : toasts) {
            Minecraft.getInstance().getToasts().addToast(toastSupplier.get());
        }
        toasts.clear();
    }
}
