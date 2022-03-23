package dqu.additionaladditions.mixin;

import dqu.additionaladditions.registry.AdditionalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
public class RedstoneWireBlockMixin {
    @Inject(method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z", at = @At("RETURN"), cancellable = true)
    private static void connectsTo(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (state.is(AdditionalBlocks.COPPER_PATINA)) {
            callbackInfo.setReturnValue(false);
        }
    }

    @Redirect(method = "calculateTargetStrength", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBestNeighborSignal(Lnet/minecraft/core/BlockPos;)I"))
    private int getReplacedReceivedRedstonePower(Level world, BlockPos pos) {
        int i = 0;
        Direction[] var3 = Direction.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            BlockPos offsetted = pos.relative(direction);
            BlockState state = world.getBlockState(offsetted);
            int j = 0;
            if (!state.is(AdditionalBlocks.COPPER_PATINA)) {
                j = world.getSignal(offsetted, direction);
            }
            if (j >= 15) {
                return 15;
            }
            if (j > i) {
                i = j;
            }
        }

        return i;
    }
}
