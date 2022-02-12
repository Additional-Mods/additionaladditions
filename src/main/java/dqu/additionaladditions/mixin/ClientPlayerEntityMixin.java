package dqu.additionaladditions.mixin;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.value.ConfigValues;
import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "isSneaking", at = @At("HEAD"))
    private void checkSneakZoom(CallbackInfoReturnable<Boolean> cir) {
        if (!Config.getBool(ConfigValues.CROSSBOWS)) return;
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!player.getEntityWorld().isClient()) return;
        ItemStack stack = player.getMainHandStack();
        if (stack.isOf(AdditionalItems.CROSSBOW_WITH_SPYGLASS)) {
            boolean sneaking = player.isInSneakingPose();
            if (sneaking && sneaking != AdditionalAdditions.zoom) {
                player.playSound(SoundEvents.ITEM_SPYGLASS_USE, 1.0F, 1.0F);
                AdditionalAdditions.zoom = sneaking;
            } else if (!sneaking && sneaking != AdditionalAdditions.zoom) {
                player.playSound(SoundEvents.ITEM_SPYGLASS_STOP_USING, 1.0F, 1.0F);
                AdditionalAdditions.zoom = sneaking;
            }
        } else {
            AdditionalAdditions.zoom = false;
        }
    }
}
