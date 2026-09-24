package one.dqu.additionaladditions.mixin.suspicious_dye;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.UvMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintContext;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintRenderTypes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Colors the glint of worn equipment (armor, elytra, animal armor).
 */
@Mixin(EquipmentLayerRenderer.class)
public class EquipmentLayerRendererMixin {
    private static final String RENDER_LAYERS = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V";

    @WrapOperation(
            method = RENDER_LAYERS,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;armorCutoutNoCullGlint(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;")
    )
    private RenderType removeInlineGlint(Identifier texture, Operation<RenderType> original, @Local(argsOnly = true) ItemStack itemStack) {
        return GlintContext.colorOf(itemStack) != null ? RenderTypes.armorCutoutNoCull(texture) : original.call(texture);
    }

    @WrapOperation(
            method = RENDER_LAYERS,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/UvMapping;I)V", ordinal = 0)
    )
    private void submitGlint(
            OrderedSubmitNodeCollector collector, Model<Object> model, Object state, PoseStack poseStack, RenderType renderType, int lightCoords, int overlayCoords, int tintedColor, @Nullable UvMapping uvMapping, int outlineColor,
            Operation<Void> original,
            @Local(argsOnly = true) ItemStack itemStack, @Local(argsOnly = true) SubmitNodeCollector submitNodeCollector,
            @Local(name = "renderShaderGlint") boolean renderShaderGlint, @Local(name = "nextOrder") LocalIntRef nextOrder
    ) {
        original.call(collector, model, state, poseStack, renderType, lightCoords, overlayCoords, tintedColor, uvMapping, outlineColor);
        DyeColor color = GlintContext.colorOf(itemStack);
        if (renderShaderGlint && color != null) {
            submitNodeCollector.order(nextOrder.get()).submitModel(model, state, poseStack, GlintRenderTypes.armorGlint(color), lightCoords, OverlayTexture.NO_OVERLAY, -1, null, 0);
            nextOrder.set(nextOrder.get() + 1);
        }
    }

    @WrapOperation(
            method = RENDER_LAYERS,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;trimmedArmorGlint()Lnet/minecraft/client/renderer/rendertype/RenderType;")
    )
    private RenderType colorTrimmedArmorGlint(Operation<RenderType> original, @Local(argsOnly = true) ItemStack itemStack) {
        DyeColor color = GlintContext.colorOf(itemStack);
        return color != null ? GlintRenderTypes.armorGlint(color) : original.call();
    }
}
