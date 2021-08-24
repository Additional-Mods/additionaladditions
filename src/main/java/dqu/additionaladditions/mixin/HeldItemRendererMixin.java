package dqu.additionaladditions.mixin;

import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Redirect(method = "isChargedCrossbow", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean isCharged(ItemStack stack, Item item) {
        return stack.isOf(item) || stack.isOf(AdditionalItems.CROSSBOW_WITH_SPYGLASS);
    }
}
