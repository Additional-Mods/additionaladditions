package dqu.additionaladditions.mixin.glint;

import dqu.additionaladditions.glint.GlintContext;
import dqu.additionaladditions.glint.GlintRenderType;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Swaps vanilla glint render type with custom ones if rendering an item with a dyed glint.
 */
@Mixin(RenderType.class)
public class RenderTypeMixin {
    @Inject(method = "glint", at = @At("HEAD"), cancellable = true)
    private static void getGlintInject(CallbackInfoReturnable<RenderType> cir) {
        RenderType renderType = GlintRenderType.getGlint(GlintContext.getDyeColor());
        if (renderType != null) {
            cir.setReturnValue(renderType);
            GlintContext.setCurrentItem(null);
        }
    }

    @Inject(method = "armorEntityGlint", at = @At("HEAD"), cancellable = true)
    private static void getArmorEntityGlintInject(CallbackInfoReturnable<RenderType> cir) {
        RenderType renderType = GlintRenderType.getArmorEntityGlint(GlintContext.getDyeColor());
        if (renderType != null) {
            cir.setReturnValue(renderType);
            GlintContext.setCurrentItem(null);
        }
    }

    @Inject(method = "entityGlintDirect", at = @At("HEAD"), cancellable = true)
    private static void getEntityGlintDirectInject(CallbackInfoReturnable<RenderType> cir) {
        RenderType renderType = GlintRenderType.getEntityGlintDirect(GlintContext.getDyeColor());
        if (renderType != null) {
            cir.setReturnValue(renderType);
            GlintContext.setCurrentItem(null);
        }
    }

    @Inject(method = "entityGlint", at = @At("HEAD"), cancellable = true)
    private static void getEntityGlintInject(CallbackInfoReturnable<RenderType> cir) {
        RenderType renderType = GlintRenderType.getEntityGlint(GlintContext.getDyeColor());
        if (renderType != null) {
            cir.setReturnValue(renderType);
            GlintContext.setCurrentItem(null);
        }
    }

    @Inject(method = "glintTranslucent", at = @At("HEAD"), cancellable = true)
    private static void getGlintTranslucentInject(CallbackInfoReturnable<RenderType> cir) {
        RenderType renderType = GlintRenderType.getGlintTranslucent(GlintContext.getDyeColor());
        if (renderType != null) {
            cir.setReturnValue(renderType);
            GlintContext.setCurrentItem(null);
        }
    }
}
