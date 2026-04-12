package one.dqu.additionaladditions.gametest.tests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import one.dqu.additionaladditions.registry.AAItems;

public class WrenchTests {
    public static void dispenser(GameTestHelper ctx) {
        BlockPos pos = new BlockPos(0, 1, 0);
        BlockPos dispenserPos = pos.east();
        ctx.setBlock(dispenserPos, Blocks.DISPENSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST));
        ctx.setBlock(dispenserPos.above(), Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.DOWN));
        ctx.spawnItem(AAItems.WRENCH_ITEM.get(), dispenserPos.above().above());

        ctx.runAtTickTime(10, () -> {
            ctx.setBlock(pos, Blocks.BIRCH_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
            ctx.pulseRedstone(dispenserPos.east(), 2);
            ctx.runAfterDelay(8, () -> {
                ctx.assertFalse(
                        ctx.getBlockState(pos).getValue(BlockStateProperties.AXIS) == Direction.Axis.Y,
                        Component.literal("Axis property not rotated.")
                );
            });
        });

        ctx.runAtTickTime(20, () -> {
            ctx.setBlock(pos, Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.DOWN));
            ctx.pulseRedstone(dispenserPos.east(), 2);
            ctx.runAfterDelay(8, () -> {
                ctx.assertFalse(
                        ctx.getBlockState(pos).getValue(BlockStateProperties.FACING_HOPPER) == Direction.DOWN,
                        Component.literal("Hopper facing property not rotated.")
                );
            });
        });

        ctx.runAtTickTime(30, ctx::succeed);
    }

    public static void hopper(GameTestHelper ctx) {
        BlockPos hopperPos = new BlockPos(2, 1, 2);
        BlockPos chestEastPos = hopperPos.east();
        BlockPos chestWestPos = hopperPos.west();
        BlockPos dispenserPos = hopperPos.north();

        ctx.setBlock(hopperPos, Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.EAST));
        ctx.setBlock(chestEastPos, Blocks.CHEST);
        ctx.setBlock(chestWestPos, Blocks.CHEST);
        ctx.setBlock(dispenserPos, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.SOUTH));
        ctx.setBlock(dispenserPos.above(), Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.DOWN));
        ctx.spawnItem(AAItems.WRENCH_ITEM.get(), dispenserPos.above().above());

        ctx.runAtTickTime(10, () -> ctx.spawnItem(Items.IRON_INGOT, hopperPos.above()));

        for (int i = 0; i < 4; i++) {
            int delay = 20 + i * 4;
            ctx.runAtTickTime(delay, () -> ctx.pulseRedstone(dispenserPos.north(), 2));
        }

        ctx.runAtTickTime(50, () -> ctx.spawnItem(Items.GOLD_INGOT, hopperPos.above()));

        ctx.runAtTickTime(60, () -> {
            ctx.assertContainerContains(chestEastPos, Items.IRON_INGOT);
            ctx.assertContainerContains(chestWestPos, Items.GOLD_INGOT);
            ctx.succeed();
        });
    }

    public static void piston(GameTestHelper ctx) {
        BlockPos pistonBasePos = new BlockPos(0, 1, 0);
        BlockPos pistonHeadPos = pistonBasePos.above();
        BlockPos redstoneBlockPos = pistonBasePos.east();
        BlockPos dispenserPos = pistonBasePos.south();
        BlockPos dispenserPosForHead = dispenserPos.above();

        ctx.setBlock(pistonBasePos, Blocks.PISTON.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP));
        ctx.setBlock(redstoneBlockPos, Blocks.REDSTONE_BLOCK);
        ctx.setBlock(dispenserPos, Blocks.DISPENSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH));
        ctx.setBlock(dispenserPos.above(), Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.DOWN));
        ctx.spawnItem(AAItems.WRENCH_ITEM.get(), dispenserPos.above().above());

        ctx.runAtTickTime(10, () -> {
            ctx.pulseRedstone(dispenserPos.south(), 2);
            ctx.runAfterDelay(8, () -> {
                ctx.assertBlockPresent(Blocks.PISTON, pistonBasePos);
                ctx.assertBlockProperty(
                        pistonBasePos, BlockStateProperties.FACING,
                        prop -> prop == Direction.UP, Component.literal("Piston base got rotated.")
                );
            });
        });

        ctx.runAtTickTime(20, () -> {
            ctx.destroyBlock(dispenserPos);
            ctx.destroyBlock(dispenserPos.above());
            ctx.setBlock(dispenserPosForHead, Blocks.DISPENSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH));
            ctx.setBlock(dispenserPosForHead.above(), Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.DOWN));
            ctx.spawnItem(AAItems.WRENCH_ITEM.get(), dispenserPosForHead.above().above());
        });

        ctx.runAtTickTime(30, () -> {
            ctx.pulseRedstone(dispenserPosForHead.south(), 2);
            ctx.runAfterDelay(8, () -> {
                ctx.assertBlockPresent(Blocks.PISTON_HEAD, pistonHeadPos);
                ctx.assertBlockProperty(
                        pistonHeadPos, BlockStateProperties.FACING,
                        prop -> prop == Direction.UP, Component.literal("Piston head got rotated.")
                );
            });
        });

        ctx.runAtTickTime(40, ctx::succeed);
    }

    public static void player(GameTestHelper ctx) {
        BlockPos pos = new BlockPos(0, 1, 0);
        Player player = ctx.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, AAItems.WRENCH_ITEM.get().getDefaultInstance());
        player.setShiftKeyDown(true);
        player.setPose(Pose.CROUCHING);

        ctx.runAtTickTime(1, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.BIRCH_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
            ctx.useBlock(pos, player);
            ctx.assertFalse(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.AXIS) == Direction.Axis.Y,
                    Component.literal("Axis property not rotated.")
            );
        });

        ctx.runAtTickTime(2, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.BIRCH_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
            ctx.useBlock(pos, player);
            ctx.assertFalse(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM,
                    Component.literal("Slab type property not rotated.")
            );
        });

        ctx.runAtTickTime(3, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.BIRCH_STAIRS.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST));
            ctx.useBlock(pos, player);
            ctx.assertFalse(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.HORIZONTAL_FACING) == Direction.EAST,
                    Component.literal("Horizontal facing property not rotated.")
            );
        });

        ctx.runAtTickTime(4, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.OBSERVER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP));
            ctx.useBlock(pos, player);
            ctx.assertFalse(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.FACING) == Direction.UP,
                    Component.literal("Facing property not rotated.")
            );
        });

        ctx.runAtTickTime(5, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.NETHER_PORTAL.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X));
            ctx.useBlock(pos, player);
            ctx.assertTrue(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.HORIZONTAL_AXIS) == Direction.Axis.X,
                    Component.literal("Nether portal got rotated.")
            );
        });

        ctx.runAtTickTime(6, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.END_PORTAL_FRAME.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST));
            ctx.useBlock(pos, player);
            ctx.assertTrue(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.HORIZONTAL_FACING) == Direction.WEST,
                    Component.literal("End portal frame got rotated.")
            );
        });

        ctx.runAtTickTime(7, () -> ctx.assertTrue(
                player.getMainHandItem().getDamageValue() > 0,
                Component.literal("Wrench did not take durability damage.")
        ));

        ctx.runAtTickTime(8, ctx::succeed);
    }
}
