package one.dqu.additionaladditions.mixin;

import one.dqu.additionaladditions.config.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "eatFood", at = @At("HEAD"))
    private void injectInEatFoodMethod(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof PlayerEntity && stack.isOf(Items.GLOW_BERRIES) && Config.PLAYER_GLOW.get().isEnabled()) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 5 * 20, 0, true, false));
        }
    }
}
