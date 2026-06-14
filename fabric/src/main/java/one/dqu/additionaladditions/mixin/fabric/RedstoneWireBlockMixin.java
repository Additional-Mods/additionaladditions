package one.dqu.additionaladditions.mixin.fabric;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import one.dqu.additionaladditions.registry.AABlocks;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RedStoneWireBlock.class)
public class RedstoneWireBlockMixin {
    @Unique
    @Nullable
    private Boolean additionaladditions$shouldConnect(BlockState state) {
        Block self = (Block) (Object) this;
        boolean isRedstoneWire = self == Blocks.REDSTONE_WIRE;
        boolean isCopperPatina = self == AABlocks.COPPER_PATINA.get();
        if (!isRedstoneWire && !isCopperPatina) return null;

        if (state.is(AABlocks.COPPER_PATINA.get())) return isCopperPatina;
        if (state.is(Blocks.REDSTONE_WIRE)) return isRedstoneWire;

        return null;
    }

    @WrapOperation(
            method = "getConnectingSide(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Z)Lnet/minecraft/world/level/block/state/properties/RedstoneSide;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/RedStoneWireBlock;shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z"
            )
    )
    private boolean sideCheck(BlockState blockState, Direction direction, Operation<Boolean> original) {
        Boolean shouldConnect = additionaladditions$shouldConnect(blockState);
        return shouldConnect != null ? shouldConnect : original.call(blockState, direction);
    }

    @WrapOperation(
            method = "getConnectingSide(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Z)Lnet/minecraft/world/level/block/state/properties/RedstoneSide;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/RedStoneWireBlock;shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;)Z"
            )
    )
    private boolean verticalCheck(BlockState blockState, Operation<Boolean> original) {
        Boolean shouldConnect = additionaladditions$shouldConnect(blockState);
        return shouldConnect != null ? shouldConnect : original.call(blockState);
    }
}
