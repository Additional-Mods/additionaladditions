package one.dqu.additionaladditions.mixin;

import com.google.gson.JsonElement;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;

/**
 * Removes recipes when their corresponding features are disabled in the config.
 */
@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    private void removeRecipes(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        map.entrySet().removeIf(entry -> {
            ResourceLocation identifier = entry.getKey();
            if (!identifier.getNamespace().equals(AdditionalAdditions.NAMESPACE)) {
                return false;
            }

            String name = identifier.getPath().split("/")[0];

            ConfigProperty<?> property = Config.getAllConfigProperties().stream()
                    .filter((p) -> p.path().getPath().split("/")[0].equals(name))
                    .findFirst().orElse(null);

            if (property == null) {
                return false;
            }

            if (property.get() instanceof Toggleable toggleable) {
                return !toggleable.enabled();
            }

            return false;
        });
    }
}
