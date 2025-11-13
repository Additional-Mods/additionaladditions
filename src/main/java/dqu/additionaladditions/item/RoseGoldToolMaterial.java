package dqu.additionaladditions.item;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class RoseGoldToolMaterial implements Tier {
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
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return BlockTags.INCORRECT_FOR_IRON_TOOL;
    }

    @Override
    public int getEnchantmentValue() {
        return 17;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(AdditionalItems.ROSE_GOLD_INGOT);
    }
}
