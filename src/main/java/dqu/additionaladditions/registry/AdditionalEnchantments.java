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
        if (Config.get("EnchantmentPrecision"))
            Registry.register(Registry.ENCHANTMENT, new Identifier(AdditionalAdditions.namespace, "precision"), ENCHANTMENT_PRECISION);
        if (Config.get("EnchantmentSpeed"))
            Registry.register(Registry.ENCHANTMENT, new Identifier(AdditionalAdditions.namespace, "speed"), ENCHANTMENT_SPEED);
    }
}