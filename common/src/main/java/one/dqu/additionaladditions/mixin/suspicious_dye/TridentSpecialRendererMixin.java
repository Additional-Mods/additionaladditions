package one.dqu.additionaladditions.mixin.suspicious_dye;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.object.projectile.TridentModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.special.TridentSpecialRenderer;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintContext;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TridentSpecialRenderer.class)
public class TridentSpecialRendererMixin {
    @WrapOperation(
            method = "submit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;III)V")
    )
    private void submitGlint(SubmitNodeCollector collector, Model<Object> model, Object state, PoseStack poseStack, RenderType renderType, int lightCoords, int overlayCoords, int outlineColor, Operation<Void> original) {
        DyeColor color = GlintContext.get();
        if (color == null) {
            original.call(collector, model, state, poseStack, renderType, lightCoords, overlayCoords, outlineColor);
            return;
        }
        original.call(collector, model, state, poseStack, model.renderType(TridentModel.TEXTURE), lightCoords, overlayCoords, outlineColor);
        collector.order(1).submitModel(model, state, poseStack, GlintRenderTypes.entityGlint(color), lightCoords, overlayCoords, 0);
    }
}
