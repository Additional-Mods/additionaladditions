package dqu.additionaladditions.mixin;

import dqu.additionaladditions.Config;
import dqu.additionaladditions.registry.AdditionalEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.world.World;
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
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow @Nullable public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);
    @Shadow @Final private static UUID SOUL_SPEED_BOOST_ID;

    @Inject(method = "addSoulSpeedBoostIfNeeded", at = @At("HEAD"))
    private void applySpeedBoost(CallbackInfo ci) {
        if (!Config.get("EnchantmentSpeed")) return;
        int i = EnchantmentHelper.getEquipmentLevel(AdditionalEnchantments.ENCHANTMENT_SPEED, ((LivingEntity) (Object) this));
        if (i > 0) {
            if (getLandingBlockState().isAir()) return;
            EntityAttributeInstance entityAttributeInstance = getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (entityAttributeInstance == null) return;
            EntityAttributeModifier modifier = new EntityAttributeModifier(SOUL_SPEED_BOOST_ID, "Soul speed boost", (i*7d)/1000d, EntityAttributeModifier.Operation.ADDITION);
            entityAttributeInstance.addTemporaryModifier(modifier);
        }
    }
}
