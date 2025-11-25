package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import one.dqu.additionaladditions.registry.AAItems;

/**
 * Test for the wrench item rotating blocks by a player.
 */
public class WrenchPlayerTest {
    @GameTest(template = "additionaladditions:empty")
    public void test(GameTestHelper ctx) {
        BlockPos pos = new BlockPos(0, 2, 0);
        Player player = ctx.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, AAItems.WRENCH_ITEM.get().getDefaultInstance());
        player.setShiftKeyDown(true);
        player.setPose(Pose.CROUCHING);

        ctx.runAtTickTime(1, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.BIRCH_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
            ctx.useBlock(pos, player);
            ctx.assertFalse(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.AXIS) == Direction.Axis.Y,
                    "Axis property not rotated."
            );
        });

        ctx.runAtTickTime(2, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.BIRCH_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
            ctx.useBlock(pos, player);
            ctx.assertFalse(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM,
                    "Slab type property not rotated."
            );
        });

        ctx.runAtTickTime(3, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.BIRCH_STAIRS.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST));
            ctx.useBlock(pos, player);
            ctx.assertFalse(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.HORIZONTAL_FACING) == Direction.EAST,
                    "Horizontal facing property not rotated."
            );
        });

        ctx.runAtTickTime(4, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.OBSERVER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP));
            ctx.useBlock(pos, player);
            ctx.assertFalse(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.FACING) == Direction.UP,
                    "Facing property not rotated."
            );
        });

        ctx.runAtTickTime(5, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.NETHER_PORTAL.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X));
            ctx.useBlock(pos, player);
            ctx.assertTrue(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.HORIZONTAL_AXIS) == Direction.Axis.X,
                    "Nether portal got rotated."
            );
        });

        ctx.runAtTickTime(6, () -> {
            ctx.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.END_PORTAL_FRAME.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST));
            ctx.useBlock(pos, player);
            ctx.assertTrue(
                    ctx.getBlockState(pos).getValue(BlockStateProperties.HORIZONTAL_FACING) == Direction.WEST,
                    "End portal frame got rotated."
            );
        });

        ctx.runAtTickTime(7, () -> {
            ctx.assertTrue(
                    player.getMainHandItem().getDamageValue() > 0,
                    "Wrench did not take durability damage."
            );
        });

        ctx.runAtTickTime(8, ctx::succeed);
    }
}
