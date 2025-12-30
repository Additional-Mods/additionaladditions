package one.dqu.additionaladditions.mixin.crossbow;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import one.dqu.additionaladditions.registry.AAItems;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Makes crossbow with spyglass render poses like vanilla crossbow.
 */
@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
    @WrapOperation(
            method = "isChargedCrossbow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private static boolean additionaladditions$isChargedCrossbow(ItemStack itemStack, Item item, Operation<Boolean> original) {
        return original.call(itemStack, item) || itemStack.is(AAItems.CROSSBOW_WITH_SPYGLASS.get());
    }

    @WrapOperation(
            method = "renderArmWithItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean additionaladditions$isChargedCrossbowInRenderArm(ItemStack itemStack, Item item, Operation<Boolean> original) {
        if (item != Items.CROSSBOW) {
            return original.call(itemStack, item);
        }
        return original.call(itemStack, item) || itemStack.is(AAItems.CROSSBOW_WITH_SPYGLASS.get());
    }
}
