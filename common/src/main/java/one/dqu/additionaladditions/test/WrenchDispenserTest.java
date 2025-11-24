package one.dqu.additionaladditions.test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import one.dqu.additionaladditions.registry.AdditionalItems;

/**
 * Test for dispenser wrench behavior
 */
public class WrenchDispenserTest {
    @GameTest(template = "additionaladditions:empty")
    public void test(GameTestHelper ctx) {
        BlockPos pos = new BlockPos(0, 2, 0);
        BlockPos dispenserPos = pos.east();
        ctx.setBlock(dispenserPos, Blocks.DISPENSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST));
        ctx.setBlock(dispenserPos.above(), Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.DOWN));
        ctx.spawnItem(AdditionalItems.WRENCH_ITEM.get(), dispenserPos.above().above());

        ctx.runAtTickTime(10, () -> {
            ctx.setBlock(pos, Blocks.BIRCH_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
            ctx.pulseRedstone(dispenserPos.east(), 2);
            ctx.runAfterDelay(8, () -> {
                ctx.assertFalse(
                        ctx.getBlockState(pos).getValue(BlockStateProperties.AXIS) == Direction.Axis.Y,
                        "Axis property not rotated."
                );
            });
        });

        ctx.runAtTickTime(20, () -> {
            ctx.setBlock(pos, Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.DOWN));
            ctx.pulseRedstone(dispenserPos.east(), 2);
            ctx.runAfterDelay(8, () -> {
                ctx.assertFalse(
                        ctx.getBlockState(pos).getValue(BlockStateProperties.FACING_HOPPER) == Direction.DOWN,
                        "Hopper facing property not rotated."
                );
            });
        });

        ctx.runAtTickTime(30, ctx::succeed);
    }
}
