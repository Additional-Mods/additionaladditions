package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.enchantment.PrecisionEnchantment;
import dqu.additionaladditions.enchantment.SpeedEnchantment;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class AdditionalEnchantments {
    public static final Enchantment ENCHANTMENT_PRECISION = new PrecisionEnchantment();
    public static final Enchantment ENCHANTMENT_SPEED = new SpeedEnchantment();

    public static void registerAll() {
        Registry.register(Registry.ENCHANTMENT, new ResourceLocation(AdditionalAdditions.namespace, "precision"), ENCHANTMENT_PRECISION);
        Registry.register(Registry.ENCHANTMENT, new ResourceLocation(AdditionalAdditions.namespace, "speed"), ENCHANTMENT_SPEED);
    }
}