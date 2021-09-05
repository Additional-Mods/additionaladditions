package dqu.additionaladditions.mixin;

import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "getHandRenderType", at = @At("RETURN"), cancellable = true)
    private static void isCharged(ClientPlayerEntity player, CallbackInfoReturnable<HeldItemRenderer.HandRenderType> cir) {
        ItemStack stack = player.getMainHandStack();
        if (stack.isOf(AdditionalItems.CROSSBOW_WITH_SPYGLASS) && CrossbowItem.isCharged(stack)) {
            cir.setReturnValue(HeldItemRenderer.HandRenderType.RENDER_MAIN_HAND_ONLY);
        }
    }
}
