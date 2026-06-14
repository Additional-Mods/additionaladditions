package one.dqu.additionaladditions.mixin.neoforge;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import one.dqu.additionaladditions.registry.AABlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RedStoneWireBlock.class)
public class RedstoneWireBlockMixin {
    @WrapOperation(
            method = "getConnectingSide(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Z)Lnet/minecraft/world/level/block/state/properties/RedstoneSide;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;canRedstoneConnectTo(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z"
            )
    )
    private boolean canRedstoneConnectTo(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction, Operation<Boolean> original) {
        Block self = (Block) (Object) this;
        boolean isRedstoneWire = self == Blocks.REDSTONE_WIRE;
        boolean isCopperPatina = self == AABlocks.COPPER_PATINA.get();

        if (isRedstoneWire || isCopperPatina) {
            if (blockState.is(AABlocks.COPPER_PATINA.get())) return isCopperPatina;
            if (blockState.is(Blocks.REDSTONE_WIRE)) return isRedstoneWire;
        }

        return original.call(blockState, blockGetter, blockPos, direction);
    }
}
