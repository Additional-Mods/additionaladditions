package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import one.dqu.additionaladditions.registry.AABlocks;

/**
 * Tests that copper patina doesn't interfere with redstone wire.
 */
public class CopperPatinaInterferenceTest {
    @GameTest(template = "additionaladditions:empty")
    public void crossing(GameTestHelper ctx) {
        for (int z = 0; z < 5; z++) {
            ctx.setBlock(new BlockPos(2, 2, z), AABlocks.COPPER_PATINA.get());
        }
        for (int x = 0; x < 5; x++) {
            ctx.setBlock(new BlockPos(x, 2, 2), Blocks.REDSTONE_WIRE);
        }

        BlockPos redstoneTarget = new BlockPos(4, 3, 2);
        BlockPos patinaTarget = new BlockPos(2, 3, 4);
        ctx.setBlock(redstoneTarget, Blocks.REDSTONE_LAMP);
        ctx.setBlock(redstoneTarget.below(), Blocks.STONE);
        ctx.setBlock(patinaTarget, Blocks.REDSTONE_LAMP);
        ctx.setBlock(patinaTarget.below(), Blocks.STONE);

        ctx.runAtTickTime(5, () -> {
            ctx.pulseRedstone(new BlockPos(0, 2, 2), 2);
            ctx.assertBlockProperty(redstoneTarget, BlockStateProperties.LIT, (state) -> state,
                "Redstone lamp should be lit");
            ctx.assertBlockProperty(patinaTarget, BlockStateProperties.LIT, (state) -> !state,
                "Patina lamp should not be lit");
            ctx.succeed();
        });
    }

    @GameTest(template = "additionaladditions:empty")
    public void directTransition(GameTestHelper ctx) {
        BlockPos start = new BlockPos(0, 2, 0);

        for (int x = 0; x < 2; x++) {
            ctx.setBlock(new BlockPos(x, 2, 0), Blocks.REDSTONE_WIRE);
        }
        for (int x = 2; x < 4; x++) {
            ctx.setBlock(new BlockPos(x, 2, 0), AABlocks.COPPER_PATINA.get());
        }

        BlockPos lampPos = new BlockPos(4, 3, 0);
        ctx.setBlock(lampPos, Blocks.REDSTONE_LAMP);
        ctx.setBlock(lampPos.below(), Blocks.STONE);

        ctx.pulseRedstone(start, 10);

        ctx.runAtTickTime(5, () -> {
            ctx.assertBlockProperty(lampPos, BlockStateProperties.LIT, (state) -> !state, "Lamp should not be lit");
            ctx.succeed();
        });
    }

    @GameTest(template = "additionaladditions:empty")
    public void softPower(GameTestHelper ctx) {
        BlockPos redstonePos = new BlockPos(1, 2, 0);
        BlockPos stonePos = new BlockPos(2, 2, 0);
        BlockPos patinaPos = new BlockPos(3, 2, 0);

        ctx.setBlock(redstonePos, Blocks.REDSTONE_WIRE);
        ctx.setBlock(stonePos, Blocks.STONE);
        ctx.setBlock(patinaPos, AABlocks.COPPER_PATINA.get());

        ctx.pulseRedstone(new BlockPos(0, 2, 0), 5);
        ctx.runAtTickTime(4, () -> {
            ctx.assertBlockProperty(patinaPos, BlockStateProperties.POWER, power -> power == 0,
                    "Copper patina should not be powered from redstone through stone");
        });

        ctx.runAtTickTime(10, () -> {
            ctx.assertBlockProperty(redstonePos, BlockStateProperties.POWER, power -> power == 0,
                    "Redstone wire should be unpowered after pulse");
            ctx.pulseRedstone(new BlockPos(4, 2, 0), 5);
        });

        ctx.runAtTickTime(14, () -> {
            ctx.assertBlockProperty(redstonePos, BlockStateProperties.POWER, power -> power == 0,
                    "Redstone wire should not be powered from patina through stone");
        });

        ctx.runAtTickTime(20, () -> {
            ctx.assertBlockProperty(patinaPos, BlockStateProperties.POWER, power -> power == 0,
                    "Copper patina should be unpowered after pulse");
            ctx.succeed();
        });
    }
}

