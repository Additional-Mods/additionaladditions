package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;

public class WateringCanTests {
    private static ItemStack can(int level) {
        ItemStack stack = AAItems.WATERING_CAN.get().getDefaultInstance();
        if (level > 0) {
            stack.set(AAMisc.WATER_LEVEL_COMPONENT.get(), level);
        }
        return stack;
    }

    private static int canLevel(Player player) {
        return player.getMainHandItem().getOrDefault(AAMisc.WATER_LEVEL_COMPONENT.get(), 0);
    }

    private static Player player(GameTestHelper ctx, GameType mode, int level) {
        Player player = ctx.makeMockPlayer(mode);
        player.setItemInHand(InteractionHand.MAIN_HAND, can(level));
        return player;
    }

    private static void aim(GameTestHelper ctx, Player player, BlockPos relTarget) {
        BlockPos abs = ctx.absolutePos(relTarget);
        player.snapTo(abs.getX() + 0.5, abs.getY() + 2.0, abs.getZ() + 0.5);
        player.setXRot(90f);
        player.setYRot(0f);
    }

    private static void use(GameTestHelper ctx, Player player) {
        AAItems.WATERING_CAN.get().use(ctx.getLevel(), player, InteractionHand.MAIN_HAND);
    }

    private static void assertCanLevel(GameTestHelper ctx, Player player, int expected) {
        int actual = canLevel(player);
        ctx.assertTrue(actual == expected, Component.literal("Expected watering can level " + expected + " but was " + actual));
    }

    // Empty can on a water source drains the full bucket and removes the source
    public static void fillWaterFull(GameTestHelper ctx) {
        BlockPos water = new BlockPos(2, 1, 2);
        ctx.setBlock(water, Blocks.WATER);
        ctx.setBlock(new BlockPos(1, 1, 2), Blocks.STONE);
        ctx.setBlock(new BlockPos(3, 1, 2), Blocks.STONE);
        ctx.setBlock(new BlockPos(2, 1, 1), Blocks.STONE);
        ctx.setBlock(new BlockPos(2, 1, 3), Blocks.STONE);

        Player player = player(ctx, GameType.SURVIVAL, 0);
        aim(ctx, player, water);
        use(ctx, player);

        ctx.assertBlockPresent(Blocks.AIR, water);
        assertCanLevel(ctx, player, 10);
        ctx.succeed();
    }

    // Half full can only needs 500mb, less than a bucket, so the source stays
    public static void fillWaterPartial(GameTestHelper ctx) {
        BlockPos water = new BlockPos(2, 1, 2);
        ctx.setBlock(water, Blocks.WATER);
        ctx.setBlock(new BlockPos(1, 1, 2), Blocks.STONE);
        ctx.setBlock(new BlockPos(3, 1, 2), Blocks.STONE);
        ctx.setBlock(new BlockPos(2, 1, 1), Blocks.STONE);
        ctx.setBlock(new BlockPos(2, 1, 3), Blocks.STONE);

        Player player = player(ctx, GameType.SURVIVAL, 5);
        aim(ctx, player, water);
        use(ctx, player);

        ctx.assertBlockPresent(Blocks.WATER, water);
        assertCanLevel(ctx, player, 10);
        ctx.succeed();
    }

    // Half full can drains 2 of 3 cauldron layers, leaving level 1
    public static void fillWaterCauldron(GameTestHelper ctx) {
        BlockPos cauldron = new BlockPos(2, 1, 2);
        ctx.setBlock(cauldron, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));

        Player player = player(ctx, GameType.SURVIVAL, 5);
        aim(ctx, player, cauldron);
        use(ctx, player);

        ctx.assertBlockProperty(cauldron, LayeredCauldronBlock.LEVEL, 1);
        ctx.succeed();
    }

    // Watering farmland moistens it and uses one level
    public static void interactFarmland(GameTestHelper ctx) {
        BlockPos farmland = new BlockPos(2, 1, 2);
        ctx.setBlock(farmland, Blocks.FARMLAND.defaultBlockState().setValue(BlockStateProperties.MOISTURE, 0));

        Player player = player(ctx, GameType.SURVIVAL, 5);
        aim(ctx, player, farmland);
        use(ctx, player);

        ctx.assertBlockProperty(farmland, BlockStateProperties.MOISTURE, 7);
        assertCanLevel(ctx, player, 4);
        ctx.succeed();
    }

    // Watering a crop moistens the farmland below and (eventually) grows it
    public static void interactCrop(GameTestHelper ctx) {
        BlockPos farmland = new BlockPos(2, 1, 2);
        BlockPos crop = farmland.above();
        ctx.setBlock(farmland, Blocks.FARMLAND.defaultBlockState().setValue(BlockStateProperties.MOISTURE, 0));
        ctx.setBlock(crop, Blocks.WHEAT);

        Player player = player(ctx, GameType.CREATIVE, 0);
        aim(ctx, player, crop);

        for (int i = 0; i < 500 && ctx.getBlockState(crop).getValue(BlockStateProperties.AGE_7) < 7; i++) {
            use(ctx, player);
        }

        ctx.assertBlockProperty(crop, BlockStateProperties.AGE_7, 7);
        ctx.assertBlockProperty(farmland, BlockStateProperties.MOISTURE, 7);
        ctx.succeed();
    }

    // Watering a grown crop does not grow it, but still moistens and uses a level
    public static void interactGrownCrop(GameTestHelper ctx) {
        BlockPos farmland = new BlockPos(2, 1, 2);
        BlockPos crop = farmland.above();
        ctx.setBlock(farmland, Blocks.FARMLAND.defaultBlockState().setValue(BlockStateProperties.MOISTURE, 0));
        ctx.setBlock(crop, Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7));

        Player player = player(ctx, GameType.SURVIVAL, 5);
        aim(ctx, player, crop);
        use(ctx, player);

        ctx.assertBlockProperty(crop, BlockStateProperties.AGE_7, 7);
        ctx.assertBlockProperty(farmland, BlockStateProperties.MOISTURE, 7);
        assertCanLevel(ctx, player, 4);
        ctx.succeed();
    }

    // A non growable crop with no farmland below does nothing, no level is used
    public static void interactNoFarmland(GameTestHelper ctx) {
        BlockPos base = new BlockPos(2, 1, 2);
        BlockPos crop = base.above();
        ctx.setBlock(base, Blocks.STONE);
        ctx.setBlock(crop, Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7));

        Player player = player(ctx, GameType.SURVIVAL, 5);
        aim(ctx, player, crop);
        use(ctx, player);

        ctx.assertBlockProperty(crop, BlockStateProperties.AGE_7, 7);
        assertCanLevel(ctx, player, 5);
        ctx.succeed();
    }
}
