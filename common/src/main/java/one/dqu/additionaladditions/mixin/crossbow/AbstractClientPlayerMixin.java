package one.dqu.additionaladditions.mixin.crossbow;

import one.dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.SpyglassItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Modifies player FOV when using crossbow with spyglass.
 */
@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {
    @Inject(method = "getFieldOfViewModifier()F", at = @At("RETURN"), cancellable = true)
    private static void getFieldOfViewModifier(CallbackInfoReturnable<Float> cir) {
        if (AdditionalAdditions.zoom) {
            cir.setReturnValue(SpyglassItem.ZOOM_FOV_MODIFIER);
        }
    }
}
