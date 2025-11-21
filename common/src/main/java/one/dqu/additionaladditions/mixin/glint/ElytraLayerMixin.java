package one.dqu.additionaladditions.mixin.glint;

import com.mojang.blaze3d.vertex.PoseStack;
import one.dqu.additionaladditions.glint.GlintContext;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stores the elytra itemstack in GlintContext.
 */
@Mixin(ElytraLayer.class)
public class ElytraLayerMixin {
    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"))
    private void store(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        ItemStack stack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        GlintContext.setCurrentItem(stack);
    }
}
