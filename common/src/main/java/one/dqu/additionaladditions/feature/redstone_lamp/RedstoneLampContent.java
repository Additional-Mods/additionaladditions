package one.dqu.additionaladditions.feature.redstone_lamp;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.registry.AABlocks;

import java.util.Map;
import java.util.function.Supplier;

public class RedstoneLampContent {
    public static Supplier<TintedRedstoneLampBlock> tintedRedstoneLamp() {
        return AAReg.block(TintedRedstoneLampBlock::new)
                .props(p -> p
                        .mapColor(MapColor.TERRACOTTA_PURPLE)
                        .pushReaction(PushReaction.NORMAL)
                        .sound(SoundType.GLASS)
                        .strength(0.3f))
                .make("tinted_redstone_lamp");
    }

    public static Supplier<BlockItem> tintedRedstoneLampItem() {
        return AAReg.blockItem(AABlocks.TINTED_REDSTONE_LAMP)
                .config(Config.TINTED_REDSTONE_LAMP)
                .creative(Items.REDSTONE_LAMP, CreativeModeTabs.REDSTONE_BLOCKS, CreativePosition.AFTER)
                .creative(Items.REDSTONE_LAMP, CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativePosition.AFTER)
                .recipe(Recipes.shaped(" A ", "ALA", " A ", Map.of('A', Items.AMETHYST_SHARD, 'L', Items.REDSTONE_LAMP), RecipeCategory.REDSTONE).unlockedBy(Items.AMETHYST_SHARD))
                .make("tinted_redstone_lamp");
    }
}
