package one.dqu.additionaladditions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.ticks.ScheduledTick;
import one.dqu.additionaladditions.registry.AAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class GlowStickBlock extends FallingBlock implements SimpleWaterloggedBlock {
    public static final VoxelShape shape = Block.box(0, 0, 0, 16, 1, 16);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final MapCodec<GlowStickBlock> CODEC = simpleCodec(GlowStickBlock::new);

    public GlowStickBlock(Properties settings) {
        super(settings);
        registerDefaultState(getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return canSupportCenter(levelReader, blockPos.below(), Direction.UP);
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    @Override
    public void onLand(Level level, BlockPos blockPos, BlockState thisBlock, BlockState existingBlock, FallingBlockEntity fallingBlockEntity) {
        super.onLand(level, blockPos, thisBlock, existingBlock, fallingBlockEntity);

        if (!thisBlock.canSurvive(level, blockPos)) {
            level.destroyBlock(blockPos, true);
        }
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
    public Item asItem() {
        return AAItems.GLOW_STICK_ITEM.get();
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return false;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER));
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!world.isClientSide()) {
            world.getBlockTicks().schedule(ScheduledTick.probe(this, pos));
        }
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }
}
