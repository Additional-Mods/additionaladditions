package one.dqu.additionaladditions.mixin.glint;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import one.dqu.additionaladditions.feature.glint.GlintContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stores the elytra itemstack in GlintContext.
 */
@Mixin(WingsLayer.class)
public class WingsLayerMixin {
    @Inject(method = "submit", at = @At("HEAD"))
    private void store(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, HumanoidRenderState humanoidRenderState, float f, float g, CallbackInfo ci) {
        GlintContext.setCurrentItem(humanoidRenderState.chestEquipment);
    }
}
