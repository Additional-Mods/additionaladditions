package dqu.additionaladditions.material;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class GildedNetheriteToolMaterial implements Tier {
    public static final GildedNetheriteToolMaterial MATERIAL = new GildedNetheriteToolMaterial();

    @Override
    public int getUses() {
        return 2234;
    }

    @Override
    public float getSpeed() {
        return 10.0F;
    }

    @Override
    public float getAttackDamageBonus() {
        return 2.5F;
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public int getEnchantmentValue() {
        return 20;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }

    @Override
    public String toString() {
        return "GILDED_NETHERITE";
    }
}
