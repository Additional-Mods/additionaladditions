package dqu.additionaladditions.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import dqu.additionaladditions.render.GlintColorRenderer;
import dqu.additionaladditions.render.GlintRenderType;
import dqu.additionaladditions.render.GlintVertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void store(ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, BakedModel bakedModel, CallbackInfo ci) {
        GlintColorRenderer.setCurrentItem(itemStack);
    }

    @Inject(method = "getFoilBufferDirect", at = @At("RETURN"), cancellable = true)
    private static void getFoilBufferDirect(MultiBufferSource multiBufferSource, RenderType renderType, boolean bl, boolean bl2, CallbackInfoReturnable<VertexConsumer> cir) {
        if (bl2) {
            VertexConsumer glint = multiBufferSource.getBuffer(bl ? GlintRenderType.GLINT_RENDER_LAYER : RenderType.entityGlintDirect());
            VertexConsumer item = multiBufferSource.getBuffer(renderType);
            VertexConsumer multi = VertexMultiConsumer.create(
                    new GlintVertexConsumer(glint, GlintColorRenderer.getGlintColor()),
                    item
            );
            cir.setReturnValue(multi);
        }
        GlintColorRenderer.setCurrentItem(null);
    }
}
