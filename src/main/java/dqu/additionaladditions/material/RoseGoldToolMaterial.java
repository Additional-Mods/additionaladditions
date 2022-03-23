package dqu.additionaladditions.material;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class RoseGoldToolMaterial implements Tier {
    public static final RoseGoldToolMaterial MATERIAL = new RoseGoldToolMaterial();

    @Override
    public int getUses() {
        return 900;
    }

    @Override
    public float getSpeed() {
        return 9.0F;
    }

    @Override
    public float getAttackDamageBonus() {
        return 2.0F;
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getEnchantmentValue() {
        return 17;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.COPPER_INGOT);
    }

    @Override
    public String toString() {
        return "ROSE_GOLD";
    }
}
