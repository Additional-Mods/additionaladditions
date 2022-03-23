package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTrader.class)
public class WanderingTraderMixin {
    @Inject(method = "updateTrades", at = @At("RETURN"))
    private void addWanderingTraderTrades(CallbackInfo ci) {
        MerchantOffers tradeOfferList = ((WanderingTrader) (Object) this).getOffers();
        Level world = ((WanderingTrader) (Object) this).level;
        if (world.getRandom().nextDouble() < 0.5D && Config.getBool(ConfigValues.MYSTERIOUS_BUNDLE)) {
            tradeOfferList.add(new MerchantOffer(new ItemStack(Items.EMERALD, 6), new ItemStack(AdditionalItems.MYSTERIOUS_BUNDLE_ITEM, 1), 6, 6, 0.5f));
        }
    }
}
