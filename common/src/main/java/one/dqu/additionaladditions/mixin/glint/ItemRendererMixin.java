package one.dqu.additionaladditions.mixin.glint;

import one.dqu.additionaladditions.feature.glint.GlintColorHolder;
import one.dqu.additionaladditions.feature.glint.GlintContext;
import one.dqu.additionaladditions.registry.AAMisc;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stores the glint dye color in GlintContext and on the ItemStackRenderState.
 */
@Mixin(ItemModelResolver.class)
public class ItemRendererMixin {
    @Inject(method = "updateForTopItem", at = @At("HEAD"))
    private void store(ItemStackRenderState itemStackRenderState, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, @Nullable Level level, @Nullable LivingEntity livingEntity, int i, CallbackInfo ci) {
        DyeColor color = null;
        if (itemStack.has(AAMisc.GLINT_COLOR_COMPONENT.get())) {
            color = itemStack.get(AAMisc.GLINT_COLOR_COMPONENT.get()).color();
        }
        GlintContext.setDyeColor(color);
        ((GlintColorHolder) itemStackRenderState).additionaladditions$setGlintColor(color);
    }
}
