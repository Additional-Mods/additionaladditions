package one.dqu.additionaladditions.mixin.glint;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.feature.glint.GlintColorHolder;
import one.dqu.additionaladditions.feature.glint.GlintContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stores the glint dye color on the render state and restores it into GlintContext before rendering.
 */
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

    @Inject(method = "render", at = @At("HEAD"))
    private void restoreGlintContext(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, CallbackInfo ci) {
        GlintContext.setDyeColor(additionaladditions$glintColor);
    }
}
