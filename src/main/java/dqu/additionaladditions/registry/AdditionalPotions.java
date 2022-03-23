package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

public class AdditionalPotions {
    public static final Potion GLOW_POTION = new Potion(new MobEffectInstance(MobEffects.GLOWING, 3600));
    public static final Potion LONG_GLOW_POTION = new Potion(new MobEffectInstance(MobEffects.GLOWING, 9600));
    public static final Potion HASTE_POTION = new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 3400));
    public static final Potion LONG_HASTE_POTION = new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 6800));
    public static final Potion STRONG_HASTE_POTION = new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 1600, 1));

    public static void registerAll() {
        Registry.register(Registry.POTION, new ResourceLocation(AdditionalAdditions.namespace, "haste_potion"), HASTE_POTION);
        Registry.register(Registry.POTION, new ResourceLocation(AdditionalAdditions.namespace, "long_haste_potion"), LONG_HASTE_POTION);
        Registry.register(Registry.POTION, new ResourceLocation(AdditionalAdditions.namespace, "strong_haste_potion"), STRONG_HASTE_POTION);

        // pre 3.0.1 compatibility
        Registry.register(Registry.POTION, new ResourceLocation(AdditionalAdditions.namespace, "glow_potion"), GLOW_POTION);
        Registry.register(Registry.POTION, new ResourceLocation(AdditionalAdditions.namespace, "long_glow_potion"), LONG_GLOW_POTION);
    }
}
