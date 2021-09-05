package dqu.additionaladditions.mixin;

import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "isChargedCrossbow", at = @At("HEAD"), cancellable = true)
    private static void isCharged(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(AdditionalItems.CROSSBOW_WITH_SPYGLASS) && CrossbowItem.isCharged(stack)) cir.setReturnValue(true);
    }
}
