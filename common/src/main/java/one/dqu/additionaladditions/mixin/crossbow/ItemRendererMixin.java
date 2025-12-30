package one.dqu.additionaladditions.mixin.crossbow;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.AdditionalAdditionsClient;
import one.dqu.additionaladditions.registry.AAItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Handles the hand predicate for the crossbow with spyglass item model.
 */
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    @Shadow
    public abstract void render(ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, BakedModel bakedModel);

    @Inject(
            method = "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void injectRenderStatic(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, Level level, int i, int j, int k, CallbackInfo ci) {
        if (itemStack.is(AAItems.CROSSBOW_WITH_SPYGLASS.get())) {
            BakedModel model = itemModelShaper.getItemModel(itemStack);
            ClientLevel clientLevel = level instanceof ClientLevel ? (ClientLevel)level : null;
            BakedModel bakedModel;

            AdditionalAdditionsClient.requestingInHandModel =
                    itemDisplayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                    || itemDisplayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                    || itemDisplayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                    || itemDisplayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;

            try {
                bakedModel = model.getOverrides().resolve(model, itemStack, clientLevel, livingEntity, i);
            } finally {
                AdditionalAdditionsClient.requestingInHandModel = false;
            }

            render(itemStack, itemDisplayContext, bl, poseStack, multiBufferSource, i, j, bakedModel);
            ci.cancel();
        }
    }
}
