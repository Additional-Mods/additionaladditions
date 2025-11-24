package one.dqu.additionaladditions.test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import one.dqu.additionaladditions.registry.AdditionalItems;

/**
 * Test for hoppers rotated by wrench updating their block entity correctly.
 */
public class WrenchHopperTest {
    @GameTest(template = "additionaladditions:empty")
    public void test(GameTestHelper ctx) {
        BlockPos hopperPos = new BlockPos(2, 2, 2);
        BlockPos chestEastPos = hopperPos.east();
        BlockPos chestWestPos = hopperPos.west();
        BlockPos dispenserPos = hopperPos.north();

        // setup
        ctx.setBlock(hopperPos, Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.EAST));
        ctx.setBlock(chestEastPos, Blocks.CHEST);
        ctx.setBlock(chestWestPos, Blocks.CHEST);
        ctx.setBlock(dispenserPos, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.SOUTH));
        ctx.setBlock(dispenserPos.above(), Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, Direction.DOWN));
        ctx.spawnItem(AdditionalItems.WRENCH_ITEM.get(), dispenserPos.above().above());

        ctx.runAtTickTime(10, () -> {
            ctx.spawnItem(Items.IRON_INGOT, hopperPos.above());
        });

        // pulse redstone 4 times that gets it from east to west
        for (int i = 0; i < 4; i++) {
            int delay = 20 + i * 4;
            ctx.runAtTickTime(delay, () -> {
                ctx.pulseRedstone(dispenserPos.north(), 2);
            });
        }

        ctx.runAtTickTime(50, () -> {
            ctx.spawnItem(Items.GOLD_INGOT, hopperPos.above());
        });

        ctx.runAtTickTime(60, () -> {
            ctx.assertContainerContains(chestEastPos, Items.IRON_INGOT);
            ctx.assertContainerContains(chestWestPos, Items.GOLD_INGOT);
            ctx.succeed();
        });
    }
}
