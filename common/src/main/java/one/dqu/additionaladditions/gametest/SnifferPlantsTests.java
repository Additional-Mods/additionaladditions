package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import one.dqu.additionaladditions.feature.sniffer_plants.FlowerCropBlock;
import one.dqu.additionaladditions.feature.sniffer_plants.LotusLilyCropBlock;
import one.dqu.additionaladditions.feature.sniffer_plants.WisteriaCropBlock;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAItems;

import java.util.List;
import java.util.function.Supplier;

public class SnifferPlantsTests {
    private record CropDef(Supplier<Block> crop, Supplier<Block> grown, int maxAge, BlockPos pos, Supplier<Item> seed) {
    }

    private static final List<CropDef> FLOWER_CROPS = List.of(
            new CropDef(AABlocks.COTTONSHIVER_CROP, AABlocks.COTTONSHIVER, 2, new BlockPos(1, 2, 1), AAItems.COTTONSHIVER_POD),
            new CropDef(AABlocks.MUDFLOWER_CROP, AABlocks.MUDFLOWER, 2, new BlockPos(2, 2, 1), AAItems.MUDFLOWER_SEEDS),
            new CropDef(AABlocks.CRIMSON_BLOSSOM_CROP, AABlocks.CRIMSON_BLOSSOM, 2, new BlockPos(3, 2, 1), AAItems.CRIMSON_BLOSSOM_SEEDS),
            new CropDef(AABlocks.AMBER_BLOSSOM_CROP, AABlocks.AMBER_BLOSSOM, 2, new BlockPos(1, 2, 2), AAItems.AMBER_BLOSSOM_SEEDS),
            new CropDef(AABlocks.BULBUS_CROP, AABlocks.BULBUS, 2, new BlockPos(2, 2, 2), AAItems.BULBUS_POD),
            new CropDef(AABlocks.SAWTOOTH_FERN_CROP, AABlocks.SAWTOOTH_FERN, 3, new BlockPos(3, 2, 2), AAItems.SAWTOOTH_FERN_FIDDLEHEAD),
            new CropDef(AABlocks.FROSTLEAF_CROP, AABlocks.FROSTLEAF, 2, new BlockPos(1, 2, 3), AAItems.FROSTLEAF_POD),
            new CropDef(AABlocks.SPIKEBLOSSOM_CROP, AABlocks.SPIKEBLOSSOM, 2, new BlockPos(2, 2, 3), AAItems.SPIKEBLOSSOM_SEEDS),
            new CropDef(AABlocks.SNAPDRAGON_CROP, AABlocks.SNAPDRAGON, 2, new BlockPos(3, 2, 3), AAItems.SNAPDRAGON_POD)
    );
    private static final BlockPos WISTERIA_POS = new BlockPos(6, 2, 1);
    private static final BlockPos LOTUS_POS = new BlockPos(8, 2, 2);

    private static void bonemeal(GameTestHelper ctx, BlockPos relPos) {
        ServerLevel level = ctx.getLevel();
        BlockPos abs = ctx.absolutePos(relPos);
        BlockState state = level.getBlockState(abs);
        ((BonemealableBlock) state.getBlock()).performBonemeal(level, level.getRandom(), abs, state);
    }

