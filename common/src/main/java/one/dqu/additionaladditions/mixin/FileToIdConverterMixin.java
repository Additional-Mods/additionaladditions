package one.dqu.additionaladditions.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import java.util.TreeMap;

/**
 * Removes recipes and advancements when their corresponding features are disabled in the config.
 * Filters the file listing used by datapack registry loading, so disabled entries are never parsed or referenced.
 */
@Mixin(FileToIdConverter.class)
public class FileToIdConverterMixin {
    @ModifyReturnValue(method = "listMatchingResources", at = @At("RETURN"))
    private Map<Identifier, Resource> removeDisabled(Map<Identifier, Resource> resources) {
        FileToIdConverter self = (FileToIdConverter) (Object) this;
        boolean recipes = self.prefix().equals("recipe");
        boolean advancements = self.prefix().equals("advancement");
        if (!recipes && !advancements) return resources;

        Map<Identifier, Resource> filtered = new TreeMap<>(resources);
        filtered.keySet().removeIf(file -> {
            Identifier id = self.fileToId(file);
            if (!id.getNamespace().equals(AdditionalAdditions.NAMESPACE)) return false;
            return recipes ? additionaladditions$isRecipeDisabled(id) : !additionaladditions$isAdvancementEnabled(id);
        });
        return filtered;
    }

    @Unique
    private static boolean additionaladditions$isRecipeDisabled(Identifier identifier) {
        if (!identifier.getNamespace().equals(AdditionalAdditions.NAMESPACE)) {
            return false;
        }

        String name = identifier.getPath().split("/")[0];

        ConfigProperty<?> property = ConfigProperty.getAll().stream()
                .filter((p) -> p.path().getPath().split("/")[0].equals(name))
                .findFirst().orElse(null);

        if (property == null) {
            AdditionalAdditions.LOGGER.warn("[{}] Could not find a matching config property for recipe '{}'!", AdditionalAdditions.NAMESPACE, identifier);
            return false;
        }

        if (property.get() instanceof Toggleable toggleable) {
            return !toggleable.enabled();
        }

        return false;
    }

    @Unique
    private static boolean additionaladditions$isAdvancementEnabled(Identifier identifier) {
        if (!identifier.getNamespace().equals(AdditionalAdditions.NAMESPACE)) {
            return true;
        }

        String name = identifier.getPath().split("/")[0];

        return switch (name) {
            case "fill_album_same_disc", "play_album" -> Config.ALBUM.get().enabled();
            case "obtain_chicken_nugget" -> Config.CHICKEN_NUGGET.get().enabled();
            case "obtain_rose_gold" -> Config.ROSE_GOLD.get().enabled();
            case "obtain_suspicious_dye", "use_all_suspicious_dyes" -> Config.SUSPICIOUS_DYE.get().enabled();
            case "place_rope_world_height" -> Config.ROPE.get().enabled();
            case "play_pocket_jukebox" -> Config.POCKET_JUKEBOX.get().enabled();
            case "use_tinted_redstone_lamp" -> Config.TINTED_REDSTONE_LAMP.get().enabled();
            case "use_watering_can" -> Config.WATERING_CAN.get().enabled();
            case "recipes" -> {
                // datagenned recipe advancements are recipes/<category>/<feature>/<name>, the ones i wrote manually aren't (recipes/<feature>/<name>)
                String[] parts = identifier.getPath().split("/");
                ConfigProperty<?> property = null;
                for (int i = 1; i < parts.length && property == null; i++) {
                    String part = parts[i];
                    property = ConfigProperty.getAll().stream()
                            .filter((p) -> p.path().getPath().split("/")[0].equals(part))
                            .findFirst().orElse(null);
                }

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
    }
}
