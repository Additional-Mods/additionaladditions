package dqu.additionaladditions.block;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import java.util.Random;

/** @noinspection deprecation*/
public class RopeBlock extends Block {
    public static final VoxelShape shape = Block.box(6, 0, 6,10, 16, 10);
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");

    public RopeBlock(Properties settings) {
        super(settings);
        registerDefaultState(getStateDefinition().any().setValue(NORTH, false).setValue(SOUTH, false).setValue(EAST, false).setValue(WEST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(NORTH);
        stateManager.add(EAST);
        stateManager.add(SOUTH);
        stateManager.add(WEST);
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

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        BlockPos npos = pos.relative(Direction.NORTH);
        BlockPos epos = pos.relative(Direction.EAST);
        BlockPos spos = pos.relative(Direction.SOUTH);
        BlockPos wpos = pos.relative(Direction.WEST);

        BlockState north = world.getBlockState(npos);
        BlockState east = world.getBlockState(epos);
        BlockState south = world.getBlockState(spos);
        BlockState west = world.getBlockState(wpos);

        boolean n = false;
        boolean e = false;
        boolean s = false;
        boolean w = false;

        if (north.isFaceSturdy(world, npos, Direction.SOUTH)) n = true;
        if (east.isFaceSturdy(world, epos, Direction.WEST)) e = true;
        if (south.isFaceSturdy(world, spos, Direction.NORTH)) s = true;
        if (west.isFaceSturdy(world, wpos, Direction.EAST)) w = true;

        world.setBlockAndUpdate(pos, state.setValue(NORTH, n).setValue(EAST, e).setValue(SOUTH, s).setValue(WEST, w));

        BlockPos up = pos.relative(Direction.UP);
        BlockState bup = world.getBlockState(up);

        if (!world.getBlockState(up).is(this) && (!n && !e && !s && !w) && !bup.isFaceSturdy(world, up, Direction.DOWN)) {
            world.destroyBlock(pos, true);
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
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!state.is(this)) return InteractionResult.PASS;
        if (!player.getMainHandItem().is(Item.byBlock(AdditionalBlocks.ROPE_BLOCK))) return InteractionResult.PASS;
        BlockPos down = pos.relative(Direction.DOWN);
        BlockState statedown = world.getBlockState(down);
        if (statedown.is(AdditionalBlocks.ROPE_BLOCK)) {
            return statedown.getBlock().use(statedown, world, down, player, hand, hit);
        } else if (statedown.isAir() && !world.isOutsideBuildHeight(down.getY())) {
            world.setBlockAndUpdate(down, state);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) player.getMainHandItem().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

}
