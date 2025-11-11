package dqu.additionaladditions.block;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;

/** @noinspection deprecation*/
public class RopeBlock extends Block {
    public static final VoxelShape shape = Block.box(6, 0, 6,10, 16, 10);

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty UP = BooleanProperty.create("up");

    public RopeBlock(Properties settings) {
        super(settings);
        registerDefaultState(getStateDefinition().any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(UP, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(NORTH);
        stateManager.add(EAST);
        stateManager.add(SOUTH);
        stateManager.add(WEST);
        stateManager.add(UP);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (!Config.getBool(ConfigValues.ROPES)) return false;

        BlockState up = world.getBlockState(pos.relative(Direction.UP));

        if(up.is(this) || up.isFaceSturdy(world, pos.relative(Direction.UP), Direction.DOWN)) return true;

        BlockState north = world.getBlockState(pos.relative(Direction.NORTH));
        BlockState east = world.getBlockState(pos.relative(Direction.EAST));
        BlockState south = world.getBlockState(pos.relative(Direction.SOUTH));
        BlockState west = world.getBlockState(pos.relative(Direction.WEST));

        if(north.isFaceSturdy(world, pos.relative(Direction.NORTH), Direction.SOUTH)) return true;
        if(east.isFaceSturdy(world, pos.relative(Direction.EAST), Direction.WEST)) return true;
        if(south.isFaceSturdy(world, pos.relative(Direction.SOUTH), Direction.NORTH)) return true;
        if(west.isFaceSturdy(world, pos.relative(Direction.WEST), Direction.EAST)) return true;

        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shape;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return shape;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClientSide()) {
            world.getBlockTicks().schedule(ScheduledTick.probe(this, pos));
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return false;
    }

    /*
     * If there is a rope above, don't connect to the side blocks
     * If there is no rope above, but it were earlier, break
     * If there is no rope above, and it wasn't here earlier, try connecting to nearby solid blocks
     * And if none of the nearby blocks are solid, break
     */
    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockPos up = pos.relative(Direction.UP);
        BlockState bup = world.getBlockState(up);

        boolean n = false;
        boolean e = false;
        boolean s = false;
        boolean w = false;
        boolean u = state.getValue(UP); // We need the old up value too

        if (bup.is(this)) {
            u = true;
        } else if (u) {
            u = false;
        } else {
            BlockPos npos = pos.relative(Direction.NORTH);
            BlockPos epos = pos.relative(Direction.EAST);
            BlockPos spos = pos.relative(Direction.SOUTH);
            BlockPos wpos = pos.relative(Direction.WEST);

            BlockState north = world.getBlockState(npos);
            BlockState east = world.getBlockState(epos);
            BlockState south = world.getBlockState(spos);
            BlockState west = world.getBlockState(wpos);

            if (north.isFaceSturdy(world, npos, Direction.SOUTH)) n = true;
            if (east.isFaceSturdy(world, epos, Direction.WEST)) e = true;
            if (south.isFaceSturdy(world, spos, Direction.NORTH)) s = true;
            if (west.isFaceSturdy(world, wpos, Direction.EAST)) w = true;
        }

        if (!bup.is(this) && (!n && !e && !s && !w) && !bup.isFaceSturdy(world, up, Direction.DOWN) && !u) {
            world.destroyBlock(pos, true);
        } else {
            world.setBlockAndUpdate(pos, state.setValue(NORTH, n).setValue(EAST, e).setValue(SOUTH, s).setValue(WEST, w).setValue(UP, u));
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!world.isClientSide()) {
            world.getBlockTicks().schedule(ScheduledTick.probe(this, pos));
        }
        return state;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!state.is(this)) {
            return InteractionResult.PASS;
        }

        if (!player.getMainHandItem().is(Item.byBlock(this))) {
            return InteractionResult.PASS;
        }

        BlockPos down = pos.relative(Direction.DOWN);
        BlockState statedown = world.getBlockState(down);

        if (statedown.is(this)) {
            return statedown.useWithoutItem(world, player, hit);
        } else if (statedown.isAir() && !world.isOutsideBuildHeight(down.getY())) {
            world.setBlockAndUpdate(down, state);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) player.getMainHandItem().shrink(1);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

}
