package one.dqu.additionaladditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class FlowerCropBlock extends CropBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;

    private final int MAX_AGE;
    private final VoxelShape[] SHAPE_BY_AGE;
    private final Supplier<Block> GROWN_BLOCK;

    public FlowerCropBlock(Supplier<Block> grownBlock, int maxAge, VoxelShape[] shapeByAge, Properties properties) {
        super(properties);

        GROWN_BLOCK = grownBlock;
        MAX_AGE = maxAge;
        SHAPE_BY_AGE = shapeByAge;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE_BY_AGE[Math.min(blockState.getValue(AGE), MAX_AGE - 1)];
    }

    @Override
    public BlockState getStateForAge(int i) {
        return i >= MAX_AGE ? GROWN_BLOCK.get().defaultBlockState() : super.getStateForAge(i);
    }

    @Override
    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(3) != 0) {
            super.randomTick(blockState, serverLevel, blockPos, randomSource);
        }
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return 1;
    }
}
