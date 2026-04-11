package one.dqu.additionaladditions.mixin.glint;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.feature.glint.GlintColorHolder;
import one.dqu.additionaladditions.feature.glint.GlintContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stores the glint color on the render state and restores it into GlintContext before submission.
 * Also associates the color with quad lists since ItemRenderer doesn't have access to the ItemStack.
 */
@Mixin(ItemStackRenderState.class)
public class ItemStackRenderStateMixin implements GlintColorHolder {
    @Shadow
    private ItemStackRenderState.LayerRenderState[] layers;

    @Shadow
    private int activeLayerCount;

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
    private void restoreContext(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int j, int k, CallbackInfo ci) {
        DyeColor color = this.additionaladditions$glintColor;
        GlintContext.setDyeColor(color);

        if (color != null) {
            for (int l = 0; l < this.activeLayerCount; l++) {
                // since ItemRenderer doesn't get the ItemStack, associate color with quad list so it can be retrieved in ItemRendererMixin
                GlintContext.setColorForQuads(this.layers[l].prepareQuadList(), color);
            }
        }
    }

    @Inject(method = "submit", at = @At("RETURN"))
    private void clearContext(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int j, int k, CallbackInfo ci) {
        GlintContext.setDyeColor(null);
    }
}
