package one.dqu.additionaladditions.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.*;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.misc.BrewingRecipe;
import one.dqu.additionaladditions.registry.AAMisc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    /**
     * Removes recipes when their corresponding features are disabled in the config.
     */
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

    @Shadow
    private Map<ResourceKey<RecipePropertySet>, RecipePropertySet> propertySets;

    /**
     * Registers custom recipe property sets.
     * They are used to tell client which ingredients are valid to be inserted into slots.
     */
    @Inject(method = "finalizeRecipeLoading", at = @At("RETURN"))
    private void additionaladditions$addPropertySets(FeatureFlagSet featureFlagSet, CallbackInfo ci) {
        List<Ingredient> ingredients = ((RecipeManager)(Object)this).getRecipes().stream()
                .filter(h -> h.value().getType() == AAMisc.BREWING_RECIPE_TYPE.get())
                .map(h -> ((BrewingRecipe) h.value()).getIngredient())
                .toList();

        var newMap = new HashMap<>(this.propertySets);
        newMap.put(AAMisc.BREWING_RECIPE_PROPERTY_SET, RecipePropertySet.create(ingredients));
        this.propertySets = Collections.unmodifiableMap(newMap);
    }
}
