package one.dqu.additionaladditions.test;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.projectile.AbstractArrow;
import one.dqu.additionaladditions.entity.RopeArrow;
import one.dqu.additionaladditions.registry.AdditionalBlocks;
import one.dqu.additionaladditions.registry.AdditionalEntities;

/**
 * Tests for rope arrow hitting entities and dropping ropes.
 */
public class RopeArrowEntityHitTest {
    @GameTest(template = "additionaladditions:empty")
    public void pickup(GameTestHelper ctx) {
        BlockPos pigPos = new BlockPos(2, 2, 2);
        BlockPos arrowStartPos = new BlockPos(2, 2, 0);

        ctx.runAtTickTime(5, () -> {
            Pig pig = ctx.spawnWithNoFreeWill(EntityType.PIG, pigPos);

            RopeArrow arrow = ctx.spawn(AdditionalEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.ALLOWED;
            arrow.setDeltaMovement(0, 0.1, 0.8);
        });

        ctx.runAtTickTime(20, () -> {
            ctx.assertItemEntityPresent(AdditionalBlocks.ROPE_BLOCK_ITEM.get());
            ctx.succeed();
        });
    }

    @GameTest(template = "additionaladditions:empty")
    public void noPickup(GameTestHelper ctx) {
        BlockPos pigPos = new BlockPos(2, 2, 2);
        BlockPos arrowStartPos = new BlockPos(2, 2, 0);

        ctx.runAtTickTime(5, () -> {
            Pig pig = ctx.spawnWithNoFreeWill(EntityType.PIG, pigPos);

            RopeArrow arrow = ctx.spawn(AdditionalEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
            arrow.setDeltaMovement(0, 0.1, 0.8);
        });

        ctx.runAtTickTime(20, () -> {
            ctx.assertItemEntityNotPresent(AdditionalBlocks.ROPE_BLOCK_ITEM.get());
            ctx.succeed();
        });
    }
}

