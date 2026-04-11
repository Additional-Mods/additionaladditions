package one.dqu.additionaladditions.mixin.glint;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import one.dqu.additionaladditions.feature.glint.GlintContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Restores the glint color into GlintContext before the RenderType is retrieved.
 */
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(method = "renderItem", at = @At("HEAD"))
    private static void restoreGlintColor(ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, int[] is, List<BakedQuad> list, RenderType renderType, ItemStackRenderState.FoilType foilType, CallbackInfo ci) {
        DyeColor color = GlintContext.getAndClearColorForQuads(list);
        GlintContext.setDyeColor(color);
    }
}
