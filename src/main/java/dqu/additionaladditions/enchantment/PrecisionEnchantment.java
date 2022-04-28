package dqu.additionaladditions.enchantment;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PrecisionEnchantment extends Enchantment {
    public PrecisionEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.CROSSBOW, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isDiscoverable() {
        return Config.getBool(ConfigValues.ENCHANTMENT_PRECISION);
    }

    @Override
    public boolean isTradeable() {
        return Config.getBool(ConfigValues.ENCHANTMENT_PRECISION);
    }
}
