package one.dqu.additionaladditions.mixin;

import com.google.gson.JsonElement;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
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
        HashSet<ResourceLocation> toRemove = new HashSet<>();
        Iterator<Map.Entry<ResourceLocation, JsonElement>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            ResourceLocation identifier = iterator.next().getKey();
            if (identifier.getNamespace().equals(AdditionalAdditions.NAMESPACE)) {
                switch (identifier.getPath()) {
                    case "wrench" -> { if (!Config.WRENCH.get().enabled()) toRemove.add(identifier); }
                    case "crossbow_with_spyglass" -> { if (!Config.CROSSBOW_WITH_SPYGLASS.get().enabled()) toRemove.add(identifier); }
                    case "glow_stick" -> { if (!Config.GLOW_STICK.get().enabled()) toRemove.add(identifier); }
                    case "depth_meter" -> { if (!Config.BAROMETER.get().enabled()) toRemove.add(identifier); }
                    case "mysterious_bundle" -> { if (!Config.MYSTERIOUS_BUNDLE.get().enabled()) toRemove.add(identifier); }
                    case "trident" -> { if (!Config.TRIDENT_SHARD.get().enabled()) toRemove.add(identifier); }
                    case "rope" -> { if (!Config.ROPE.get().enabled()) toRemove.add(identifier); }
                    case "copper_patina" -> { if (!Config.COPPER_PATINA.get().enabled()) toRemove.add(identifier); }
                    case "amethyst_lamp" -> { if (!Config.AMETHYST_LAMP.get().enabled()) toRemove.add(identifier); }
                    case "berry_pie" -> { if (!Config.BERRY_PIE.get().enabled()) toRemove.add(identifier); }
                    case "honeyed_apple" -> { if (!Config.HONEYED_APPLE.get().enabled()) toRemove.add(identifier); }
                    case "pocket_jukebox" -> { if (!Config.POCKET_JUKEBOX.get().enabled()) toRemove.add(identifier); }
                    case "powered_rails" -> { if (!Config.POWERED_RAILS_COPPER_RECIPE.get().enabled()) toRemove.add(identifier); }
                    case "watering_can" -> { if (!Config.WATERING_CAN.get().enabled()) toRemove.add(identifier); }
                    default -> {
                        if (identifier.getPath().startsWith("rose_gold")) {
                            if (!Config.ROSE_GOLD.get().enabled()) toRemove.add(identifier);
                        }
                        if (identifier.getPath().startsWith("fried_egg")) {
                            if (!Config.FRIED_EGG.get().enabled()) toRemove.add(identifier);
                        }
                    }
                }
            }
        }

        for (ResourceLocation identifier : toRemove) {
            map.remove(identifier);
        }
    }
}
