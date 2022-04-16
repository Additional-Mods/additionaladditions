package dqu.additionaladditions.material;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class GildedNetheriteArmorMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = {481, 555, 592, 407};
    private static final int[] PROTECTION_VALUES = {3, 6, 8, 3};

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getIndex()];
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return PROTECTION_VALUES[slot.getIndex()];
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
        return "gilded_netherite";
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
        return 24;
    }

    @Override
    public String toString() {
        return "GILDED_NETHERITE";
    }
}
