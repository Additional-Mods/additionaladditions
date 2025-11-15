package dqu.additionaladditions.mixin.glint;

import dqu.additionaladditions.glint.GlintContext;
import dqu.additionaladditions.glint.GlintRenderType;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
}
