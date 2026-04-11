package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.level.block.Blocks;
import one.dqu.additionaladditions.entity.RopeArrow;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAEntities;
import one.dqu.additionaladditions.registry.AAItems;

import java.util.List;

public class RopeArrowTests {
    public static void placementPickup(GameTestHelper ctx) {
        BlockPos targetPos = new BlockPos(5, 9, 5);
        BlockPos arrowStartPos = new BlockPos(5, 6, 5);

        ctx.setBlock(targetPos, Blocks.STONE);

        ctx.runAtTickTime(5, () -> {
            RopeArrow arrow = ctx.spawn(AAEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.ALLOWED;
            arrow.setDeltaMovement(0, 1, 0);
        });

        ctx.runAtTickTime(30, () -> {
            for (int i = 1; i <= 8; i++) {
                BlockPos ropePos = targetPos.below(i);
                ctx.assertBlockPresent(AABlocks.ROPE_BLOCK.get(), ropePos);
            }
            ctx.assertBlockNotPresent(AABlocks.ROPE_BLOCK.get(), targetPos.below(9));
            ctx.succeed();
        });
    }

    public static void placementNoPickup(GameTestHelper ctx) {
        BlockPos targetPos = new BlockPos(5, 9, 5);
        BlockPos arrowStartPos = new BlockPos(5, 6, 5);

        ctx.setBlock(targetPos, Blocks.STONE);

        ctx.runAtTickTime(5, () -> {
            RopeArrow arrow = ctx.spawn(AAEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
            arrow.setDeltaMovement(0, 1, 0);
        });

        ctx.runAtTickTime(30, () -> {
            for (int i = 1; i <= 8; i++) {
                BlockPos ropePos = targetPos.below(i);
                ctx.assertBlockNotPresent(AABlocks.ROPE_BLOCK.get(), ropePos);
            }
            ctx.succeed();
        });
    }

    public static void badPlacement(GameTestHelper ctx) {
        BlockPos targetPos = new BlockPos(5, 9, 5);
        BlockPos arrowStartPos = new BlockPos(5, 6, 5);

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
            ctx.assertTrue(entities.size() == 1, Component.literal("Expected one item entity to be dropped"));
            ctx.assertTrue(entities.getFirst().getItem().getCount() == 8, Component.literal("Expected 8 rope items to be dropped"));
            ctx.succeed();
        });
    }

    public static void entityHitPickup(GameTestHelper ctx) {
        BlockPos pigPos = new BlockPos(2, 1, 2);
        BlockPos arrowStartPos = new BlockPos(2, 1, 0);

        ctx.runAtTickTime(5, () -> {
            var pig = ctx.spawnWithNoFreeWill(EntityType.PIG, pigPos);

            RopeArrow arrow = ctx.spawn(AAEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.ALLOWED;
            arrow.setDeltaMovement(0, 0.1, 0.8);
        });

        ctx.runAtTickTime(20, () -> {
            ctx.assertItemEntityPresent(AAItems.ROPE.get());
            ctx.succeed();
        });
    }

    public static void entityHitNoPickup(GameTestHelper ctx) {
        BlockPos pigPos = new BlockPos(2, 1, 2);
        BlockPos arrowStartPos = new BlockPos(2, 1, 0);

        ctx.runAtTickTime(5, () -> {
            var pig = ctx.spawnWithNoFreeWill(EntityType.PIG, pigPos);

            RopeArrow arrow = ctx.spawn(AAEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
            arrow.setDeltaMovement(0, 0.1, 0.8);
        });

        ctx.runAtTickTime(20, () -> {
            ctx.assertItemEntityNotPresent(AAItems.ROPE.get());
            ctx.succeed();
        });
    }

    public static void partialPlacementPickup(GameTestHelper ctx) {
        BlockPos targetPos = new BlockPos(5, 9, 5);
        BlockPos arrowStartPos = new BlockPos(5, 6, 5);
        BlockPos obstaclePos = targetPos.below(4);

        ctx.setBlock(targetPos, Blocks.STONE);
        ctx.setBlock(obstaclePos, Blocks.STONE);

        ctx.runAtTickTime(5, () -> {
            RopeArrow arrow = ctx.spawn(AAEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.ALLOWED;
            arrow.setDeltaMovement(0, 1, 0);
        });

        ctx.runAtTickTime(30, () -> {
            for (int i = 1; i <= 3; i++) {
                BlockPos ropePos = targetPos.below(i);
                ctx.assertBlockPresent(AABlocks.ROPE_BLOCK.get(), ropePos);
            }
            ctx.assertBlockPresent(Blocks.STONE, obstaclePos);

            List<ItemEntity> entities = ctx.getEntities(EntityType.ITEM);
            ctx.assertTrue(entities.size() == 1, Component.literal("Expected one item entity to be dropped"));
            ctx.assertTrue(entities.getFirst().getItem().getCount() == 5, Component.literal("Expected 5 rope items to be dropped"));
            ctx.succeed();
        });
    }

    public static void partialPlacementNoPickup(GameTestHelper ctx) {
        BlockPos targetPos = new BlockPos(5, 9, 5);
        BlockPos arrowStartPos = new BlockPos(5, 6, 5);
        BlockPos obstaclePos = targetPos.below(4);

        ctx.setBlock(targetPos, Blocks.STONE);
        ctx.setBlock(obstaclePos, Blocks.STONE);

        ctx.runAtTickTime(5, () -> {
            RopeArrow arrow = ctx.spawn(AAEntities.ROPE_ARROW.get(), arrowStartPos);
            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
            arrow.setDeltaMovement(0, 1, 0);
        });

        ctx.runAtTickTime(30, () -> {
            for (int i = 1; i <= 3; i++) {
                BlockPos ropePos = targetPos.below(i);
                ctx.assertBlockNotPresent(AABlocks.ROPE_BLOCK.get(), ropePos);
            }
            ctx.assertBlockPresent(Blocks.STONE, obstaclePos);

            ctx.assertItemEntityNotPresent(AAItems.ROPE.get());
            ctx.succeed();
        });
    }
}
