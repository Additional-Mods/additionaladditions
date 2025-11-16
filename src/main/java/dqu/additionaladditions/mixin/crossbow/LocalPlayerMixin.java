package dqu.additionaladditions.mixin.crossbow;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Updates AdditionalAdditions.zoom field depending on whether the player is sneaking with a crossbow with spyglass.
 */
@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(method = "isShiftKeyDown", at = @At("HEAD"))
    private void checkSneakZoom(CallbackInfoReturnable<Boolean> cir) {
        if (!Config.getBool(ConfigValues.CROSSBOWS)) return;
        Player player = (Player) (Object) this;
        if (!player.getCommandSenderWorld().isClientSide()) return;
        ItemStack stack = player.getMainHandItem();
        if (stack.is(AdditionalItems.CROSSBOW_WITH_SPYGLASS)) {
            boolean sneaking = player.isCrouching();
            if (sneaking && sneaking != AdditionalAdditions.zoom) {
                player.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
                AdditionalAdditions.zoom = sneaking;
            } else if (!sneaking && sneaking != AdditionalAdditions.zoom) {
                player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
                AdditionalAdditions.zoom = sneaking;
            }
        } else {
            AdditionalAdditions.zoom = false;
        }
    }
}
