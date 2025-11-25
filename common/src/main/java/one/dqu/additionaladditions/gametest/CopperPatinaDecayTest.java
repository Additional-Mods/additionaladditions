package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import one.dqu.additionaladditions.registry.AABlocks;

/**
 * Tests that copper patina signal strength decays properly over distance (like redstone wire).
 */
public class CopperPatinaDecayTest {
    @GameTest(template = "additionaladditions:empty")
    public void test(GameTestHelper ctx) {
        BlockPos powerSource = new BlockPos(0, 2, 0);
        BlockPos patina1 = new BlockPos(1, 2, 0);
        BlockPos patina2 = new BlockPos(2, 2, 0);
        BlockPos patina3 = new BlockPos(3, 2, 0);

        ctx.pulseRedstone(powerSource, 10);

        ctx.setBlock(patina1, AABlocks.COPPER_PATINA.get());
        ctx.setBlock(patina2, AABlocks.COPPER_PATINA.get());
        ctx.setBlock(patina3, AABlocks.COPPER_PATINA.get());

        ctx.runAtTickTime(5, () -> {
            ctx.assertBlockProperty(patina1, BlockStateProperties.POWER, power -> power == 15, "First patina should have power 15");
            ctx.assertBlockProperty(patina2, BlockStateProperties.POWER, power -> power == 14, "Second patina should have power 14");
            ctx.assertBlockProperty(patina3, BlockStateProperties.POWER, power -> power == 13, "Third patina should have power 13");

            ctx.succeed();
        });
    }
}

