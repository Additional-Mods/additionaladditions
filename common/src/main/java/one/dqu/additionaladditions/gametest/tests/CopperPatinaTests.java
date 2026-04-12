package one.dqu.additionaladditions.gametest.tests;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import one.dqu.additionaladditions.registry.AABlocks;

public class CopperPatinaTests {
    public static void decay(GameTestHelper ctx) {
        BlockPos powerSource = new BlockPos(0, 1, 0);
        BlockPos patina1 = new BlockPos(1, 1, 0);
        BlockPos patina2 = new BlockPos(2, 1, 0);
        BlockPos patina3 = new BlockPos(3, 1, 0);

        ctx.pulseRedstone(powerSource, 10);

        ctx.setBlock(patina1, AABlocks.COPPER_PATINA.get());
        ctx.setBlock(patina2, AABlocks.COPPER_PATINA.get());
        ctx.setBlock(patina3, AABlocks.COPPER_PATINA.get());

        ctx.runAtTickTime(5, () -> {
            ctx.assertBlockProperty(patina1, BlockStateProperties.POWER, power -> power == 15, Component.literal("First patina should have power 15"));
            ctx.assertBlockProperty(patina2, BlockStateProperties.POWER, power -> power == 14, Component.literal("Second patina should have power 14"));
            ctx.assertBlockProperty(patina3, BlockStateProperties.POWER, power -> power == 13, Component.literal("Third patina should have power 13"));

            ctx.succeed();
        });
    }

    public static void interferenceCrossing(GameTestHelper ctx) {
        for (int z = 0; z < 5; z++) {
            ctx.setBlock(new BlockPos(2, 1, z), AABlocks.COPPER_PATINA.get());
        }
        for (int x = 0; x < 5; x++) {
            ctx.setBlock(new BlockPos(x, 1, 2), Blocks.REDSTONE_WIRE);
        }

        BlockPos redstoneTarget = new BlockPos(4, 2, 2);
        BlockPos patinaTarget = new BlockPos(2, 2, 4);
        ctx.setBlock(redstoneTarget, Blocks.REDSTONE_LAMP);
        ctx.setBlock(redstoneTarget.below(), Blocks.STONE);
        ctx.setBlock(patinaTarget, Blocks.REDSTONE_LAMP);
        ctx.setBlock(patinaTarget.below(), Blocks.STONE);

        ctx.runAtTickTime(5, () -> {
            ctx.pulseRedstone(new BlockPos(0, 1, 2), 2);
            ctx.assertBlockProperty(redstoneTarget, BlockStateProperties.LIT, state -> state, Component.literal("Redstone lamp should be lit"));
            ctx.assertBlockProperty(patinaTarget, BlockStateProperties.LIT, state -> !state, Component.literal("Patina lamp should not be lit"));
            ctx.succeed();
        });
    }

    public static void interferenceDirectTransition(GameTestHelper ctx) {
        BlockPos start = new BlockPos(0, 1, 0);

        for (int x = 0; x < 2; x++) {
            ctx.setBlock(new BlockPos(x, 1, 0), Blocks.REDSTONE_WIRE);
        }
        for (int x = 2; x < 4; x++) {
            ctx.setBlock(new BlockPos(x, 1, 0), AABlocks.COPPER_PATINA.get());
        }

        BlockPos lampPos = new BlockPos(4, 2, 0);
        ctx.setBlock(lampPos, Blocks.REDSTONE_LAMP);
        ctx.setBlock(lampPos.below(), Blocks.STONE);

        ctx.pulseRedstone(start, 10);

        ctx.runAtTickTime(5, () -> {
            ctx.assertBlockProperty(lampPos, BlockStateProperties.LIT, state -> !state, Component.literal("Lamp should not be lit"));
            ctx.succeed();
        });
    }

