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
        map.entrySet().removeIf(entry -> {
            ResourceLocation identifier = entry.getKey();
            if (!identifier.getNamespace().equals(AdditionalAdditions.NAMESPACE)) {
                return false;
            }

            String name = identifier.getPath().split("/")[0];

            return !switch (name) {
                case "fill_album_same_disc", "play_album" -> Config.ALBUM.get().enabled();
                case "obtain_chicken_nugget" -> Config.CHICKEN_NUGGET.get().enabled();
                case "obtain_rose_gold" -> Config.ROSE_GOLD.get().enabled();
                case "obtain_suspicious_dye", "use_all_suspicious_dyes" -> Config.SUSPICIOUS_DYE.get().enabled();
                case "place_rope_world_height" -> Config.ROPE.get().enabled();
                case "play_pocket_jukebox" -> Config.POCKET_JUKEBOX.get().enabled();
                case "use_tinted_redstone_lamp" -> Config.TINTED_REDSTONE_LAMP.get().enabled();
                case "use_watering_can" -> Config.WATERING_CAN.get().enabled();
                case "recipes" -> {
                    String feature = identifier.getPath().split("/")[1];

                    ConfigProperty<?> property = ConfigProperty.getAll().stream()
                            .filter((p) -> p.path().getPath().split("/")[0].equals(feature))
                            .findFirst().orElse(null);

                    if (property == null) {
                        AdditionalAdditions.LOGGER.warn("[{}] Could not find a matching config property for advancement '{}'!", AdditionalAdditions.NAMESPACE, identifier);
                        yield true;
                    }

                    if (property.get() instanceof Toggleable toggleable) {
                        yield toggleable.enabled();
                    }

                    yield true;
                }
                default -> {
                    AdditionalAdditions.LOGGER.warn("[{}] Advancement '{}' not recognized!", AdditionalAdditions.NAMESPACE, identifier);
                    yield true;
                }
            };
        });
    }
}
