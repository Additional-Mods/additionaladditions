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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import one.dqu.additionaladditions.util.Registrar;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class AABlocks {
    public static final Supplier<RopeBlock> ROPE_BLOCK = register(
            "rope", RopeBlock::new,
            (p) -> p.mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.WOOL).explosionResistance(0f).destroyTime(0.2f)
    );

    public static final Supplier<TintedRedstoneLampBlock> TINTED_REDSTONE_LAMP = register(
            "tinted_redstone_lamp", TintedRedstoneLampBlock::new,
            (p) -> p.mapColor(MapColor.TERRACOTTA_PURPLE).pushReaction(PushReaction.NORMAL).sound(SoundType.GLASS).strength(0.3f)
    );

    public static final Supplier<CopperPatinaBlock> COPPER_PATINA = register(
            "copper_patina", CopperPatinaBlock::new,
            (p) -> p.mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.TUFF).instabreak()
    );

    public static final Supplier<GlowStickBlock> GLOW_STICK_BLOCK = register(
            "glow_stick", GlowStickBlock::new,
            (p) -> p.mapColor(MapColor.NONE).pushReaction(PushReaction.DESTROY).noCollission().lightLevel((state) -> 12).instabreak()
    );

    public static final Supplier<PatinaBlock> PATINA_BLOCK = register(
            "patina_block", PatinaBlock::new,
            (p) -> p.mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.NORMAL).sound(SoundType.ROOTED_DIRT).strength(0.5f)
    );

    public static final Supplier<Block> ROSE_GOLD_BLOCK = register(
            "rose_gold_block", Block::new,
            (p) -> p.mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.METAL)
    );

    // sniffer plantws

    public static final Supplier<Block> COTTONSHIVER = register(
            "cottonshiver", DoublePlantBlock::new,
            (p) -> p.mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> COTTONSHIVER_CROP = register(
            "cottonshiver_crop",
            (p) -> new FlowerCropBlock(COTTONSHIVER, 2, new VoxelShape[]{Block.box(4d, 0d, 4d, 12d, 8d, 12d), Block.box(4d, 0d, 4d, 12d, 15d, 12d), Shapes.block()}, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> MUDFLOWER = register(
            "mudflower", DoublePlantBlock::new,
            (p) -> p.mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> MUDFLOWER_CROP = register(
            "mudflower_crop",
            (p) -> new FlowerCropBlock(MUDFLOWER, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 9d, 11d), Block.box(4d, 0d, 4d, 12d, 15d, 12d), Shapes.block()}, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> CRIMSON_BLOSSOM = register(
            "crimson_blossom",
            (p) -> new FlowerBlock(MobEffects.DAMAGE_BOOST, 5f, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> CRIMSON_BLOSSOM_CROP = register(
            "crimson_blossom_crop",
            (p) -> new FlowerCropBlock(CRIMSON_BLOSSOM, 2, new VoxelShape[]{Block.box(3d, 0d, 3d, 13d, 10d, 13d), Block.box(3d, 0d, 3d, 13d, 12d, 13d), Shapes.block()}, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> AMBER_BLOSSOM = register(
            "amber_blossom", DoublePlantBlock::new,
            (p) -> p.mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> AMBER_BLOSSOM_CROP = register(
            "amber_blossom_crop",
            (p) -> new FlowerCropBlock(AMBER_BLOSSOM, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 7d, 11d), Block.box(4d, 0d, 4d, 12d, 12d, 12d), Shapes.block()}, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> BULBUS = register(
            "bulbus",
            (p) -> new FlowerBlock(MobEffects.NIGHT_VISION, 5f, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> BULBUS_CROP = register(
            "bulbus_crop",
            (p) -> new FlowerCropBlock(BULBUS, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 10d, 11d), Block.box(4d, 0d, 4d, 12d, 15d, 12d), Shapes.block()}, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> SAWTOOTH_FERN = register(
            "sawtooth_fern", DoublePlantBlock::new,
            (p) -> p.mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> SAWTOOTH_FERN_CROP = register(
            "sawtooth_fern_crop",
            (p) -> new FlowerCropBlock(SAWTOOTH_FERN, 3, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 10d, 11d), Block.box(5d, 0d, 5d, 11d, 10d, 11d), Block.box(4d, 0d, 4d, 12d, 15d, 12d), Shapes.block()}, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> FROSTLEAF = register(
            "frostleaf",
            (p) -> new FlowerBlock(MobEffects.MOVEMENT_SPEED, 5f, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> FROSTLEAF_CROP = register(
            "frostleaf_crop",
            (p) -> new FlowerCropBlock(FROSTLEAF, 2, new VoxelShape[]{Block.box(1d, 0d, 1d, 14d, 6d, 14d), Block.box(1d, 0d, 1d, 14d, 6d, 14d), Shapes.block()}, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> WISTERIA = register(
            "wisteria", WisteriaBlock::new,
            (p) -> p.mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.VINE).strength(0.2F).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> WISTERIA_CROP = register(
            "wisteria_crop", WisteriaCropBlock::new,
            (p) -> p.mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.VINE).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> SPIKEBLOSSOM = register(
            "spikeblossom", DoublePlantBlock::new,
            (p) -> p.mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> SPIKEBLOSSOM_CROP = register(
            "spikeblossom_crop",
            (p) -> new FlowerCropBlock(SPIKEBLOSSOM, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 7d, 11d), Block.box(4d, 0d, 4d, 12d, 11d, 12d), Shapes.block()}, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> SNAPDRAGON = register(
            "snapdragon", DoublePlantBlock::new,
            (p) -> p.mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> SNAPDRAGON_CROP = register(
            "snapdragon_crop",
            (p) -> new FlowerCropBlock(SNAPDRAGON, 2, new VoxelShape[]{Block.box(5d, 0d, 5d, 11d, 8d, 11d), Block.box(4d, 0d, 4d, 12d, 15d, 12d), Shapes.block()}, p),
            (p) -> p.mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> LOTUS_LILY = register(
            "lotus_lily", WaterlilyBlock::new,
            (p) -> p.mapColor(MapColor.PLANT).instabreak().sound(SoundType.LILY_PAD).noOcclusion().pushReaction(PushReaction.DESTROY)
    );

    public static final Supplier<Block> LOTUS_LILY_CROP = register(
            "lotus_lily_crop", LotusLilyCropBlock::new,
            (p) -> p.mapColor(MapColor.PLANT).randomTicks().instabreak().sound(SoundType.LILY_PAD).noOcclusion().pushReaction(PushReaction.DESTROY)
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

    // register helper method

    private static <T extends Block> Supplier<T> register(String id, Function<BlockBehaviour.Properties, T> blockFactory, Consumer<BlockBehaviour.Properties> props) {
        ResourceLocation location = ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, id);
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of();
        properties.setId(ResourceKey.create(Registries.BLOCK, location));
        props.accept(properties);
        return AARegistries.BLOCKS.register(location, () -> blockFactory.apply(properties));
    }

    public static void registerAll() {
        // amethyst lamp poi
        Registrar.defer(() -> {
            Holder<PoiType> poi = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getOrThrow(AMETHYST_LAMP_POI_KEY);

            // collect unregistered states. on neoforge they seem to get registered automatically, but not on fabric?
            Set<BlockState> states = AMETHYST_LAMP_POI_STATES.get().stream()
                    .filter(state -> PoiTypes.forState(state).orElse(null) == null)
                    .collect(Collectors.toUnmodifiableSet());

            PoiTypes.registerBlockStates(poi, states);
        });
    }
}
