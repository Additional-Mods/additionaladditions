package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import one.dqu.additionaladditions.entity.RopeArrow;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAEntities;
import one.dqu.additionaladditions.registry.AAItems;

/**
 * Test for rope arrows not duplicating rope when shot at a floating gravity block.
 * https://github.com/Additional-Mods/additionaladditions/issues/175
 */
public class RopeArrowGravityBlockDupeTest {

    @GameTest(template = "additionaladditions:empty_big")
    public void gravityBlockDupe(GameTestHelper ctx) {
        BlockPos sandPos = new BlockPos(6, 6, 5);
        BlockPos supportPos = new BlockPos(6, 5, 5);
        BlockPos arrowStartPos = new BlockPos(2, 6, 5);

        ctx.setBlock(supportPos, Blocks.STONE);
        ctx.setBlock(sandPos, Blocks.SAND);

        ctx.runAtTickTime(5, () -> {
            // remove the support without notifying the sand, leaving it floating
            ctx.getLevel().setBlock(
                    ctx.absolutePos(supportPos),
                    Blocks.AIR.defaultBlockState(),
                    Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE
            );

            RopeArrow arrow = ctx.spawn(AAEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.ALLOWED;
            arrow.setDeltaMovement(0.85, 0.12, 0);
        });

        ctx.runAtTickTime(80, () -> {
            int total = 0;
            for (ItemEntity item : ctx.getEntities(EntityType.ITEM)) {
                if (item.getItem().is(AAItems.ROPE.get())) {
                    total += item.getItem().getCount();
                }
            }

            // empty_big is 10x11x10
            for (BlockPos pos : BlockPos.betweenClosed(new BlockPos(0, 0, 0), new BlockPos(9, 10, 9))) {
                if (ctx.getBlockState(pos).is(AABlocks.ROPE_BLOCK.get())) {
                    total++;
                }
            }

            ctx.assertTrue(total <= 8, "Expected at most 8 total rope (items + placed), got " + total);
            ctx.succeed();
        });
    }
}
