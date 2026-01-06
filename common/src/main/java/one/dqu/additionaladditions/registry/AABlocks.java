package one.dqu.additionaladditions.registry;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.block.*;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.item.CopperPatinaItem;
import one.dqu.additionaladditions.util.CreativeAdder;
import one.dqu.additionaladditions.util.LootAdder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.*;
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
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class AABlocks {
    public static final Supplier<RopeBlock> ROPE_BLOCK = AARegistries.BLOCKS.register(
                    ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rope"),
                    () -> new RopeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.WOOL).explosionResistance(0f).destroyTime(0.2f))
            );

    public static final Supplier<TintedRedstoneLampBlock> TINTED_REDSTONE_LAMP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "tinted_redstone_lamp"),
            () -> new TintedRedstoneLampBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).pushReaction(PushReaction.NORMAL).sound(SoundType.GLASS).strength(0.3f))
    );

    public static final Supplier<CopperPatinaBlock> COPPER_PATINA = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "copper_patina"),
            () -> new CopperPatinaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.TUFF).instabreak())
    );

    public static final Supplier<GlowStickBlock> GLOW_STICK_BLOCK = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "glow_stick"),
            () -> new GlowStickBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).pushReaction(PushReaction.DESTROY).noCollission().lightLevel((state) -> 12).instabreak())
    );

    public static final Supplier<PatinaBlock> PATINA_BLOCK = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "patina_block"),
            () -> new PatinaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.NORMAL).sound(SoundType.ROOTED_DIRT).strength(0.5f))
    );

    public static final Supplier<Block> ROSE_GOLD_BLOCK = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rose_gold_block"),
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.METAL))
    );

    // sniffer plantws

    public static final Supplier<Block> COTTONSHIVER = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "cottonshiver"),
            () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> COTTONSHIVER_CROP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "cottonshiver_crop"),
            () -> new FlowerCropBlock(
                    COTTONSHIVER, 2, new VoxelShape[]{Block.box(4d, 0d, 4d, 12d, 8d, 12d), Block.box(4d, 0d, 4d, 12d, 15d, 12d), Shapes.block()},
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
            )
    );

    public static final Supplier<Block> MUDFLOWER = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "mudflower"),
            () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> MUDFLOWER_CROP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "mudflower_crop"),
            () -> new FlowerCropBlock(
                    MUDFLOWER, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 9d, 11d), Block.box(4d, 0d, 4d, 12d, 15d, 12d), Shapes.block()},
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
            )
    );

    public static final Supplier<Block> CRIMSON_BLOSSOM = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "crimson_blossom"),
            () -> new FlowerBlock(MobEffects.DAMAGE_BOOST, 5f, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> CRIMSON_BLOSSOM_CROP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "crimson_blossom_crop"),
            () -> new FlowerCropBlock(
                    CRIMSON_BLOSSOM, 2, new VoxelShape[]{Block.box(3d, 0d, 3d, 13d, 10d, 13d), Block.box(3d, 0d, 3d, 13d, 12d, 13d), Shapes.block()},
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
            )
    );

    public static final Supplier<Block> AMBER_BLOSSOM = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "amber_blossom"),
            () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> AMBER_BLOSSOM_CROP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "amber_blossom_crop"),
            () -> new FlowerCropBlock(
                    AMBER_BLOSSOM, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 7d, 11d), Block.box(4d, 0d, 4d, 12d, 12d, 12d), Shapes.block()},
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
            )
    );

    public static final Supplier<Block> BULBUS = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "bulbus"),
            () -> new FlowerBlock(MobEffects.NIGHT_VISION, 5f, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> BULBUS_CROP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "bulbus_crop"),
            () -> new FlowerCropBlock(
                    BULBUS, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 10d, 11d), Block.box(4d, 0d, 4d, 12d, 15d, 12d), Shapes.block()},
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
            )
    );

    public static final Supplier<Block> SAWTOOTH_FERN = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "sawtooth_fern"),
            () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> SAWTOOTH_FERN_CROP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "sawtooth_fern_crop"),
            () -> new FlowerCropBlock(
                    SAWTOOTH_FERN, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 10d, 11d), Block.box(4d, 0d, 4d, 12d, 15d, 12d), Shapes.block()},
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
            )
    );

    public static final Supplier<Block> FROSTLEAF = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "frostleaf"),
            () -> new FlowerBlock(MobEffects.MOVEMENT_SPEED, 5f, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> FROSTLEAF_CROP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "frostleaf_crop"),
            () -> new FlowerCropBlock(
                    FROSTLEAF, 2, new VoxelShape[]{Block.box(1d, 0d, 1d, 14d, 6d, 14d), Block.box(1d, 0d, 1d, 14d, 6d, 14d), Shapes.block()},
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
            )
    );

    public static final Supplier<Block> WISTERIA = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "wisteria"),
            () -> new FlowerBlock(MobEffects.JUMP, 5f, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> WISTERIA_CROP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "wisteria_crop"),
            () -> new FlowerCropBlock(
                    WISTERIA, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 6d, 11d), Block.box(4d, 0d, 4d, 12d, 10d, 12d), Shapes.block()},
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
            )
    );

    public static final Supplier<Block> SPIKEBLOSSOM = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "spikeblossom"),
            () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> SPIKEBLOSSOM_CROP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "spikeblossom_crop"),
            () -> new FlowerCropBlock(
                    SPIKEBLOSSOM, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 7d, 11d), Block.box(4d, 0d, 4d, 12d, 11d, 12d), Shapes.block()},
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
            )
    );

    public static final Supplier<Block> SNAPDRAGON = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "snapdragon"),
            () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> SNAPDRAGON_CROP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "snapdragon_crop"),
            () -> new FlowerCropBlock(
                    SNAPDRAGON, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 8d, 11d), Block.box(4d, 0d, 4d, 12d, 15d, 12d), Shapes.block()},
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
            )
    );

    public static final Supplier<Block> LOTUS_LILY = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "lotus_lily"),
            () -> new FlowerBlock(MobEffects.WATER_BREATHING, 5f, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> LOTUS_LILY_CROP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "lotus_lily_crop"),
            () -> new FlowerCropBlock(
                    LOTUS_LILY, 1, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 6d, 11d), Shapes.block()},
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
            )
    );

    // amethyst lamp poi

    private static final Supplier<Set<BlockState>> AMETHYST_LAMP_POI_STATES = Suppliers.memoize(() -> ImmutableSet.copyOf(AABlocks.TINTED_REDSTONE_LAMP.get().getStateDefinition().getPossibleStates().stream().filter(state -> state.getValue(RedstoneLampBlock.LIT)).toList()));
    public static ResourceKey<PoiType> AMETHYST_LAMP_POI_KEY = ResourceKey.create(
            Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "amethyst_lamp_poi")
    );
    public static Supplier<PoiType> AMETHYST_LAMP_POI = AARegistries.POI.register(
            AMETHYST_LAMP_POI_KEY.location(),
            () -> new PoiType(AMETHYST_LAMP_POI_STATES.get(), 0, 0)
    );

    public static void registerAll() {
        // amethyst lamp poi
        Registrar.defer(() -> {
            Holder<PoiType> poi = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolderOrThrow(AMETHYST_LAMP_POI_KEY);

            // collect unregistered states. on neoforge they seem to get registered automatically, but not on fabric?
            Set<BlockState> states = AMETHYST_LAMP_POI_STATES.get().stream()
                    .filter(state -> PoiTypes.forState(state).orElse(null) == null)
                    .collect(Collectors.toUnmodifiableSet());

            PoiTypes.registerBlockStates(poi, states);
        });
    }
}