    public static void interferenceSoftPower(GameTestHelper ctx) {
        BlockPos redstonePos = new BlockPos(1, 1, 0);
        BlockPos stonePos = new BlockPos(2, 1, 0);
        BlockPos patinaPos = new BlockPos(3, 1, 0);

        ctx.setBlock(redstonePos, Blocks.REDSTONE_WIRE);
        ctx.setBlock(stonePos, Blocks.STONE);
        ctx.setBlock(patinaPos, AABlocks.COPPER_PATINA.get());

        ctx.pulseRedstone(new BlockPos(0, 1, 0), 5);
        ctx.runAtTickTime(4, () -> {
            ctx.assertBlockProperty(patinaPos, BlockStateProperties.POWER, power -> power == 0, Component.literal("Copper patina should not be powered from redstone through stone"));
        });

        ctx.runAtTickTime(10, () -> {
            ctx.assertBlockProperty(redstonePos, BlockStateProperties.POWER, power -> power == 0, Component.literal("Redstone wire should be unpowered after pulse"));
            ctx.pulseRedstone(new BlockPos(4, 1, 0), 5);
        });

        ctx.runAtTickTime(14, () -> {
            ctx.assertBlockProperty(redstonePos, BlockStateProperties.POWER, power -> power == 0, Component.literal("Redstone wire should not be powered from patina through stone"));
        });

        ctx.runAtTickTime(20, () -> {
            ctx.assertBlockProperty(patinaPos, BlockStateProperties.POWER, power -> power == 0, Component.literal("Copper patina should be unpowered after pulse"));
            ctx.succeed();
        });
    }

    public static void transmit(GameTestHelper ctx) {
        BlockPos lampOne = new BlockPos(0, 2, 0);
        BlockPos lampTwo = new BlockPos(1, 2, 0);
        BlockPos pulseOne = new BlockPos(0, 1, 4);
        BlockPos pulseTwo = new BlockPos(1, 1, 4);

        ctx.setBlock(lampOne, Blocks.REDSTONE_LAMP);
        ctx.setBlock(lampOne.below(), Blocks.STONE);
        ctx.setBlock(lampTwo, Blocks.REDSTONE_LAMP);
        ctx.setBlock(lampTwo.below(), Blocks.STONE);

        for (int i = 1; i < 4; i++) {
            ctx.setBlock(new BlockPos(0, 1, i), Blocks.REDSTONE_WIRE);
            ctx.setBlock(new BlockPos(1, 1, i), AABlocks.COPPER_PATINA.get());
        }

        ctx.startSequence()
                .thenIdle(5)
                .thenExecute(() -> {
                    ctx.pulseRedstone(pulseOne, 2);
                    ctx.assertBlockProperty(lampOne, BlockStateProperties.LIT, state -> state, Component.literal("Lamp one should be lit"));
                    ctx.assertBlockProperty(lampTwo, BlockStateProperties.LIT, state -> !state, Component.literal("Lamp two should be unlit"));
                })
                .thenIdle(10)
                .thenExecute(() -> {
                    ctx.pulseRedstone(pulseTwo, 2);
                    ctx.assertBlockProperty(lampOne, BlockStateProperties.LIT, state -> !state, Component.literal("Lamp one should be unlit"));
                    ctx.assertBlockProperty(lampTwo, BlockStateProperties.LIT, state -> state, Component.literal("Lamp two should be lit"));
                })
                .thenIdle(10)
                .thenExecute(() -> {
                    ctx.assertBlockProperty(lampOne, BlockStateProperties.LIT, state -> !state, Component.literal("Lamp one should be unlit"));
                    ctx.assertBlockProperty(lampTwo, BlockStateProperties.LIT, state -> !state, Component.literal("Lamp two should be unlit"));
                })
                .thenSucceed();
    }

    public static void scrape(GameTestHelper ctx) {
        BlockPos copper = new BlockPos(2, 1, 2);

        ctx.setBlock(copper, Blocks.OXIDIZED_COPPER);

        Player player = ctx.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, Items.IRON_AXE.getDefaultInstance());

        ctx.useBlock(copper, player);
        ctx.assertBlockNotPresent(Blocks.OXIDIZED_COPPER, copper);
        ctx.assertBlockPresent(Blocks.WEATHERED_COPPER, copper);
        ctx.assertItemEntityPresent(AABlocks.COPPER_PATINA.get().asItem());
        ctx.succeed();
    }

    public static void oxidize(GameTestHelper ctx) {
        BlockPos copper = new BlockPos(2, 1, 2);

        ctx.setBlock(copper, Blocks.COPPER_BLOCK);

        Player player = ctx.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, AABlocks.COPPER_PATINA.get().asItem().getDefaultInstance());

        ctx.useBlock(copper, player);
        ctx.assertBlockNotPresent(Blocks.COPPER_BLOCK, copper);
        ctx.assertBlockPresent(Blocks.EXPOSED_COPPER, copper);
        ctx.succeed();
    }
}
