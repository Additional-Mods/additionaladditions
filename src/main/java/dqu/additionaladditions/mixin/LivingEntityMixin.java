package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalEnchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow @Nullable public abstract AttributeInstance getAttribute(Attribute attribute);
    @Shadow @Final private static UUID SPEED_MODIFIER_SOUL_SPEED_UUID;
    @Inject(method = "tryAddSoulSpeed", at = @At("HEAD"))
    private void applySpeedBoost(CallbackInfo ci) {
        if (!Config.getBool(ConfigValues.ENCHANTMENT_SPEED)) {
            return;
        }

        int i = EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.ENCHANTMENT_SPEED, ((LivingEntity) (Object) this));
        if (i > 0) {
            if (getBlockStateOn().isAir()) {
                return;
            }

            AttributeInstance entityAttributeInstance = getAttribute(Attributes.MOVEMENT_SPEED);
            if (entityAttributeInstance == null) {
                return;
            }

            AttributeModifier modifier = new AttributeModifier(SPEED_MODIFIER_SOUL_SPEED_UUID, "Soul speed boost", (i*7d)/1000d, AttributeModifier.Operation.ADDITION);
            if (entityAttributeInstance.hasModifier(modifier)) {
                return;
            }

            entityAttributeInstance.addTransientModifier(modifier);
        }
    }
}
