package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.Blocks;
import one.dqu.additionaladditions.entity.RopeArrow;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAEntities;

import java.util.List;

/**
 * Tests for rope arrow hitting a block that cannot support ropes.
 */
public class RopeArrowBadPlacementTest {
    @GameTest(template = "additionaladditions:empty_big")
    public void pickup(GameTestHelper ctx) {
        BlockPos targetPos = new BlockPos(5, 10, 5);
        BlockPos arrowStartPos = new BlockPos(5, 7, 5);

        ctx.setBlock(targetPos, Blocks.CAKE);

        ctx.runAtTickTime(5, () -> {
            RopeArrow arrow = ctx.spawn(AAEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.ALLOWED;
            arrow.setDeltaMovement(0, 1, 0);
        });

        ctx.runAtTickTime(30, () -> {
            for (int i = 1; i <= 8; i++) {
                BlockPos ropePos = targetPos.below(i);
                ctx.assertBlockNotPresent(AABlocks.ROPE_BLOCK.get(), ropePos);
            }

            List<ItemEntity> entities = ctx.getEntities(EntityType.ITEM);
            ctx.assertTrue(entities.size() == 1, "Expected one item entity to be dropped");
            ctx.assertTrue(entities.getFirst().getItem().getCount() == 8, "Expected 8 rope items to be dropped");
            ctx.succeed();
        });
    }
}

