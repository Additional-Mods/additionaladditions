package one.dqu.additionaladditions.util.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import one.dqu.additionaladditions.util.FluidHelper;

public class FluidHelperImpl {
    public static FluidHelper.Transaction modded(Player player, BlockPos pos, Direction side, int amount, Fluid fluid) {
        Level level = player.level();

        ResourceHandler<FluidResource> handler = level.getCapability(Capabilities.Fluid.BLOCK, pos, side);
        if (handler == null) {
            return new FluidHelper.Transaction(0, (committed) -> {});
        }

        FluidResource resource = FluidResource.of(fluid);

        int available;
        try (var tx = Transaction.openRoot()) {
            available = handler.extract(resource, amount, tx);
        }

        return new FluidHelper.Transaction(available, (committed) -> {
            try (var tx = Transaction.openRoot()) {
                handler.extract(resource, committed, tx);
                tx.commit();
            }
            fluid.getPickupSound().ifPresent((sound) -> level.playSound(null, pos, sound, SoundSource.BLOCKS));
        });
    }
}
