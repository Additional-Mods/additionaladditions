package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import one.dqu.additionaladditions.registry.AABlocks;

/**
 * Tests that Copper Blocks can be scraped to obtain Copper Patina,
 * and that Copper Patina can be used to oxidize Copper Blocks.
 */
public class CopperPatinaWithCopperBlockTest {
    @GameTest(template = "additionaladditions:empty")
    public void scrape(GameTestHelper ctx) {
        BlockPos copper = new BlockPos(2, 2, 2);

        ctx.setBlock(copper, Blocks.OXIDIZED_COPPER);

        Player player = ctx.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, Items.IRON_AXE.getDefaultInstance());

        ctx.useBlock(copper, player);
        ctx.assertBlockNotPresent(Blocks.OXIDIZED_COPPER, copper);
        ctx.assertBlockPresent(Blocks.WEATHERED_COPPER, copper);
        ctx.assertItemEntityPresent(AABlocks.COPPER_PATINA.get().asItem());
        ctx.succeed();
    }

    @GameTest(template = "additionaladditions:empty")
    public void oxidize(GameTestHelper ctx) {
        BlockPos copper = new BlockPos(2, 2, 2);

        ctx.setBlock(copper, Blocks.COPPER_BLOCK);

        Player player = ctx.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, AABlocks.COPPER_PATINA.get().asItem().getDefaultInstance());

        ctx.useBlock(copper, player);
        ctx.assertBlockNotPresent(Blocks.COPPER_BLOCK, copper);
        ctx.assertBlockPresent(Blocks.EXPOSED_COPPER, copper);
        ctx.succeed();
    }
}
