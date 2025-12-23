package one.dqu.additionaladditions.item;

import one.dqu.additionaladditions.config.Config;
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
        return 0.0F;
    }

    @Override
    public float getAttackDamageBonus() {
        return -1.0F;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return BlockTags.INCORRECT_FOR_IRON_TOOL;
    }

    @Override
    public int getEnchantmentValue() {
        return Config.ROSE_GOLD_ARMOR_MATERIAL.get().enchantability();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Config.ROSE_GOLD_ARMOR_MATERIAL.get().repairIngredient().get();
    }
}
