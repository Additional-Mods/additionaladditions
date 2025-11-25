package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import one.dqu.additionaladditions.registry.AABlocks;

/**
 * Verifies that copper patina transmits power correctly and doesn't interfere with redstone wire.
 */
public class CopperPatinaTransmitTest {
    @GameTest(template = "additionaladditions:empty")
    public void test(GameTestHelper ctx) {
        BlockPos lampOne = new BlockPos(0, 3, 0);
        BlockPos lampTwo = new BlockPos(1, 3, 0);
        BlockPos pulseOne = new BlockPos(0, 2, 4);
        BlockPos pulseTwo = new BlockPos(1, 2, 4);

        ctx.setBlock(lampOne, Blocks.REDSTONE_LAMP);
        ctx.setBlock(lampOne.below(), Blocks.STONE);
        ctx.setBlock(lampTwo, Blocks.REDSTONE_LAMP);
        ctx.setBlock(lampTwo.below(), Blocks.STONE);

        for (int i = 1; i < 4; i++) {
            ctx.setBlock(new BlockPos(0, 2, i), Blocks.REDSTONE_WIRE);
            ctx.setBlock(new BlockPos(1, 2, i), AABlocks.COPPER_PATINA.get());
        }

        ctx.runAtTickTime(5, () -> {
            ctx.pulseRedstone(pulseOne, 2);
            ctx.assertBlockProperty(lampOne, BlockStateProperties.LIT, (state) -> state, "Lamp one should be lit");
        });

        ctx.runAtTickTime(10, () -> {;
            ctx.pulseRedstone(pulseTwo, 2);
            ctx.assertBlockProperty(lampTwo, BlockStateProperties.LIT, (state) -> state, "Lamp two should be lit");
        });

        ctx.runAtTickTime(20, () -> {
            ctx.assertBlockProperty(lampOne, BlockStateProperties.LIT, (state) -> !state, "Lamp one should be unlit");
            ctx.assertBlockProperty(lampTwo, BlockStateProperties.LIT, (state) -> !state, "Lamp two should be unlit");
            ctx.succeed();
        });
    }
 }
