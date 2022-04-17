package dqu.additionaladditions.item;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class CopperPatinaItem extends BlockItem {
    public CopperPatinaItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!Config.getBool(ConfigValues.COPPER_PATINA)) return super.useOn(context);

        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        Optional<BlockState> optional = WeatheringCopper.getNext(state.getBlock()).map((block) -> block.withPropertiesOf(state));
        if (optional.isPresent() && player != null && !player.isShiftKeyDown()) {
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.USING_ITEM.trigger(serverPlayer, context.getItemInHand());
            }

            world.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.levelEvent(player, 3005, pos, 0);
            world.setBlock(pos, optional.get(), 11);
            context.getItemInHand().shrink(1);

            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }
}
