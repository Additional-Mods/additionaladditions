package one.dqu.additionaladditions.mixin.suspicious_dye;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.feature.RenderTypeFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintColorHolder;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintRenderTypes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFeatureRenderer.class)
public abstract class ItemFeatureRendererMixin extends RenderTypeFeatureRenderer<ItemFeatureRenderer.Submit> {
    @WrapOperation(
            method = "prepareMainSubmit",
            at = {
                    @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/geometry/BakedQuad$MaterialInfo;itemGlintRenderType()Lnet/minecraft/client/renderer/rendertype/RenderType;"),
                    @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/geometry/BakedQuad$MaterialInfo;itemGlintSpecialRenderType()Lnet/minecraft/client/renderer/rendertype/RenderType;")
            }
    )
    private RenderType colorGlint(BakedQuad.MaterialInfo material, Operation<RenderType> original, @Local(argsOnly = true) ItemFeatureRenderer.Submit submit) {
        RenderType renderType = original.call(material);
        DyeColor color = ((GlintColorHolder) (Object) submit).additionaladditions$getGlintColor();
        if (color == null) {
            return renderType;
        }
        return GlintRenderTypes.itemOverlay(renderType, color) != null ? material.itemRenderType() : GlintRenderTypes.item(renderType, color);
    }

    @Inject(method = "prepareMainSubmit", at = @At("TAIL"))
    private void submitGlintOverlay(ItemFeatureRenderer.Submit submit, CallbackInfo ci, @Local(name = "foilDecalPose") PoseStack.@Nullable Pose foilDecalPose) {
        DyeColor color = ((GlintColorHolder) (Object) submit).additionaladditions$getGlintColor();
        if (color == null || submit.foilType() == ItemStackRenderState.FoilType.NONE) {
            return;
        }
        boolean special = submit.foilType() == ItemStackRenderState.FoilType.SPECIAL;
        for (BakedQuad quad : submit.quads()) {
            BakedQuad.MaterialInfo material = quad.materialInfo();
            RenderType overlay = GlintRenderTypes.itemOverlay(special ? material.itemGlintSpecialRenderType() : material.itemGlintRenderType(), color);
            if (overlay != null) {
                GlintRenderTypes.putItemOverlayQuad(this.getVertexBuilder(overlay), submit.pose(), quad, foilDecalPose);
            }
        }
    }
}
