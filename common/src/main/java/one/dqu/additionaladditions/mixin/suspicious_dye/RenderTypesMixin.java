package one.dqu.additionaladditions.mixin.suspicious_dye;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintContext;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Colors the glint of patterned shields while their item stack is being submitted.
 */
@Mixin(RenderTypes.class)
public class RenderTypesMixin {
    @Inject(method = "patternedShieldGlint", at = @At("HEAD"), cancellable = true)
    private static void colorPatternedShieldGlint(CallbackInfoReturnable<RenderType> cir) {
        DyeColor color = GlintContext.get();
        if (color != null) {
            cir.setReturnValue(GlintRenderTypes.entityGlint(color));
        }
    }
}
