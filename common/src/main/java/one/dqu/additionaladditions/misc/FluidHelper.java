package one.dqu.additionaladditions.misc;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

public class FluidHelper {
    public static Transaction transaction(Player player, BlockPos pos, Direction side, int amount, Fluid fluid) {
        Level level = player.level();
        BlockState state = level.getBlockState(pos);

        // liquid block
        if (state.getBlock() instanceof BucketPickup bucketPickup && state.getFluidState().is(fluid)) {
            boolean isSource = state.getFluidState().isSource();

            if (amount >= 1000 && !isSource) return new Transaction(0, (committed) -> {});

            return new Transaction(Math.min(amount, 1000), (committed) -> {
                fluid.getPickupSound().ifPresent((sound) -> level.playSound(player, pos, sound, SoundSource.BLOCKS));

                if (committed >= 1000 && !level.isClientSide()) {
                    bucketPickup.pickupBlock(player, level, pos, state);
                }
            });
        }

        // cauldron
        if (fluid == Fluids.WATER && state.is(Blocks.WATER_CAULDRON)) {
            int layers = state.getValue(LayeredCauldronBlock.LEVEL);

            int needed = Math.ceilDiv(amount * LayeredCauldronBlock.MAX_FILL_LEVEL, 1000);
            int finalAmount = (Math.min(needed, layers) * 1000) / LayeredCauldronBlock.MAX_FILL_LEVEL;

            return new Transaction(finalAmount, (commited) -> {
                fluid.getPickupSound().ifPresent((sound) -> level.playSound(player, pos, sound, SoundSource.BLOCKS));

                int toTake = Math.ceilDiv(commited * LayeredCauldronBlock.MAX_FILL_LEVEL, 1000);

                if (!level.isClientSide()) {
                    int newLayers = layers - Math.min(toTake, layers);

                    if (newLayers == 0) {
                        level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                    } else {
                        level.setBlockAndUpdate(pos, state.setValue(LayeredCauldronBlock.LEVEL, newLayers));
                    }
                }
            });
        }

        if (fluid == Fluids.LAVA && state.is(Blocks.LAVA_CAULDRON)) {
            if (amount < 1000) return new Transaction(0, (committed) -> {});

            return new Transaction(1000, (committed) -> {
                if (committed < 1000) return;

                fluid.getPickupSound().ifPresent((sound) -> level.playSound(player, pos, sound, SoundSource.BLOCKS));
                if (!level.isClientSide()) {
                    level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                }
            });
        }

        // modded
        return modded(player, pos, side, amount, fluid);
    }

    @ExpectPlatform
    private static Transaction modded(Player player, BlockPos pos, Direction side, int amount, Fluid fluid) {
        throw new AssertionError();
    }

    public record Transaction(int amount, IntConsumer commitRunnable) {
        public int commit() {
            if (amount > 0) {
                commitRunnable.accept(amount);
            }
            return amount;
        }

        public boolean commitIf(IntPredicate predicate) {
            if (amount > 0 && predicate.test(amount)) {
                commitRunnable.accept(amount);
                return true;
            }
            return false;
        }

        public int commitAmount(int amount) {
            if (amount > 0 && this.amount >= amount) {
                commitRunnable.accept(amount);
                return amount;
            }
            return 0;
        }

        public int commitAmountIf(int amount, IntPredicate predicate) {
            if (amount > 0 && this.amount >= amount && predicate.test(this.amount)) {
                commitRunnable.accept(amount);
                return amount;
            }
            return 0;
        }
    }
}
