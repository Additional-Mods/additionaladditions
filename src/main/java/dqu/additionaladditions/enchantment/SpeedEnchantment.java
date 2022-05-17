package dqu.additionaladditions.enchantment;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class SpeedEnchantment extends Enchantment {
    public SpeedEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    protected boolean checkCompatibility(Enchantment other) {
        if (other == Enchantments.SOUL_SPEED || other == Enchantments.FROST_WALKER || other == Enchantments.DEPTH_STRIDER) {
            return false;
        }
        return super.checkCompatibility(other);
    }

    @Override
    public boolean isDiscoverable() {
        return Config.getBool(ConfigValues.ENCHANTMENT_SPEED);
    }

    @Override
    public boolean isTradeable() {
        return Config.getBool(ConfigValues.ENCHANTMENT_SPEED);
    }
}
