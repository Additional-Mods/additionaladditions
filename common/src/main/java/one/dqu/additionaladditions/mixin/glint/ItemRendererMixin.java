package one.dqu.additionaladditions.mixin.glint;

import com.mojang.blaze3d.vertex.PoseStack;
import one.dqu.additionaladditions.feature.glint.GlintContext;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stores the currently rendered itemstack in GlintContext.
 */
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(method = "renderStatic(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;IILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;I)V", at = @At("HEAD"))
    private void store(ItemStack itemStack, ItemDisplayContext itemDisplayContext, int i, int j, PoseStack poseStack, MultiBufferSource multiBufferSource, @Nullable Level level, int k, CallbackInfo ci) {
        GlintContext.setCurrentItem(itemStack);
    }
}
