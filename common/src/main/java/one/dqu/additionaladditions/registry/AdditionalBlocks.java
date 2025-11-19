package one.dqu.additionaladditions.registry;

import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.block.CopperPatinaBlock;
import one.dqu.additionaladditions.block.GlowStickBlock;
import one.dqu.additionaladditions.block.PatinaBlock;
import one.dqu.additionaladditions.block.RopeBlock;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.util.CreativeAdder;
import one.dqu.additionaladditions.util.LootHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import one.dqu.additionaladditions.util.Registrar;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class AdditionalBlocks {
    public static final Supplier<RopeBlock> ROPE_BLOCK = AdditionalRegistries.BLOCKS.register(
                    ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rope"),
                    () -> new RopeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.WOOL).instabreak())
            );
    public static final Supplier<Item> ROPE_BLOCK_ITEM = AdditionalRegistries.ITEMS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rope"),
            () -> new BlockItem(ROPE_BLOCK.get(), new Item.Properties())
    );

    public static final Supplier<RedstoneLampBlock> AMETHYST_LAMP = AdditionalRegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "amethyst_lamp"),
            () -> new RedstoneLampBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).pushReaction(PushReaction.NORMAL).sound(SoundType.GLASS).strength(0.3f))
    );
    public static final Supplier<Item> AMETHYST_BLOCK_ITEM = AdditionalRegistries.ITEMS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "amethyst_lamp"),
            () -> new BlockItem(AMETHYST_LAMP.get(), new Item.Properties())
    );

    public static final Supplier<CopperPatinaBlock> COPPER_PATINA = AdditionalRegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "copper_patina"),
            () -> new CopperPatinaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.TUFF).instabreak())
    );
    public static final Supplier<Item> COPPER_PATINA_ITEM = AdditionalRegistries.ITEMS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "copper_patina"),
            () -> new BlockItem(COPPER_PATINA.get(), new Item.Properties())
    );

    public static final Supplier<GlowStickBlock> GLOW_STICK_BLOCK = AdditionalRegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "glow_stick"),
            () -> new GlowStickBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).pushReaction(PushReaction.DESTROY).noCollission().lightLevel((state) -> 12).instabreak())
    );

    public static final Supplier<PatinaBlock> PATINA_BLOCK = AdditionalRegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "patina_block"),
            () -> new PatinaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.NORMAL).sound(SoundType.ROOTED_DIRT).strength(0.5f))
    );
    public static final Supplier<Item> PATINA_BLOCK_ITEM = AdditionalRegistries.ITEMS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "patina_block"),
            () -> new BlockItem(PATINA_BLOCK.get(), new Item.Properties())
    );

    public static final Supplier<Block> ROSE_GOLD_BLOCK = AdditionalRegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rose_gold_block"),
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.METAL))
    );
    public static final Supplier<Item> ROSE_GOLD_BLOCK_ITEM = AdditionalRegistries.ITEMS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rose_gold_block"),
            () -> new BlockItem(ROSE_GOLD_BLOCK.get(), new Item.Properties())
    );

    public static PoiType AMETHYST_LAMP_POI;
    public static ResourceLocation AMETHYST_LAMP_POI_RL = ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "amethyst_lamp_poi");

    public static void registerAll() {
        //TODO poi
//        AMETHYST_LAMP_POI = PointOfInterestHelper.register(
//            AMETHYST_LAMP_POI_RL,
//            0, 8,
//            ImmutableSet.copyOf(
//                    AdditionalBlocks.AMETHYST_LAMP.getStateDefinition().getPossibleStates().stream().filter(state -> state.getValue(BlockStateProperties.LIT)).toList()
//        ));

        Registrar.defer(() -> {
            LootHandler.register(List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.STRONGHOLD_CORRIDOR), () -> Config.ROPE.get().enabled(), LootPool.lootPool()
                .setRolls(UniformGenerator.between(1, 4))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8)))
                .add(LootItem.lootTableItem(AdditionalBlocks.ROPE_BLOCK.get()))
            );
        });

        CreativeAdder.add(CreativeModeTabs.REDSTONE_BLOCKS, () -> Config.COPPER_PATINA.get().enabled(), Items.REDSTONE_BLOCK, () -> COPPER_PATINA.get().asItem());
        CreativeAdder.add(CreativeModeTabs.REDSTONE_BLOCKS, () -> Config.AMETHYST_LAMP.get().enabled(), Items.REDSTONE_LAMP, () -> AMETHYST_LAMP.get().asItem());
        CreativeAdder.add(CreativeModeTabs.FUNCTIONAL_BLOCKS, () -> Config.AMETHYST_LAMP.get().enabled(), Items.REDSTONE_LAMP, () -> AMETHYST_LAMP.get().asItem());
        CreativeAdder.add(CreativeModeTabs.FUNCTIONAL_BLOCKS, () -> Config.ROPE.get().enabled(), Items.LADDER, () -> ROPE_BLOCK.get().asItem());
        CreativeAdder.add(CreativeModeTabs.BUILDING_BLOCKS, () -> Config.COPPER_PATINA.get().enabled(), Items.REDSTONE_BLOCK, () -> PATINA_BLOCK.get().asItem());
        CreativeAdder.add(CreativeModeTabs.BUILDING_BLOCKS, () -> Config.ROSE_GOLD.get().enabled(), Items.LIGHT_WEIGHTED_PRESSURE_PLATE, () -> ROSE_GOLD_BLOCK.get().asItem());
        CreativeAdder.add(CreativeModeTabs.TOOLS_AND_UTILITIES, () -> Config.ROPE.get().enabled(), Items.SPYGLASS, () -> ROPE_BLOCK.get().asItem());
    }
}
