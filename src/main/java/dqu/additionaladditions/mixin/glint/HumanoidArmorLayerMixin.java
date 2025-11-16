package dqu.additionaladditions.mixin.glint;

import com.mojang.blaze3d.vertex.PoseStack;
import dqu.additionaladditions.glint.GlintContext;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stores the armor itemstack in GlintContext.
 */
@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {
    @Inject(method = "renderArmorPiece", at = @At("HEAD"))
    private static void store(PoseStack poseStack, MultiBufferSource multiBufferSource, LivingEntity livingEntity, EquipmentSlot equipmentSlot, int i, HumanoidModel humanoidModel, CallbackInfo ci) {
        ItemStack itemStack = livingEntity.getItemBySlot(equipmentSlot);
        GlintContext.setCurrentItem(itemStack);
    }
}
