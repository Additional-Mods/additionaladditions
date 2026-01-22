package one.dqu.additionaladditions.util.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
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
            if (FabricLoader.getInstance().isModLoaded(modid)) {
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
            if (FabricLoader.getInstance().isModLoaded(modid)) {
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

    public static boolean isClientSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
