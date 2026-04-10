package one.dqu.additionaladditions.util.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.ResourceHandler;
import one.dqu.additionaladditions.util.FluidHelper;

public class FluidHelperImpl {
    public static FluidHelper.Transaction modded(Player player, BlockPos pos, Direction side, int amount, Fluid fluid) {
        Level level = player.level();

        ResourceHandler<FluidResource> handler = level.getCapability(Capabilities.Fluid.BLOCK, pos, side);
        if (handler == null) {
            return new FluidHelper.Transaction(0, (committed) -> {});
        }
        IFluidHandler capability = IFluidHandler.of(handler);

        FluidStack fluidStack = new FluidStack(fluid, amount);
        FluidStack drained = capability.drain(fluidStack, IFluidHandler.FluidAction.SIMULATE);

        return new FluidHelper.Transaction(drained.getAmount(), (committed) -> {
            FluidStack toDrain = new FluidStack(fluid, committed);
            toDrain.getFluid().getPickupSound().ifPresent((sound) -> level.playSound(null, pos, sound, net.minecraft.sounds.SoundSource.BLOCKS));
            capability.drain(toDrain, IFluidHandler.FluidAction.EXECUTE);
        });
    }
}
