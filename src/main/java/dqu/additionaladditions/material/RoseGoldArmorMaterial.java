package dqu.additionaladditions.material;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Locale;

public class RoseGoldArmorMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = {264, 384, 360, 312};
    private static final int[] PROTECTION_VALUES = {2, 6, 7, 2};
    public static final String NAME = "rose_gold";

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
        return SoundEvents.ARMOR_EQUIP_GOLD;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.COPPER_INGOT);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public float getToughness() {
        return 1.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0F;
    }

    @Override
    public int getEnchantmentValue() {
        return 17;
    }

    @Override
    public String toString() {
        return NAME.toUpperCase(Locale.ROOT);
    }
}
