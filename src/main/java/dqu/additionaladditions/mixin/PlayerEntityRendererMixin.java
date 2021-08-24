package dqu.additionaladditions.mixin;

import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Redirect(method = "getArmPose", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean setCrossbowArmPose(ItemStack stack, Item item) {
        return stack.isOf(item) || stack.isOf(AdditionalItems.CROSSBOW_WITH_SPYGLASS);
    }
}
