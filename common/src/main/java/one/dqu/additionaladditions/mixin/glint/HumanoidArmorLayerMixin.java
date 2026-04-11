package one.dqu.additionaladditions.mixin.glint;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import one.dqu.additionaladditions.feature.glint.GlintContext;
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
    private void store(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack itemStack, EquipmentSlot equipmentSlot, int i, HumanoidRenderState humanoidRenderState, CallbackInfo ci) {
        GlintContext.setCurrentItem(itemStack);
    }
}
