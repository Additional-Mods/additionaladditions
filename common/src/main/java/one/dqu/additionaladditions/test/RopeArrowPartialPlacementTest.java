package one.dqu.additionaladditions.test;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.Blocks;
import one.dqu.additionaladditions.entity.RopeArrow;
import one.dqu.additionaladditions.registry.AdditionalBlocks;
import one.dqu.additionaladditions.registry.AdditionalEntities;

import java.util.List;

/**
 * Tests for rope arrow placing less than 8 ropes and dropping the remainder.
 */
public class RopeArrowPartialPlacementTest {
    @GameTest(template = "additionaladditions:empty_big")
    public void pickup(GameTestHelper ctx) {
        BlockPos targetPos = new BlockPos(5, 10, 5);
        BlockPos arrowStartPos = new BlockPos(5, 7, 5);
        BlockPos obstaclePos = targetPos.below(4);

        ctx.setBlock(targetPos, Blocks.STONE);
        ctx.setBlock(obstaclePos, Blocks.STONE);

        ctx.runAtTickTime(5, () -> {
            RopeArrow arrow = ctx.spawn(AdditionalEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.ALLOWED;
            arrow.setDeltaMovement(0, 1, 0);
        });

        ctx.runAtTickTime(30, () -> {
            for (int i = 1; i <= 3; i++) {
                BlockPos ropePos = targetPos.below(i);
                ctx.assertBlockPresent(AdditionalBlocks.ROPE_BLOCK.get(), ropePos);
            }
            ctx.assertBlockPresent(Blocks.STONE, obstaclePos);

            List<ItemEntity> entities = ctx.getEntities(EntityType.ITEM);
            ctx.assertTrue(entities.size() == 1, "Expected one item entity to be dropped");
            ctx.assertTrue(entities.getFirst().getItem().getCount() == 5, "Expected 5 rope items to be dropped");
            ctx.succeed();
        });
    }

    @GameTest(template = "additionaladditions:empty_big")
    public void noPickup(GameTestHelper ctx) {
        BlockPos targetPos = new BlockPos(5, 10, 5);
        BlockPos arrowStartPos = new BlockPos(5, 7, 5);
        BlockPos obstaclePos = targetPos.below(4);

        ctx.setBlock(targetPos, Blocks.STONE);
        ctx.setBlock(obstaclePos, Blocks.STONE);

        ctx.runAtTickTime(5, () -> {
            RopeArrow arrow = ctx.spawn(AdditionalEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
            arrow.setDeltaMovement(0, 1, 0);
        });

        ctx.runAtTickTime(30, () -> {
            for (int i = 1; i <= 3; i++) {
                BlockPos ropePos = targetPos.below(i);
                ctx.assertBlockNotPresent(AdditionalBlocks.ROPE_BLOCK.get(), ropePos);
            }
            ctx.assertBlockPresent(Blocks.STONE, obstaclePos);

            ctx.assertItemEntityNotPresent(AdditionalBlocks.ROPE_BLOCK_ITEM.get());
            ctx.succeed();
        });
    }
}

