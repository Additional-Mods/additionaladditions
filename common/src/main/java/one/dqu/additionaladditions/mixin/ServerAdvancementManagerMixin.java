package one.dqu.additionaladditions.mixin;

import com.google.gson.JsonElement;
import one.dqu.additionaladditions.AdditionalAdditions;
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

/**
 * Removes advancements when their corresponding features are disabled in the config.
 */
@Mixin(ServerAdvancementManager.class)
public class ServerAdvancementManagerMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    private void removeAdvancements(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        HashSet<ResourceLocation> toRemove = new HashSet<>();
        Iterator<Map.Entry<ResourceLocation, JsonElement>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            ResourceLocation identifier = iterator.next().getKey();
            if (identifier.getNamespace().equals(AdditionalAdditions.NAMESPACE)) {
                switch (identifier.getPath()) {
                    //TODO bring this back once i rework advancements
                }
            }
        }

        for (ResourceLocation identifier : toRemove) {
            map.remove(identifier);
        }
    }
}
