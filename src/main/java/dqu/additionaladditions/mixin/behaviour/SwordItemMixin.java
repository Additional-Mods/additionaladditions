package dqu.additionaladditions.mixin.behaviour;

import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.behaviour.BehaviourValues;
import dqu.additionaladditions.material.GildedNetheriteArmorMaterial;
import dqu.additionaladditions.material.GildedNetheriteToolMaterial;
import dqu.additionaladditions.material.RoseGoldArmorMaterial;
import dqu.additionaladditions.material.RoseGoldToolMaterial;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
public class SwordItemMixin extends TieredItem {
    public SwordItemMixin(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Inject(method = "getDamage", at = @At("RETURN"), cancellable = true)
    private void modifyDamage(CallbackInfoReturnable<Float> cir) {
        if (getTier() == GildedNetheriteToolMaterial.MATERIAL) {
            String path = GildedNetheriteArmorMaterial.NAME + "/sword";
            Float damage = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.DAMAGE);
            if (damage != null) cir.setReturnValue(damage);
        } else if (getTier() == RoseGoldToolMaterial.MATERIAL) {
            String path = RoseGoldArmorMaterial.NAME + "/sword";
            Float damage = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.DAMAGE);
            if (damage != null) cir.setReturnValue(damage);
        }
    }
}
