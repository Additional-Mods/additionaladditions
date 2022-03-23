package dqu.additionaladditions.mixin;

import com.google.gson.JsonElement;
import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

@Mixin(ServerAdvancementManager.class)
public class ServerAdvancementManagerMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    private void removeAdvancements(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        HashSet<ResourceLocation> toRemove = new HashSet<>();
        Iterator<Map.Entry<ResourceLocation, JsonElement>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            ResourceLocation identifier = iterator.next().getKey();
            if (identifier.getNamespace().equals(AdditionalAdditions.namespace)) {
                switch (identifier.getPath()) {
                    case "obtain_adoghr_disc" -> { if (!Config.getBool(ConfigValues.MUSIC_DISCS)) toRemove.add(identifier); }
                    case "obtain_chicken_nugget" -> { if (!Config.getBool(ConfigValues.CHICKEN_NUGGET)) toRemove.add(identifier); }
                    case "obtain_glow_stick" -> { if (!Config.getBool(ConfigValues.GLOW_STICK)) toRemove.add(identifier); }
                    case "obtain_mysterious_bundle" -> { if (!Config.getBool(ConfigValues.MYSTERIOUS_BUNDLE)) toRemove.add(identifier); }
                    case "obtain_pocket_jukebox" -> { if (!Config.getBool(ConfigValues.POCKET_JUKEBOX)) toRemove.add(identifier); }
                    case "obtain_rose_gold" -> { if (!Config.getBool(ConfigValues.ROSE_GOLD)) toRemove.add(identifier); }
                    case "obtain_scoped_crossbow", "shot_self_scoped_crossbow" -> { if (!Config.getBool(ConfigValues.CROSSBOWS)) toRemove.add(identifier); }
                    case "use_watering_can" -> { if (!Config.getBool(ConfigValues.WATERING_CAN)) toRemove.add(identifier); }
                }
            }
        }

        for (ResourceLocation identifier : toRemove) {
            map.remove(identifier);
        }
    }
}
