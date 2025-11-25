package one.dqu.additionaladditions.test;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.Blocks;
import one.dqu.additionaladditions.entity.RopeArrow;
import one.dqu.additionaladditions.registry.AdditionalBlocks;
import one.dqu.additionaladditions.registry.AdditionalEntities;

/**
 * Tests for rope arrow placing 8 ropes when hitting a block.
 */
public class RopeArrowPlacementTest {

    @GameTest(template = "additionaladditions:empty_big")
    public void pickup(GameTestHelper ctx) {
        BlockPos targetPos = new BlockPos(5, 10, 5);
        BlockPos arrowStartPos = new BlockPos(5, 7, 5);

        ctx.setBlock(targetPos, Blocks.STONE);

        ctx.runAtTickTime(5, () -> {
            RopeArrow arrow = ctx.spawn(AdditionalEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.ALLOWED;
            arrow.setDeltaMovement(0, 1, 0);
        });

        ctx.runAtTickTime(30, () -> {
            for (int i = 1; i <= 8; i++) {
                BlockPos ropePos = targetPos.below(i);
                ctx.assertBlockPresent(AdditionalBlocks.ROPE_BLOCK.get(), ropePos);
            }
            ctx.assertBlockNotPresent(AdditionalBlocks.ROPE_BLOCK.get(), targetPos.below(9));
            ctx.succeed();
        });
    }

    @GameTest(template = "additionaladditions:empty_big")
    public void noPickup(GameTestHelper ctx) {
        BlockPos targetPos = new BlockPos(5, 10, 5);
        BlockPos arrowStartPos = new BlockPos(5, 7, 5);

        ctx.setBlock(targetPos, Blocks.STONE);

        ctx.runAtTickTime(5, () -> {
            RopeArrow arrow = ctx.spawn(AdditionalEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
            arrow.setDeltaMovement(0, 1, 0);
        });

        ctx.runAtTickTime(30, () -> {
            for (int i = 1; i <= 8; i++) {
                BlockPos ropePos = targetPos.below(i);
                ctx.assertBlockNotPresent(AdditionalBlocks.ROPE_BLOCK.get(), ropePos);
            }
            ctx.succeed();
        });
    }
}

