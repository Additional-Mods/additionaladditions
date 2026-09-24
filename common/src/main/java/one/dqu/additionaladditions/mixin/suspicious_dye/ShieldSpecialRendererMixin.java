package one.dqu.additionaladditions.mixin.suspicious_dye;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.special.ShieldSpecialRenderer;
import net.minecraft.client.renderer.texture.UvMapping;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintContext;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintRenderTypes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShieldSpecialRenderer.class)
public class ShieldSpecialRendererMixin {
    @WrapOperation(
            method = "submit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/UvMapping;I)V")
    )
    private void submitGlint(
            SubmitNodeCollector collector, Model<Object> model, Object state, PoseStack poseStack, RenderType renderType, int lightCoords, int overlayCoords, int tintedColor, @Nullable UvMapping uvMapping, int outlineColor,
            Operation<Void> original, @Local(name = "base") SpriteId base
    ) {
        DyeColor color = GlintContext.get();
        if (color == null) {
            original.call(collector, model, state, poseStack, renderType, lightCoords, overlayCoords, tintedColor, uvMapping, outlineColor);
            return;
        }
        original.call(collector, model, state, poseStack, base.renderType(model.renderType()), lightCoords, overlayCoords, tintedColor, uvMapping, outlineColor);
        collector.order(1).submitModel(model, state, poseStack, GlintRenderTypes.entityGlint(color), lightCoords, overlayCoords, -1, uvMapping, 0);
    }
}
