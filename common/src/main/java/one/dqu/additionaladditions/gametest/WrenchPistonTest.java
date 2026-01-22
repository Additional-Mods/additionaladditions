package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import one.dqu.additionaladditions.registry.AAItems;

/**
 * Test for the wrench item NOT rotating extended pistons.
 */
public class WrenchPistonTest {
    @GameTest(template = "additionaladditions:empty")
    public void test(GameTestHelper ctx) {
        BlockPos pistonBasePos = new BlockPos(0, 2, 0);
        BlockPos pistonHeadPos = pistonBasePos.above();
        BlockPos redstoneBlockPos = pistonBasePos.east();
        BlockPos dispenserPos = pistonBasePos.south();
        BlockPos dispenserPosForHead = dispenserPos.above();

        // Setup
        ctx.setBlock(pistonBasePos, Blocks.PISTON.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP));
        ctx.setBlock(redstoneBlockPos, Blocks.REDSTONE_BLOCK);
        ctx.setBlock(dispenserPos, Blocks.DISPENSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH));
        ctx.setBlock(dispenserPos.above(), Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.DOWN));
        ctx.spawnItem(AAItems.WRENCH_ITEM.get(), dispenserPos.above().above());

        // Test piston base
        ctx.runAtTickTime(10, () -> {
            ctx.pulseRedstone(dispenserPos.south(), 2);
            ctx.runAfterDelay(8, () -> {
                ctx.assertBlockPresent(Blocks.PISTON, pistonBasePos);
                ctx.assertBlockProperty(
                        pistonBasePos, BlockStateProperties.FACING,
                        (prop) -> prop == Direction.UP, "Piston base got rotated."
                );
            });
        });

        // Move dispenser up
        ctx.runAtTickTime(20, () -> {
            ctx.destroyBlock(dispenserPos);
            ctx.destroyBlock(dispenserPos.above());
            ctx.setBlock(dispenserPosForHead, Blocks.DISPENSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH));
            ctx.setBlock(dispenserPosForHead.above(), Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.DOWN));
            ctx.spawnItem(AAItems.WRENCH_ITEM.get(), dispenserPosForHead.above().above());
        });

        // Test piston head
        ctx.runAtTickTime(30, () -> {
            ctx.pulseRedstone(dispenserPosForHead.south(), 2);
            ctx.runAfterDelay(8, () -> {
                ctx.assertBlockPresent(Blocks.PISTON_HEAD, pistonHeadPos);
                ctx.assertBlockProperty(
                        pistonHeadPos, BlockStateProperties.FACING,
                        (prop) -> prop == Direction.UP, "Piston head got rotated."
                );
            });
        });

        ctx.runAtTickTime(40, ctx::succeed);
    }
}

