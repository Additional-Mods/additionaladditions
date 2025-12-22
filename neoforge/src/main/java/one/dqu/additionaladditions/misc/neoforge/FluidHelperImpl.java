package one.dqu.additionaladditions.misc.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import one.dqu.additionaladditions.misc.FluidHelper;

public class FluidHelperImpl {
    public static FluidHelper.Transaction modded(Player player, BlockPos pos, Direction side, int amount, Fluid fluid) {
        Level level = player.level();

        var capability = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, side);
        if (capability == null) {
            return new FluidHelper.Transaction(0, (committed) -> {});
        }

        FluidStack fluidStack = new FluidStack(fluid, amount);
        FluidStack drained = capability.drain(fluidStack, IFluidHandler.FluidAction.SIMULATE);

        return new FluidHelper.Transaction(drained.getAmount(), (committed) -> {
            FluidStack toDrain = new FluidStack(fluid, committed);
            toDrain.getFluid().getPickupSound().ifPresent((sound) -> level.playSound(null, pos, sound, net.minecraft.sounds.SoundSource.BLOCKS));
            capability.drain(toDrain, IFluidHandler.FluidAction.EXECUTE);
        });
    }
}
