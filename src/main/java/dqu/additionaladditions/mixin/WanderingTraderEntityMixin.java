package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTraderEntity.class)
public class WanderingTraderEntityMixin {
    @Inject(method = "fillRecipes", at = @At("RETURN"))
    private void addWanderingTraderTrades(CallbackInfo ci) {
        TradeOfferList tradeOfferList = ((WanderingTraderEntity) (Object) this).getOffers();
        World world = ((WanderingTraderEntity) (Object) this).world;
        if (world.getRandom().nextDouble() < 0.5D && Config.get("MysteriousBundle")) {
            tradeOfferList.add(new TradeOffer(new ItemStack(Items.EMERALD, 6), new ItemStack(AdditionalItems.MYSTERIOUS_BUNDLE_ITEM, 1), 6, 6, 0.5f));
        }
    }
}
