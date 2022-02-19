package dqu.additionaladditions.mixin;


import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalPotions;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingRecipeRegistryMixin {
    @Shadow
    private static void registerPotionRecipe(Potion input, Item item, Potion output) {}

    @Inject(method = "registerDefaults", at = @At("TAIL"))
    private static void registerPotions(CallbackInfo ci) {
        if (!Config.initialized) {
            Config.load();
        }

        if (!Config.getBool(ConfigValues.HASTE_POTIONS)) return;
        registerPotionRecipe(Potions.SWIFTNESS, Items.AMETHYST_SHARD, AdditionalPotions.HASTE_POTION);
        registerPotionRecipe(AdditionalPotions.HASTE_POTION, Items.REDSTONE, AdditionalPotions.LONG_HASTE_POTION);
        registerPotionRecipe(AdditionalPotions.HASTE_POTION, Items.GLOWSTONE_DUST, AdditionalPotions.STRONG_HASTE_POTION);
        registerPotionRecipe(Potions.STRONG_SWIFTNESS, Items.AMETHYST_SHARD, AdditionalPotions.STRONG_HASTE_POTION);
        registerPotionRecipe(Potions.LONG_WEAKNESS, Items.AMETHYST_SHARD, AdditionalPotions.LONG_GLOW_POTION);
    }
}
