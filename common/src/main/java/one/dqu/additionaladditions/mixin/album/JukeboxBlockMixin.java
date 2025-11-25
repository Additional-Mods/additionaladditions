package one.dqu.additionaladditions.mixin.album;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import one.dqu.additionaladditions.registry.AAItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JukeboxBlock.class)
public class JukeboxBlockMixin {
    @Inject(method = "useItemOn", at = @At("RETURN"), cancellable = true)
    private void useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<ItemInteractionResult> cir) {
        if (cir.getReturnValue() != ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION) {
            return;
        }

        if (!itemStack.is(AAItems.ALBUM.get())) {
            return;
        }

        cir.setReturnValue(ItemInteractionResult.sidedSuccess(level.isClientSide));

        if (blockState.is(Blocks.JUKEBOX) && !blockState.getValue(JukeboxBlock.HAS_RECORD)) {
            if (level.isClientSide) {
                return;
            }

            ItemStack itemStack2 = itemStack.consumeAndReturn(1, player);
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof JukeboxBlockEntity jukebox) {
                jukebox.setTheItem(itemStack2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, blockState));
            }
            player.awardStat(Stats.PLAY_RECORD);
        }
    }
}
