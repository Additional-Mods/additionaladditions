package one.dqu.additionaladditions.mixin.crossbow;

import one.dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes isScoping return true when zooming with crossbow with spyglass.
 * This affects a few things e.g. spyglass overlay.
 */
@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "isScoping()Z", at = @At("RETURN"), cancellable = true)
    private void isScoping(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && AdditionalAdditions.zoom) {
            cir.setReturnValue(true);
        }
    }
}