    private static void assertGrown(GameTestHelper ctx, CropDef def) {
        Block grown = def.grown().get();
        ctx.assertBlockPresent(grown, def.pos());
        if (grown instanceof DoublePlantBlock) {
            ctx.assertBlockProperty(def.pos(), DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
            ctx.assertBlockPresent(grown, def.pos().above());
            ctx.assertBlockProperty(def.pos().above(), DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
        }
    }

    private static void buildField(GameTestHelper ctx) {
        for (int x = 0; x <= 9; x++) {
            for (int z = 0; z <= 4; z++) {
                ctx.setBlock(new BlockPos(x, 0, z), Blocks.DIRT);
            }
        }

        BlockState moistFarmland = Blocks.FARMLAND.defaultBlockState().setValue(BlockStateProperties.MOISTURE, FarmlandBlock.MAX_MOISTURE);
        for (CropDef def : FLOWER_CROPS) {
            ctx.setBlock(def.pos().below(), moistFarmland);
        }

        ctx.setBlock(new BlockPos(4, 1, 2), Blocks.WATER);
        ctx.setBlock(new BlockPos(5, 1, 2), Blocks.STONE);
        ctx.setBlock(new BlockPos(4, 1, 1), Blocks.STONE);
        ctx.setBlock(new BlockPos(4, 1, 3), Blocks.STONE);

        ctx.setBlock(new BlockPos(6, 1, 2), Blocks.STONE);
        ctx.setBlock(new BlockPos(6, 2, 2), Blocks.STONE);

        ctx.setBlock(new BlockPos(8, 1, 2), Blocks.WATER);
        ctx.setBlock(new BlockPos(7, 1, 2), Blocks.STONE);
        ctx.setBlock(new BlockPos(9, 1, 2), Blocks.STONE);
        ctx.setBlock(new BlockPos(8, 1, 1), Blocks.STONE);
        ctx.setBlock(new BlockPos(8, 1, 3), Blocks.STONE);

        ctx.setBlock(new BlockPos(2, 4, 2), Blocks.GLOWSTONE);
        ctx.setBlock(new BlockPos(6, 4, 1), Blocks.GLOWSTONE);
        ctx.setBlock(new BlockPos(8, 4, 2), Blocks.GLOWSTONE);
    }

    private static void plantCrops(GameTestHelper ctx) {
        for (CropDef def : FLOWER_CROPS) {
            ctx.setBlock(def.pos(), def.crop().get());
        }
        ctx.setBlock(WISTERIA_POS, AABlocks.WISTERIA_CROP.get().defaultBlockState().setValue(BlockStateProperties.SOUTH, true));
        ctx.setBlock(LOTUS_POS, AABlocks.LOTUS_LILY_CROP.get());
    }

    // tests each growth stage with bone meal
    public static void stages(GameTestHelper ctx) {
        buildField(ctx);
        plantCrops(ctx);

        for (CropDef def : FLOWER_CROPS) {
            ctx.assertBlockPresent(def.crop().get(), def.pos());
            ctx.assertBlockProperty(def.pos(), FlowerCropBlock.AGE, 0);

            for (int age = 1; age < def.maxAge(); age++) {
                bonemeal(ctx, def.pos());
                ctx.assertBlockPresent(def.crop().get(), def.pos());
                ctx.assertBlockProperty(def.pos(), FlowerCropBlock.AGE, age);
            }

            bonemeal(ctx, def.pos());
            assertGrown(ctx, def);
        }

        // Wisteria
        ctx.assertBlockProperty(WISTERIA_POS, WisteriaCropBlock.AGE, 0);
        bonemeal(ctx, WISTERIA_POS);
        ctx.assertBlockProperty(WISTERIA_POS, WisteriaCropBlock.AGE, 1);
        bonemeal(ctx, WISTERIA_POS);
        ctx.assertBlockPresent(AABlocks.WISTERIA.get(), WISTERIA_POS);

        // Lotus lily
        ctx.assertBlockProperty(LOTUS_POS, LotusLilyCropBlock.AGE, 0);
        bonemeal(ctx, LOTUS_POS);
        ctx.assertBlockPresent(AABlocks.LOTUS_LILY.get(), LOTUS_POS);

        ctx.succeed();
    }

    // tests all plants grow naturally with random ticks
    public static void naturalGrowth(GameTestHelper ctx) {
        buildField(ctx);
        plantCrops(ctx);

        ctx.startSequence()
                .thenWaitUntil(() -> {
                    for (CropDef def : FLOWER_CROPS) {
                        assertGrown(ctx, def);
                    }
                    ctx.assertBlockPresent(AABlocks.WISTERIA.get(), WISTERIA_POS);
                    ctx.assertBlockPresent(AABlocks.LOTUS_LILY.get(), LOTUS_POS);
                })
                .thenSucceed();
    }

    // places a block item from a mock player onto the face of the block at pos
    private static void placeOn(GameTestHelper ctx, Player player, Supplier<Item> item, BlockPos pos, Direction face) {
        ItemStack stack = item.get().getDefaultInstance();
        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        ctx.placeAt(player, stack, pos, face);
    }

    // tests every plant can be planted from its seed item
    public static void planting(GameTestHelper ctx) {
        buildField(ctx);

        Player player = ctx.makeMockPlayer(GameType.CREATIVE);
        player.setXRot(0f);
        player.setYRot(0f);

        // flower crops
        for (CropDef def : FLOWER_CROPS) {
            placeOn(ctx, player, def.seed(), def.pos().below(), Direction.UP);
            ctx.assertBlockPresent(def.crop().get(), def.pos());
        }

        // wisteria
        placeOn(ctx, player, AAItems.WISTERIA_VINES, new BlockPos(6, 2, 2), Direction.NORTH);
        ctx.assertBlockPresent(AABlocks.WISTERIA_CROP.get(), WISTERIA_POS);

        // lotus lily
        BlockPos water = ctx.absolutePos(new BlockPos(8, 1, 2));
        player.setItemInHand(InteractionHand.MAIN_HAND, AAItems.LOTUS_LILY_POD.get().getDefaultInstance());
        player.snapTo(water.getX() + 0.5, water.getY() + 1.0, water.getZ() + 0.5);
        player.setXRot(90f);
        player.setYRot(0f);
        AAItems.LOTUS_LILY_POD.get().use(ctx.getLevel(), player, InteractionHand.MAIN_HAND);
        ctx.assertBlockPresent(AABlocks.LOTUS_LILY_CROP.get(), LOTUS_POS);

        ctx.succeed();
    }
}
