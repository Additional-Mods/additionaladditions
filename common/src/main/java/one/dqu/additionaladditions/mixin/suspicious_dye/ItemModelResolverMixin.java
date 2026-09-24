package one.dqu.additionaladditions.mixin.suspicious_dye;

import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintColorHolder;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {
    @Inject(method = "updateForTopItem", at = @At("RETURN"))
    private void storeGlintColor(ItemStackRenderState output, ItemStack item, ItemDisplayContext displayContext, @Nullable Level level, @Nullable ItemOwner owner, int seed, CallbackInfo ci) {
        DyeColor color = GlintContext.colorOf(item);
        ((GlintColorHolder) output).additionaladditions$setGlintColor(color);
        output.appendModelIdentityElement(color);
    }
}
