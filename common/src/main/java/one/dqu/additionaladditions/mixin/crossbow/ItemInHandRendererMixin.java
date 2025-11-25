package one.dqu.additionaladditions.mixin.crossbow;

import one.dqu.additionaladditions.registry.AAItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes offhand not render when holding charged crossbow with spyglass.
 */
@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
    @Inject(method = "evaluateWhichHandsToRender", at = @At("RETURN"), cancellable = true)
    private static void isCharged(LocalPlayer player, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        ItemStack stack = player.getMainHandItem();
        if (stack.is(AAItems.CROSSBOW_WITH_SPYGLASS.get()) && CrossbowItem.isCharged(stack)) {
            cir.setReturnValue(ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY);
        }
    }
}
