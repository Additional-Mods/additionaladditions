package dqu.additionaladditions.item;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WrenchItem extends Item {
    public WrenchItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!Config.get("Wrench")) { return ActionResult.FAIL; }
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (context.getPlayer() == null) return ActionResult.PASS;
        if (world.isClient()) return ActionResult.PASS;
        if (state.getBlock() instanceof ChestBlock || state.getBlock() instanceof BedBlock) return ActionResult.PASS;

        if (state.contains(Properties.FACING)) {
            world.setBlockState(pos, state.cycle(Properties.FACING));
            success(context.getStack(), world, pos, context.getPlayer(), context.getHand());
            return ActionResult.SUCCESS;
        }

        if (state.contains(Properties.HOPPER_FACING)) {
            BlockState newstate = state.cycle(Properties.HOPPER_FACING);
            world.setBlockState(pos, newstate);

            if (AdditionalAdditions.lithiumInstalled && !world.isClient()) {
                /*
                * Lithium mod caches hopper's output and input inventories
                * Which causes an issue where the hopper keeps transferring to the old location
                * This replaces the block entity, which fixes that.
                */
                HopperBlockEntity hopperBlockEntity = (HopperBlockEntity) world.getBlockEntity(pos);
                NbtCompound nbt = hopperBlockEntity.writeNbt(new NbtCompound());
                world.removeBlockEntity(pos);
                HopperBlockEntity blockEntity = new HopperBlockEntity(pos, newstate);
                blockEntity.readNbt(nbt);
                world.addBlockEntity(blockEntity);
            }

            success(context.getStack(), world, pos, context.getPlayer(), context.getHand());
            return ActionResult.SUCCESS;
        }

        if (state.contains(Properties.HORIZONTAL_FACING)) {
            world.setBlockState(pos, state.cycle(Properties.HORIZONTAL_FACING));
            success(context.getStack(), world, pos, context.getPlayer(), context.getHand());
            return ActionResult.SUCCESS;
        }

        if (state.contains(Properties.AXIS)) {
            world.setBlockState(pos, state.cycle(Properties.AXIS));
            success(context.getStack(), world, pos, context.getPlayer(), context.getHand());
            return ActionResult.SUCCESS;
        }

        if (state.contains(Properties.HORIZONTAL_AXIS)) {
            world.setBlockState(pos, state.cycle(Properties.HORIZONTAL_AXIS));
            success(context.getStack(), world, pos, context.getPlayer(), context.getHand());
            return ActionResult.SUCCESS;
        }

        if (state.getBlock() instanceof SlabBlock) {
            world.setBlockState(pos, state.cycle(Properties.SLAB_TYPE));
            BlockState newState = state;
            if (state.get(Properties.SLAB_TYPE).equals(SlabType.DOUBLE)) return ActionResult.PASS;
            if (state.get(Properties.SLAB_TYPE).equals(SlabType.BOTTOM)) newState = state.with(Properties.SLAB_TYPE, SlabType.TOP);
            if (state.get(Properties.SLAB_TYPE).equals(SlabType.TOP)) newState = state.with(Properties.SLAB_TYPE, SlabType.BOTTOM);
            world.setBlockState(pos, newState);
            success(context.getStack(), world, pos, context.getPlayer(), context.getHand());
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public void dispenserUse(World world, BlockPos pos, BlockState state, ItemStack stack) {
        if (state.getBlock() instanceof ChestBlock) return;
        if (state.contains(Properties.FACING)) {
            world.setBlockState(pos, state.cycle(Properties.FACING));
            success(stack, world, pos);
            return;
        }
        if (state.contains(Properties.HOPPER_FACING)) {
            world.setBlockState(pos, state.cycle(Properties.HOPPER_FACING));
            success(stack, world, pos);
            return;
        }
        if (state.contains(Properties.HORIZONTAL_FACING)) {
            world.setBlockState(pos, state.cycle(Properties.HORIZONTAL_FACING));
            success(stack, world, pos);
        }
        if (state.contains(Properties.AXIS)) {
            world.setBlockState(pos, state.cycle(Properties.AXIS));
            success(stack, world, pos);
        }
        if (state.contains(Properties.HORIZONTAL_AXIS)) {
            world.setBlockState(pos, state.cycle(Properties.HORIZONTAL_AXIS));
            success(stack, world, pos);
        }

        if (state.getBlock() instanceof SlabBlock) {
            world.setBlockState(pos, state.cycle(Properties.SLAB_TYPE));
            BlockState news = null;
            if (state.get(Properties.SLAB_TYPE) == SlabType.BOTTOM) news = state.with(Properties.SLAB_TYPE, SlabType.TOP);
            if (state.get(Properties.SLAB_TYPE) == SlabType.TOP) news = state.with(Properties.SLAB_TYPE, SlabType.BOTTOM);
            if (news != null) {
                world.setBlockState(pos, news);
                success(stack, world, pos);
            }
        }
    }

    public void success(ItemStack stack, World world, BlockPos pos) {
        if (world.isClient()) return;
        if (stack.damage(1, world.random, null)) stack.decrement(1);
        world.playSound(null, pos, SoundEvents.ITEM_SPYGLASS_USE, SoundCategory.AMBIENT, 2.0F, 1.0F);
    }

    public void success(ItemStack stack, World world, BlockPos pos, PlayerEntity player, Hand hand) {
        if (world.isClient()) return;
        if (!player.isCreative()) {
            stack.damage(1, player, (PlayerEntity -> {
                player.sendToolBreakStatus(hand);
            }));
        }
        world.playSound(null, pos, SoundEvents.ITEM_SPYGLASS_USE, SoundCategory.AMBIENT, 2.0F, 1.0F); }

}
