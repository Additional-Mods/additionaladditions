package dqu.additionaladditions.mixin;

import com.google.gson.JsonElement;
import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    private void removeRecipes(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        HashSet<Identifier> toRemove = new HashSet<>();
        Iterator<Map.Entry<Identifier, JsonElement>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Identifier identifier = iterator.next().getKey();
            if (identifier.getNamespace().equals(AdditionalAdditions.namespace)) {
                switch (identifier.getPath()) {
                    case "watering_can" -> { if (!Config.getBool(ConfigValues.WATERING_CAN)) toRemove.add(identifier); }
                    case "wrench" -> { if (!Config.getBool(ConfigValues.WRENCH)) toRemove.add(identifier); }
                    case "crossbow_with_spyglass" -> { if (!Config.getBool(ConfigValues.CROSSBOWS)) toRemove.add(identifier); }
                    case "glow_stick" -> { if (!Config.getBool(ConfigValues.GLOW_STICK)) toRemove.add(identifier); }
                    case "depth_meter" -> { if (!Config.getBool(ConfigValues.DEPTH_METER)) toRemove.add(identifier); }
                    case "mysterious_bundle" -> { if (!Config.getBool(ConfigValues.MYSTERIOUS_BUNDLE)) toRemove.add(identifier); }
                    case "trident" -> { if (!Config.getBool(ConfigValues.TRIDENT_SHARD)) toRemove.add(identifier); }
                    case "rope" -> { if (!Config.getBool(ConfigValues.ROPES)) toRemove.add(identifier); }
                    case "copper_patina" -> { if (!Config.getBool(ConfigValues.COPPER_PATINA)) toRemove.add(identifier); }
                    case "amethyst_lamp" -> { if (!Config.getBool(ConfigValues.AMETHYST_LAMP)) toRemove.add(identifier); }
                    case "honeyed_apple", "berry_pie" -> { if (!Config.getBool(ConfigValues.FOOD)) toRemove.add(identifier); }
                    case "pocket_jukebox" -> { if (!Config.getBool(ConfigValues.POCKET_JUKEBOX)) toRemove.add(identifier); }
                    case "powered_rails" -> { if (!Config.getBool(ConfigValues.POWERED_RAILS_COPPER_RECIPE)) toRemove.add(identifier); }
                    default -> {
                        if (identifier.getPath().startsWith("rose_gold")) {
                            if (!Config.getBool(ConfigValues.ROSE_GOLD)) toRemove.add(identifier);
                        }
                        if (identifier.getPath().startsWith("fried_egg")) {
                            if (!Config.getBool(ConfigValues.FOOD)) toRemove.add(identifier);
                        }
                        if (identifier.getPath().startsWith("gilded_netherite")) {
                            if (!Config.getBool(ConfigValues.GILDED_NETHERITE)) toRemove.add(identifier);
                            if (Config.getBool(ConfigValues.GOLD_RING)) toRemove.add(identifier);
                        }
                        if (identifier.getPath().startsWith("ring_gilded_netherite")) {
                            if (!Config.getBool(ConfigValues.GILDED_NETHERITE)) toRemove.add(identifier);
                            if (!Config.getBool(ConfigValues.GOLD_RING)) toRemove.add(identifier);
                        }
                    }
                }
            }
        }

        for (Identifier identifier : toRemove) {
            map.remove(identifier);
        }
    }
}
