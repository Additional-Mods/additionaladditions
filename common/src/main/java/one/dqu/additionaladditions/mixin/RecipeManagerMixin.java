package one.dqu.additionaladditions.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Removes recipes when their corresponding features are disabled in the config.
 */
@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @ModifyExpressionValue(
            method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/world/item/crafting/RecipeMap;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/RecipeMap;create(Ljava/lang/Iterable;)Lnet/minecraft/world/item/crafting/RecipeMap;")
    )
    private RecipeMap removeRecipes(RecipeMap recipes) {
        List<RecipeHolder<?>> list = new ArrayList<>(recipes.values());

        list.removeIf(holder -> {
            ResourceLocation identifier = holder.id().location();
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
        });

        return RecipeMap.create(list);
    }
}
