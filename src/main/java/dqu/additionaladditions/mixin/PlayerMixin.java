package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//Check FoxGlow out, if you want.
@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "eat", at = @At("HEAD"))
    protected void injectInEatFoodMethod(Level world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if(stack.getItem().equals(Items.GLOW_BERRIES) && Config.getBool(ConfigValues.GLOW_BERRY_GLOW, "enabled")) {
            Integer duration = Config.get(ConfigValues.GLOW_BERRY_GLOW, "duration");
            if (duration == null) return;
            this.addEffect(new MobEffectInstance(MobEffects.GLOWING, duration*20, 0, true, false));
        }
    }
}

