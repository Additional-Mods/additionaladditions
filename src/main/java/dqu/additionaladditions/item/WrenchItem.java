package dqu.additionaladditions.item;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrenchItem extends Item {
    public WrenchItem(Properties settings) {
        super(settings);
    }

    private boolean tryPlacing(BlockPos pos, BlockState state, Level world, ItemStack stack, Optional<Player> player, Optional<InteractionHand> hand) {
        if (state.canSurvive(world, pos)) {
            world.setBlockAndUpdate(pos, state);

            if (world.isClientSide()) {
                return true;
            }

            player.ifPresentOrElse((pl) -> {
                if (!pl.isCreative()) {
                    stack.hurtAndBreak(1, pl, (PlayerEntity -> pl.broadcastBreakEvent(hand.get())));
                }
            }, () -> {
                if (stack.hurt(1, world.random, null)) {
                    stack.shrink(1);
                }
            });

            world.playSound(null, pos, SoundEvents.SPYGLASS_USE, SoundSource.AMBIENT, 2.0F, 1.0F);

            return true;
        }
        return false;
    }

    private InteractionResult rotate(BlockPos pos, BlockState state, Level world, ItemStack stack, Optional<Player> player, Optional<InteractionHand> hand) {
        if (!Config.getBool(ConfigValues.WRENCH)) { return InteractionResult.FAIL; }

        if (world.isClientSide()) return InteractionResult.PASS;
        if (state.getBlock() instanceof ChestBlock || state.getBlock() instanceof BedBlock) return InteractionResult.PASS;

        if (state.hasProperty(BlockStateProperties.FACING)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.FACING), world, stack, player, hand)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (state.hasProperty(BlockStateProperties.FACING_HOPPER)) {
            BlockState newstate = state.cycle(BlockStateProperties.FACING_HOPPER);
            if (tryPlacing(pos, newstate, world, stack, player, hand)) {
                if (FabricLoader.getInstance().isModLoaded("lithium") && !world.isClientSide()) {
                    /*
                     * Lithium mod caches hopper's output and input inventories
                     * Which causes an issue where the hopper keeps transferring to the old location
                     * This replaces the block entity, which fixes that.
                     */
                    HopperBlockEntity hopperBlockEntity = (HopperBlockEntity) world.getBlockEntity(pos);
                    CompoundTag nbt = hopperBlockEntity.saveWithoutMetadata();
                    world.removeBlockEntity(pos);
                    HopperBlockEntity blockEntity = new HopperBlockEntity(pos, newstate);
                    blockEntity.load(nbt);
                    world.setBlockEntity(blockEntity);
                }

                return InteractionResult.SUCCESS;
            }
        }

        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.HORIZONTAL_FACING), world, stack, player, hand)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (state.hasProperty(BlockStateProperties.AXIS)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.AXIS), world, stack, player, hand)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (state.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.HORIZONTAL_AXIS), world, stack, player, hand)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (state.getBlock() instanceof SlabBlock) {
            BlockState newState = state;
            if (state.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.DOUBLE)) {
                return InteractionResult.PASS;
            }
            if (state.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.BOTTOM)) {
                newState = state.setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP);
            }
            if (state.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.TOP)) {
                newState = state.setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM);
            }

            if (tryPlacing(pos, newState, world, stack, player, hand)) {
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        return rotate(
                context.getClickedPos(),
                context.getLevel().getBlockState(context.getClickedPos()),
                context.getLevel(),
                context.getItemInHand(),
                Optional.ofNullable(context.getPlayer()),
                Optional.of(context.getHand())
        );
    }

    public void dispenserUse(Level world, BlockPos pos, BlockState state, ItemStack stack) {
        rotate(pos, state, world, stack, Optional.empty(), Optional.empty());
    }
}