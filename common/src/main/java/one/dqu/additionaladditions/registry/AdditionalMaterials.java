package one.dqu.additionaladditions.registry;

import one.dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AdditionalMaterials {
    public static final Holder<ArmorMaterial> ROSE_GOLD = register(
            "rose_gold",
            new EnumMap<>(Map.of(
                    ArmorItem.Type.BOOTS, 2,
                    ArmorItem.Type.LEGGINGS, 6,
                    ArmorItem.Type.CHESTPLATE, 7,
                    ArmorItem.Type.HELMET, 2
            )),
            17,
            0.0f,
            0.0f,
            SoundEvents.ARMOR_EQUIP_GOLD,
            () -> Ingredient.of(AdditionalItems.ROSE_GOLD_INGOT.get())
    );

    //TODO
    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> protection,
                                                  int enchantability, float toughness, float knockbackResistance,
                                                  Holder<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient) {
        return Registry.registerForHolder(
                BuiltInRegistries.ARMOR_MATERIAL,
                ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, name),
                new ArmorMaterial(
                        protection,
                        enchantability,
                        equipSound,
                        repairIngredient,
                        List.of(
                                new ArmorMaterial.Layer(ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, name))
                        ),
                        toughness,
                        knockbackResistance
                )
        );
    }
}
