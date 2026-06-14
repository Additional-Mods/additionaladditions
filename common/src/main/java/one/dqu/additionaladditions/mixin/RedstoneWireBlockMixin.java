package one.dqu.additionaladditions.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import one.dqu.additionaladditions.registry.AABlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Prevents redstone wire and copper patina from connecting to each other.
 */
@Mixin(RedStoneWireBlock.class)
public abstract class RedstoneWireBlockMixin {
    @Inject(
            method = "calculateTargetStrength",
            at = @At("HEAD")
    )
    private void unsetShouldSignal(Level level, BlockPos blockPos, CallbackInfoReturnable<Integer> cir) {
        Block self = (Block) (Object) this;
        boolean isCopperPatina = self == AABlocks.COPPER_PATINA.get();
        boolean isRedstoneWire = self == Blocks.REDSTONE_WIRE;

        if (isRedstoneWire) AABlocks.COPPER_PATINA.get().shouldSignal = false;
        if (isCopperPatina) ((RedStoneWireBlock) Blocks.REDSTONE_WIRE).shouldSignal = false;
    }

    @Inject(
            method = "calculateTargetStrength",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getBestNeighborSignal(Lnet/minecraft/core/BlockPos;)I",
                    shift = At.Shift.AFTER
            )
    )
    private void setShouldSignal(Level level, BlockPos blockPos, CallbackInfoReturnable<Integer> cir) {
        Block self = (Block) (Object) this;
        boolean isCopperPatina = self == AABlocks.COPPER_PATINA.get();
        boolean isRedstoneWire = self == Blocks.REDSTONE_WIRE;

        if (isRedstoneWire) AABlocks.COPPER_PATINA.get().shouldSignal = true;
        if (isCopperPatina) ((RedStoneWireBlock) Blocks.REDSTONE_WIRE).shouldSignal = true;
    }

    @WrapOperation(
            method = "calculateTargetStrength",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getBestNeighborSignal(Lnet/minecraft/core/BlockPos;)I"
            )
    )
    private static int getBestNeighborSignal(Level level, BlockPos blockPos, Operation<Integer> original) {
        BlockState blockState = level.getBlockState(blockPos);
        boolean isCopperPatina = blockState.is(AABlocks.COPPER_PATINA.get());
        boolean isRedstoneWire = blockState.is(Blocks.REDSTONE_WIRE);

        if (!isCopperPatina && !isRedstoneWire) {
            return original.call(level, blockPos);
        }

        int i = 0;
        for (Direction direction : Direction.values()) {
            BlockState offsetState = level.getBlockState(blockPos.relative(direction));
            boolean flag = isCopperPatina
                    ? !offsetState.is(Blocks.REDSTONE_WIRE)
                    : !offsetState.is(AABlocks.COPPER_PATINA.get());

            int j = 0;
            if (flag) {
                j = level.getSignal(blockPos.relative(direction), direction);
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
