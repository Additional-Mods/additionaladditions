package one.dqu.additionaladditions.mixin.glint;

import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.feature.glint.GlintColorHolder;
import one.dqu.additionaladditions.registry.AAMisc;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stores the glint color in the ItemStackRenderState
 */
@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {
    @Inject(method = "updateForTopItem", at = @At("RETURN"))
    private void store(ItemStackRenderState itemStackRenderState, ItemStack itemStack, ItemDisplayContext itemDisplayContext, @Nullable Level level, @Nullable ItemOwner itemOwner, int i, CallbackInfo ci) {
        DyeColor color = null;
        if (itemStack.has(AAMisc.GLINT_COLOR_COMPONENT.get())) {
            color = itemStack.get(AAMisc.GLINT_COLOR_COMPONENT.get()).color();
        }

        ((GlintColorHolder) itemStackRenderState).additionaladditions$setGlintColor(color);

        // include glint color in model identity so same items with different glints are not batched during gui rendering
        itemStackRenderState.appendModelIdentityElement(color);
    }
}
