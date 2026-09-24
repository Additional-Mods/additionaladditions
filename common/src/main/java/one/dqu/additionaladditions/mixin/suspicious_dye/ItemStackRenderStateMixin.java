package one.dqu.additionaladditions.mixin.suspicious_dye;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintColorHolder;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackRenderState.class)
public class ItemStackRenderStateMixin implements GlintColorHolder {
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

    @Inject(method = "clear", at = @At("HEAD"))
    private void clearGlintColor(CallbackInfo ci) {
        this.additionaladditions$glintColor = null;
    }

    @Inject(method = "submit", at = @At("HEAD"))
    private void setContext(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor, CallbackInfo ci) {
        GlintContext.set(this.additionaladditions$glintColor);
    }

    @Inject(method = "submit", at = @At("RETURN"))
    private void clearContext(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor, CallbackInfo ci) {
        GlintContext.set(null);
    }
}
