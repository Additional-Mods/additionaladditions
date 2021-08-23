package dqu.additionaladditions.enchantment;

import dqu.additionaladditions.Config;
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
        if (other == Enchantments.SOUL_SPEED && Config.get("EnchantmentSpeed")) return false;
        return super.canAccept(other);
    }
}
