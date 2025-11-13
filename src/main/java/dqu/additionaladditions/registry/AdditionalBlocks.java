package dqu.additionaladditions.registry;

import com.google.common.collect.ImmutableSet;
import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.block.CopperPatinaBlock;
import dqu.additionaladditions.block.GlowStickBlock;
import dqu.additionaladditions.block.PatinaBlock;
import dqu.additionaladditions.block.RopeBlock;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.item.CopperPatinaItem;
import dqu.additionaladditions.misc.CreativeAdder;
import dqu.additionaladditions.misc.LootHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;

public class AdditionalBlocks {
    public static final RopeBlock ROPE_BLOCK = new RopeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.WOOL).instabreak());
    public static final RedstoneLampBlock AMETHYST_LAMP = new RedstoneLampBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).pushReaction(PushReaction.NORMAL).sound(SoundType.GLASS).strength(0.3f));
    public static final CopperPatinaBlock COPPER_PATINA = new CopperPatinaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.TUFF).instabreak());
    public static final GlowStickBlock GLOW_STICK_BLOCK = new GlowStickBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).pushReaction(PushReaction.DESTROY).noCollission().lightLevel((state) -> 12).instabreak());
    public static final PatinaBlock PATINA_BLOCK = new PatinaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.NORMAL).sound(SoundType.ROOTED_DIRT).strength(0.5f));
    public static final Block ROSE_GOLD_BLOCK = new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.METAL));

    public static PoiType AMETHYST_LAMP_POI;
    public static ResourceLocation AMETHYST_LAMP_POI_RL = ResourceLocation.tryBuild(AdditionalAdditions.namespace, "amethyst_lamp_poi");

    public static void registerAll() {
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "glow_stick"), GLOW_STICK_BLOCK);

        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "copper_patina"), COPPER_PATINA);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "copper_patina"),
                new CopperPatinaItem(COPPER_PATINA, new Item.Properties()));

        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rope"), ROPE_BLOCK);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rope"),
                new BlockItem(ROPE_BLOCK, new Item.Properties()));

        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "amethyst_lamp"), AMETHYST_LAMP);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "amethyst_lamp"),
                new BlockItem(AMETHYST_LAMP, new Item.Properties()));

        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "patina_block"), PATINA_BLOCK);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "patina_block"),
                new BlockItem(PATINA_BLOCK, new Item.Properties()));

        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_block"), ROSE_GOLD_BLOCK);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_block"),
                new BlockItem(ROSE_GOLD_BLOCK, new Item.Properties()));

        AMETHYST_LAMP_POI = PointOfInterestHelper.register(
            AMETHYST_LAMP_POI_RL,
            0, 8,
            ImmutableSet.copyOf(
                    AdditionalBlocks.AMETHYST_LAMP.getStateDefinition().getPossibleStates().stream().filter(state -> state.getValue(BlockStateProperties.LIT)).toList()
        ));

        LootHandler.register(List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.STRONGHOLD_CORRIDOR), () -> Config.getBool(ConfigValues.ROPES), LootPool.lootPool()
                .setRolls(UniformGenerator.between(1, 4))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8)))
                .add(LootItem.lootTableItem(AdditionalBlocks.ROPE_BLOCK))
        );

        CreativeAdder.REDSTONE_BLOCKS.add(() -> Config.getBool(ConfigValues.COPPER_PATINA), Items.REDSTONE_BLOCK, COPPER_PATINA);
        CreativeAdder.REDSTONE_BLOCKS.add(() -> Config.getBool(ConfigValues.AMETHYST_LAMP, "enabled"), Items.REDSTONE_LAMP, AMETHYST_LAMP);
        CreativeAdder.FUNCTIONAL_BLOCKS.add(() -> Config.getBool(ConfigValues.AMETHYST_LAMP, "enabled"), Items.REDSTONE_LAMP, AMETHYST_LAMP);
        CreativeAdder.FUNCTIONAL_BLOCKS.add(() -> Config.getBool(ConfigValues.ROPES), Items.LADDER, ROPE_BLOCK);
        CreativeAdder.BUILDING_BLOCKS.add(() -> Config.getBool(ConfigValues.COPPER_PATINA), Items.REDSTONE_BLOCK, PATINA_BLOCK);
        CreativeAdder.BUILDING_BLOCKS.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.LIGHT_WEIGHTED_PRESSURE_PLATE, ROSE_GOLD_BLOCK);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.ROPES), Items.SPYGLASS, ROPE_BLOCK);
    }
}
