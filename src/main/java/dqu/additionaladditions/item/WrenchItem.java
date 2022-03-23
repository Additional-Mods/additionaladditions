package dqu.additionaladditions.item;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
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

public class WrenchItem extends Item {
    public WrenchItem(Properties settings) {
        super(settings);
    }

    private boolean tryPlacing(BlockPos pos, BlockState state, Level world) {
        if (state.canSurvive(world, pos)) {
            world.setBlockAndUpdate(pos, state);
            return true;
        }
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!Config.getBool(ConfigValues.WRENCH)) { return InteractionResult.FAIL; }
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        if (context.getPlayer() == null) return InteractionResult.PASS;
        if (world.isClientSide()) return InteractionResult.PASS;
        if (state.getBlock() instanceof ChestBlock || state.getBlock() instanceof BedBlock) return InteractionResult.PASS;

        if (state.hasProperty(BlockStateProperties.FACING)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.FACING), world)) {
                success(context.getItemInHand(), world, pos, context.getPlayer(), context.getHand());
                return InteractionResult.SUCCESS;
            }
        }

        if (state.hasProperty(BlockStateProperties.FACING_HOPPER)) {
            BlockState newstate = state.cycle(BlockStateProperties.FACING_HOPPER);
            world.setBlockAndUpdate(pos, newstate);

            if (AdditionalAdditions.lithiumInstalled && !world.isClientSide()) {
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

            success(context.getItemInHand(), world, pos, context.getPlayer(), context.getHand());
            return InteractionResult.SUCCESS;
        }

        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.HORIZONTAL_FACING), world)) {
                success(context.getItemInHand(), world, pos, context.getPlayer(), context.getHand());
                return InteractionResult.SUCCESS;
            }
        }

        if (state.hasProperty(BlockStateProperties.AXIS)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.AXIS), world)) {
                success(context.getItemInHand(), world, pos, context.getPlayer(), context.getHand());
                return InteractionResult.SUCCESS;
            }
        }

        if (state.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.HORIZONTAL_AXIS), world)) {
                success(context.getItemInHand(), world, pos, context.getPlayer(), context.getHand());
                return InteractionResult.SUCCESS;
            }
        }

        if (state.getBlock() instanceof SlabBlock) {
            BlockState newState = state;
            if (state.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.DOUBLE)) return InteractionResult.PASS;
            if (state.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.BOTTOM)) newState = state.setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP);
            if (state.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.TOP)) newState = state.setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM);
            world.setBlockAndUpdate(pos, newState);
            success(context.getItemInHand(), world, pos, context.getPlayer(), context.getHand());
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public void dispenserUse(Level world, BlockPos pos, BlockState state, ItemStack stack) {
        if (state.getBlock() instanceof ChestBlock) return;
        if (state.hasProperty(BlockStateProperties.FACING)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.FACING), world)) {
                success(stack, world, pos);
            }
        }
        if (state.hasProperty(BlockStateProperties.FACING_HOPPER)) {
            BlockState newstate = state.cycle(BlockStateProperties.FACING_HOPPER);
            world.setBlockAndUpdate(pos, newstate);

            if (AdditionalAdditions.lithiumInstalled && !world.isClientSide()) {
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

            success(stack, world, pos);
        }
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.HORIZONTAL_FACING), world)) {
                success(stack, world, pos);
            }
        }
        if (state.hasProperty(BlockStateProperties.AXIS)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.AXIS), world)) {
                success(stack, world, pos);
            }
        }
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.HORIZONTAL_AXIS), world)) {
                success(stack, world, pos);
            }
        }

        if (state.getBlock() instanceof SlabBlock) {
            world.setBlockAndUpdate(pos, state.cycle(BlockStateProperties.SLAB_TYPE));
            BlockState news = null;
            if (state.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM) news = state.setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP);
            if (state.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.TOP) news = state.setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM);
            if (news != null) {
                world.setBlockAndUpdate(pos, news);
                success(stack, world, pos);
            }
        }
    }

    public void success(ItemStack stack, Level world, BlockPos pos) {
        if (world.isClientSide()) return;
        if (stack.hurt(1, world.random, null)) stack.shrink(1);
        world.playSound(null, pos, SoundEvents.SPYGLASS_USE, SoundSource.AMBIENT, 2.0F, 1.0F);
    }

    public void success(ItemStack stack, Level world, BlockPos pos, Player player, InteractionHand hand) {
        if (world.isClientSide()) return;
        if (!player.isCreative()) {
            stack.hurtAndBreak(1, player, (PlayerEntity -> {
                player.broadcastBreakEvent(hand);
            }));
        }
        world.playSound(null, pos, SoundEvents.SPYGLASS_USE, SoundSource.AMBIENT, 2.0F, 1.0F); }

}