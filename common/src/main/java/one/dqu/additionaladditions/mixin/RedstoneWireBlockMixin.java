package one.dqu.additionaladditions.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import one.dqu.additionaladditions.registry.AABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Prevents redstone wire and copper patina from connecting to each other.
 */
@Mixin(RedStoneWireBlock.class)
public abstract class RedstoneWireBlockMixin {
    @Shadow
    protected abstract boolean canSurviveOn(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState);

    @Inject(
            method = "getConnectingSide(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Z)Lnet/minecraft/world/level/block/state/properties/RedstoneSide;",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void getConnectingSide(BlockGetter blockGetter, BlockPos blockPos, Direction direction, boolean bl, CallbackInfoReturnable<RedstoneSide> cir) {
        Block self = (Block) (Object) this;
        boolean isCopperPatina = self == AABlocks.COPPER_PATINA.get();
        boolean isRedstoneWire = self == Blocks.REDSTONE_WIRE;
        if (!isCopperPatina && !isRedstoneWire) {
            return;
        }
        cir.setReturnValue(
                additionaladditions$getConnectingSide(blockGetter, blockPos, direction, bl, isCopperPatina)
        );
    }

    @Unique
    private RedstoneSide additionaladditions$getConnectingSide(BlockGetter blockGetter, BlockPos blockPos, Direction direction, boolean bl, boolean isCopperPatina) {
        BlockPos blockPos2 = blockPos.relative(direction);
        BlockState blockState = blockGetter.getBlockState(blockPos2);
        if (bl) {
            boolean bl2 = blockState.getBlock() instanceof TrapDoorBlock || this.canSurviveOn(blockGetter, blockPos2, blockState);
            if (bl2 && additionaladditions$shouldConnectTo(blockGetter.getBlockState(blockPos2.above()), null, isCopperPatina)) {
                if (blockState.isFaceSturdy(blockGetter, blockPos2, direction.getOpposite())) {
                    return RedstoneSide.UP;
                }

                return RedstoneSide.SIDE;
            }
        }

        return !additionaladditions$shouldConnectTo(blockState, direction, isCopperPatina) && (blockState.isRedstoneConductor(blockGetter, blockPos2) || !additionaladditions$shouldConnectTo(blockGetter.getBlockState(blockPos2.below()), null, isCopperPatina)) ? RedstoneSide.NONE : RedstoneSide.SIDE;
    }

    @Unique
    private static boolean additionaladditions$shouldConnectTo(BlockState blockState, @Nullable Direction direction, boolean isCopperPatina) {
        if (blockState.is(AABlocks.COPPER_PATINA.get())) {
            return isCopperPatina;
        } else if (blockState.is(Blocks.REDSTONE_WIRE)) {
            return !isCopperPatina;
        } else if (blockState.is(Blocks.REPEATER)) {
            Direction direction2 = blockState.getValue(RepeaterBlock.FACING);
            return direction2 == direction || direction2.getOpposite() == direction;
        } else if (blockState.is(Blocks.OBSERVER)) {
            return direction == blockState.getValue(ObserverBlock.FACING);
        } else {
            return blockState.isSignalSource() && direction != null;
        }
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
            boolean flag = isCopperPatina ? !offsetState.is(Blocks.REDSTONE_WIRE) : !offsetState.is(AABlocks.COPPER_PATINA.get());

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
