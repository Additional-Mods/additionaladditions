package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.Config;
import dqu.additionaladditions.enchantment.PrecisionEnchantment;
import dqu.additionaladditions.enchantment.SpeedEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AdditionalEnchantments {
    public static final Enchantment ENCHANTMENT_PRECISION = new PrecisionEnchantment();
    public static final Enchantment ENCHANTMENT_SPEED = new SpeedEnchantment();

    public static void registerAll() {
        Registry.register(Registry.ENCHANTMENT, new Identifier(AdditionalAdditions.namespace, "precision"), ENCHANTMENT_PRECISION);
        Registry.register(Registry.ENCHANTMENT, new Identifier(AdditionalAdditions.namespace, "speed"), ENCHANTMENT_SPEED);
    }
}