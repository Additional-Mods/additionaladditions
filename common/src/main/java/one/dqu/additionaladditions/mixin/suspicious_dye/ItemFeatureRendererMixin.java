package one.dqu.additionaladditions.mixin.suspicious_dye;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Restores the glint color into GlintContext before each ItemSubmit is rendered.
 * ItemFeatureRenderer.getFoilRenderType then returns the colored glint render type.
 */
@Mixin(ItemFeatureRenderer.class)
public class ItemFeatureRendererMixin {
    @Inject(method = "renderItem", at = @At("HEAD"))
    private void restoreGlintColor(MultiBufferSource.BufferSource bufferSource, OutlineBufferSource outlineBufferSource, SubmitNodeStorage.ItemSubmit submit, CallbackInfo ci) {
        DyeColor color = GlintContext.getAndClearColorForQuads(submit.quads());
        GlintContext.setDyeColor(color);
    }

    @Inject(method = "renderItem", at = @At("RETURN"))
    private void clearGlintColor(MultiBufferSource.BufferSource bufferSource, OutlineBufferSource outlineBufferSource, SubmitNodeStorage.ItemSubmit submit, CallbackInfo ci) {
        GlintContext.setDyeColor(null);
    }
}
