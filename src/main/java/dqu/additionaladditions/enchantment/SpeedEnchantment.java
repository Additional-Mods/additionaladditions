package dqu.additionaladditions.enchantment;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class SpeedEnchantment extends Enchantment {
    public SpeedEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        if (Config.getBool(ConfigValues.ENCHANTMENT_SPEED)) return false;
        if (other == Enchantments.SOUL_SPEED || other == Enchantments.FROST_WALKER || other == Enchantments.DEPTH_STRIDER)
            return false;
        return super.canAccept(other);
    }
}
