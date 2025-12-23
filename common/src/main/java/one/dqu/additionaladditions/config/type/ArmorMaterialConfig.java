package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import one.dqu.additionaladditions.config.type.unit.ItemUnitConfig;
import one.dqu.additionaladditions.config.type.unit.SoundEventUnitConfig;

import java.util.Arrays;
import java.util.function.Supplier;

public record ArmorMaterialConfig(
        float toughness,
        float knockbackResistance,
        int enchantability,
        Holder<SoundEvent> equipSound,
        Supplier<Ingredient> repairIngredient
) {
    public static final Codec<ArmorMaterialConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.floatRange(0.0f, Float.MAX_VALUE).fieldOf("toughness").forGetter(ArmorMaterialConfig::toughness),
                    Codec.floatRange(0.0f, Float.MAX_VALUE).fieldOf("knockback_resistance").forGetter(ArmorMaterialConfig::knockbackResistance),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("enchantability").forGetter(ArmorMaterialConfig::enchantability),
                    SoundEventUnitConfig.CODEC.fieldOf("equip_sound").forGetter(ArmorMaterialConfig::equipSound),
                    ItemUnitConfig.CODEC.xmap(
                            item -> (Supplier<Ingredient>) () -> Ingredient.of(item.get()),
                            supplier -> () -> Arrays.stream(supplier.get().getItems()).map(ItemStack::getItem).findFirst().orElse(null)
                    ).fieldOf("repair_ingredient").forGetter(ArmorMaterialConfig::repairIngredient)
            ).apply(instance, ArmorMaterialConfig::new)
    );
}
