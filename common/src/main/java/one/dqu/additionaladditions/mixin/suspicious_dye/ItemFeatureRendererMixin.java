package one.dqu.additionaladditions.mixin.suspicious_dye;

import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Restores the glint color into GlintContext before each item's foil is submitted.
 * getFoilBuffer then asks RenderTypes for the glint render type and RenderTypesMixin returns the colored variant.
 */
@Mixin(ItemFeatureRenderer.class)
public class ItemFeatureRendererMixin {
    @Inject(method = "prepareFoilSubmit", at = @At("HEAD"))
    private void restoreGlintColor(ItemFeatureRenderer.Submit submit, CallbackInfo ci) {
        DyeColor color = GlintContext.getAndClearColorForQuads(submit.quads());
        GlintContext.setDyeColor(color);
    }

    @Inject(method = "prepareFoilSubmit", at = @At("RETURN"))
    private void clearGlintColor(ItemFeatureRenderer.Submit submit, CallbackInfo ci) {
        GlintContext.setDyeColor(null);
    }
}
