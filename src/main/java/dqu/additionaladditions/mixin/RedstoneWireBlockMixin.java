package dqu.additionaladditions.mixin;

import dqu.additionaladditions.registry.AdditionalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireBlockMixin {
    @Inject(method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", at = @At("RETURN"), cancellable = true)
    private static void connectsTo(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (state.isOf(AdditionalBlocks.COPPER_PATINA)) {
            callbackInfo.setReturnValue(false);
        }
    }

    @Redirect(method = "getReceivedRedstonePower", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getReceivedRedstonePower(Lnet/minecraft/util/math/BlockPos;)I"))
    private int getReplacedReceivedRedstonePower(World world, BlockPos pos) {
        int i = 0;
        Direction[] var3 = Direction.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            BlockPos offsetted = pos.offset(direction);
            BlockState state = world.getBlockState(offsetted);
            int j = 0;
            if (!state.isOf(AdditionalBlocks.COPPER_PATINA)) {
                j = world.getEmittedRedstonePower(offsetted, direction);
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
