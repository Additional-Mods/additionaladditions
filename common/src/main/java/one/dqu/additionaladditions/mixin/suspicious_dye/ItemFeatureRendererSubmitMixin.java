package one.dqu.additionaladditions.mixin.suspicious_dye;

import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintColorHolder;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Captures the glint color of the item being submitted, since the quads are rendered later.
 */
@Mixin(ItemFeatureRenderer.Submit.class)
public class ItemFeatureRendererSubmitMixin implements GlintColorHolder {
    @Unique
    private @Nullable DyeColor additionaladditions$glintColor;

    @Override
    public @Nullable DyeColor additionaladditions$getGlintColor() {
        return additionaladditions$glintColor;
    }

    @Override
    public void additionaladditions$setGlintColor(@Nullable DyeColor color) {
        this.additionaladditions$glintColor = color;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void captureGlintColor(CallbackInfo ci) {
        this.additionaladditions$glintColor = GlintContext.get();
    }
}
