package dqu.additionaladditions.mixin.behaviour;

import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.behaviour.BehaviourValues;
import dqu.additionaladditions.material.GildedNetheriteArmorMaterial;
import dqu.additionaladditions.material.GildedNetheriteToolMaterial;
import dqu.additionaladditions.material.RoseGoldArmorMaterial;
import dqu.additionaladditions.material.RoseGoldToolMaterial;
import net.minecraft.core.Registry;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DiggerItem.class)
public class DiggerItemMixin extends TieredItem {
    public DiggerItemMixin(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Inject(method = "getDestroySpeed", at = @At("RETURN"))
    private void modifyDestroySpeed(ItemStack itemStack, BlockState blockState, CallbackInfoReturnable<Float> cir) {
        if (cir.getReturnValue() == 1.0F) return;
        if (getTier() == GildedNetheriteToolMaterial.MATERIAL) {
            String path = GildedNetheriteArmorMaterial.NAME + "/" + Registry.ITEM.getKey(asItem()).getPath();
            Float destroySpeed = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.MINING_SPEED);
            if (destroySpeed != null) cir.setReturnValue(destroySpeed);
        } else if (getTier() == RoseGoldToolMaterial.MATERIAL) {
            String path = RoseGoldArmorMaterial.NAME + "/" + Registry.ITEM.getKey(asItem()).getPath();
            Float destroySpeed = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.MINING_SPEED);
            if (destroySpeed != null) cir.setReturnValue(destroySpeed);
        }
    }

    @Inject(method = "getAttackDamage", at = @At("RETURN"))
    private void modifyDamage(CallbackInfoReturnable<Float> cir) {
        if (cir.getReturnValue() == 1.0F) return;
        if (getTier() == GildedNetheriteToolMaterial.MATERIAL) {
            String path = GildedNetheriteArmorMaterial.NAME + "/" + Registry.ITEM.getKey(asItem()).getPath();
            Float damage = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.DAMAGE);
            if (damage != null) cir.setReturnValue(damage);
        } else if (getTier() == RoseGoldToolMaterial.MATERIAL) {
            String path = RoseGoldArmorMaterial.NAME + "/" + Registry.ITEM.getKey(asItem()).getPath();
            Float damage = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.DAMAGE);
            if (damage != null) cir.setReturnValue(damage);
        }
    }
}
