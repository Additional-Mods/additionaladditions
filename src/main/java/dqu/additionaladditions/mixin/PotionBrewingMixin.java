package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.registry.AdditionalPotions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionBrewing.class)
public abstract class PotionBrewingMixin {
    @Shadow
    private static void addMix(Potion input, Item item, Potion output) {}

    @Inject(method = "bootStrap", at = @At("TAIL"))
    private static void registerPotions(CallbackInfo ci) {
        if (!Config.initialized) {
            Config.load();
        }

        addMix(Potions.SWIFTNESS, Items.AMETHYST_SHARD, AdditionalPotions.HASTE_POTION);
        addMix(AdditionalPotions.HASTE_POTION, Items.REDSTONE, AdditionalPotions.LONG_HASTE_POTION);
        addMix(AdditionalPotions.HASTE_POTION, Items.GLOWSTONE_DUST, AdditionalPotions.STRONG_HASTE_POTION);
        addMix(Potions.STRONG_SWIFTNESS, Items.AMETHYST_SHARD, AdditionalPotions.STRONG_HASTE_POTION);
        addMix(Potions.LONG_WEAKNESS, Items.AMETHYST_SHARD, AdditionalPotions.LONG_GLOW_POTION);
    }
}
