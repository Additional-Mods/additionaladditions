package one.dqu.additionaladditions.mixin.glint;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import one.dqu.additionaladditions.feature.glint.GlintContext;
import one.dqu.additionaladditions.feature.glint.GlintRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Swaps vanilla glint render types with custom colored ones when a colored glint is active.
 */
@Mixin(RenderTypes.class)
public class RenderTypesMixin {
    @Inject(method = "glint", at = @At("HEAD"), cancellable = true)
    private static void getGlint(CallbackInfoReturnable<RenderType> cir) {
        RenderType renderType = GlintRenderTypes.getGlint(GlintContext.getDyeColor());
        if (renderType != null) {
            cir.setReturnValue(renderType);
            GlintContext.setDyeColor(null);
        }
    }

    @Inject(method = "armorEntityGlint", at = @At("HEAD"), cancellable = true)
    private static void getArmorEntityGlint(CallbackInfoReturnable<RenderType> cir) {
        RenderType renderType = GlintRenderTypes.getArmorEntityGlint(GlintContext.getDyeColor());
        if (renderType != null) {
            cir.setReturnValue(renderType);
            GlintContext.setDyeColor(null);
        }
    }

    @Inject(method = "entityGlint", at = @At("HEAD"), cancellable = true)
    private static void getEntityGlint(CallbackInfoReturnable<RenderType> cir) {
        RenderType renderType = GlintRenderTypes.getEntityGlint(GlintContext.getDyeColor());
        if (renderType != null) {
            cir.setReturnValue(renderType);
            GlintContext.setDyeColor(null);
        }
    }

    @Inject(method = "glintTranslucent", at = @At("HEAD"), cancellable = true)
    private static void getGlintTranslucent(CallbackInfoReturnable<RenderType> cir) {
        RenderType renderType = GlintRenderTypes.getGlintTranslucent(GlintContext.getDyeColor());
        if (renderType != null) {
            cir.setReturnValue(renderType);
            GlintContext.setDyeColor(null);
        }
    }
}
