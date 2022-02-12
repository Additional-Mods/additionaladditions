package dqu.additionaladditions.block;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.value.ConfigValues;
import dqu.additionaladditions.registry.AdditionalBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;

import java.util.Random;

/** @noinspection deprecation*/
public class RopeBlock extends Block {
    public static final VoxelShape shape = Block.createCuboidShape(6, 0, 6,10, 16, 10);
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty EAST = BooleanProperty.of("east");

    public RopeBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(NORTH);
        stateManager.add(EAST);
        stateManager.add(SOUTH);
        stateManager.add(WEST);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (!Config.getBool(ConfigValues.ROPES)) return false;
        BlockState up = world.getBlockState(pos.offset(Direction.UP));
        if(up.isOf(this) || up.isSideSolidFullSquare(world, pos.offset(Direction.UP), Direction.DOWN)) return true;

        BlockState north = world.getBlockState(pos.offset(Direction.NORTH));
        BlockState east = world.getBlockState(pos.offset(Direction.EAST));
        BlockState south = world.getBlockState(pos.offset(Direction.SOUTH));
        BlockState west = world.getBlockState(pos.offset(Direction.WEST));

        if(north.isSideSolidFullSquare(world, pos.offset(Direction.NORTH), Direction.SOUTH)) return true;
        if(east.isSideSolidFullSquare(world, pos.offset(Direction.EAST), Direction.WEST)) return true;
        if(south.isSideSolidFullSquare(world, pos.offset(Direction.SOUTH), Direction.NORTH)) return true;
        if(west.isSideSolidFullSquare(world, pos.offset(Direction.WEST), Direction.EAST)) return true;

        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return shape;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient()) {
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(this, pos));
        }
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos npos = pos.offset(Direction.NORTH);
        BlockPos epos = pos.offset(Direction.EAST);
        BlockPos spos = pos.offset(Direction.SOUTH);
        BlockPos wpos = pos.offset(Direction.WEST);

        BlockState north = world.getBlockState(npos);
        BlockState east = world.getBlockState(epos);
        BlockState south = world.getBlockState(spos);
        BlockState west = world.getBlockState(wpos);

        boolean n = false;
        boolean e = false;
        boolean s = false;
        boolean w = false;

        if (north.isSideSolidFullSquare(world, npos, Direction.SOUTH)) n = true;
        if (east.isSideSolidFullSquare(world, epos, Direction.WEST)) e = true;
        if (south.isSideSolidFullSquare(world, spos, Direction.NORTH)) s = true;
        if (west.isSideSolidFullSquare(world, wpos, Direction.EAST)) w = true;

        world.setBlockState(pos, state.with(NORTH, n).with(EAST, e).with(SOUTH, s).with(WEST, w));

        BlockPos up = pos.offset(Direction.UP);
        BlockState bup = world.getBlockState(up);

        if (!world.getBlockState(up).isOf(this) && (!n && !e && !s && !w) && !bup.isSideSolidFullSquare(world, up, Direction.DOWN)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!world.isClient()) {
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(this, pos));
        }
        return state;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!state.isOf(this)) return ActionResult.PASS;
        if (!player.getMainHandStack().isOf(Item.fromBlock(AdditionalBlocks.ROPE_BLOCK))) return ActionResult.PASS;
        BlockPos down = pos.offset(Direction.DOWN);
        BlockState statedown = world.getBlockState(down);
        if (statedown.isOf(AdditionalBlocks.ROPE_BLOCK)) {
            return statedown.getBlock().onUse(statedown, world, down, player, hand, hit);
        } else if (statedown.isAir() && !world.isOutOfHeightLimit(down.getY())) {
            world.setBlockState(down, state);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) player.getMainHandStack().decrement(1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

}
