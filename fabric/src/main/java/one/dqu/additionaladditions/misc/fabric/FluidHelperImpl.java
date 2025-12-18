package one.dqu.additionaladditions.misc.fabric;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import one.dqu.additionaladditions.misc.FluidHelper;

public class FluidHelperImpl {
    public static FluidHelper.Transaction modded(Player player, BlockPos pos, Direction side, int amount, Fluid fluid) {
        Level level = player.level();

        Storage<FluidVariant> storage = FluidStorage.SIDED.find(level, pos, side);
        if (storage == null) {
            return new FluidHelper.Transaction(0, (committed) -> {});
        }

        FluidVariant fluidVariant = FluidVariant.of(fluid);

        long extracted;
        try (Transaction transaction = Transaction.openOuter()) {
            extracted = storage.extract(fluidVariant, amount * 81L, transaction);
            transaction.abort();
        }

        return new FluidHelper.Transaction((int)(extracted / 81L), (committed) -> {
            fluid.getPickupSound().ifPresent((sound) -> level.playSound(player, pos, sound, net.minecraft.sounds.SoundSource.BLOCKS));

            try (Transaction transaction = Transaction.openOuter()) {
                storage.extract(fluidVariant, committed * 81L, transaction);
                transaction.commit();
            }
        });
    }
}
