package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.Config;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AdditionalPotions {
    public static final Potion GLOW_POTION = new Potion(new StatusEffectInstance(StatusEffects.GLOWING, 3600));
    public static final Potion LONG_GLOW_POTION = new Potion(new StatusEffectInstance(StatusEffects.GLOWING, 9600));
    public static final Potion HASTE_POTION = new Potion(new StatusEffectInstance(StatusEffects.HASTE, 3400));
    public static final Potion LONG_HASTE_POTION = new Potion(new StatusEffectInstance(StatusEffects.HASTE, 6800));
    public static final Potion STRONG_HASTE_POTION = new Potion(new StatusEffectInstance(StatusEffects.HASTE, 1600, 1));

    public static void registerAll() {
        if (!Config.get("Potions")) return;
        Registry.register(Registry.POTION, new Identifier(AdditionalAdditions.namespace, "glow_potion"), GLOW_POTION);
        Registry.register(Registry.POTION, new Identifier(AdditionalAdditions.namespace, "long_glow_potion"), LONG_GLOW_POTION);
        Registry.register(Registry.POTION, new Identifier(AdditionalAdditions.namespace, "haste_potion"), HASTE_POTION);
        Registry.register(Registry.POTION, new Identifier(AdditionalAdditions.namespace, "long_haste_potion"), LONG_HASTE_POTION);
        Registry.register(Registry.POTION, new Identifier(AdditionalAdditions.namespace, "strong_haste_potion"), STRONG_HASTE_POTION);
    }
}
