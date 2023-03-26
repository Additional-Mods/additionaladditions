package dqu.additionaladditions.material;

import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.behaviour.BehaviourValues;
import dqu.additionaladditions.config.value.ListConfigValue;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Locale;

public class GildedNetheriteArmorMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = {481, 555, 592, 407};
    private static final int[] PROTECTION_VALUES = {3, 6, 8, 3};
    public static final String NAME = "gilded_netherite";

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.getSlot().getIndex()];
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return PROTECTION_VALUES[type.getSlot().getIndex()];
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public float getToughness() {
        return 2.5F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.1F;
    }

    @Override
    public int getEnchantmentValue() {
        return 20;
    }
    

    @Override
    public String toString() {
        return NAME.toUpperCase(Locale.ROOT);
    }
}
