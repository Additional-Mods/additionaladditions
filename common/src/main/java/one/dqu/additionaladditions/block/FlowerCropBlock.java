package one.dqu.additionaladditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class FlowerCropBlock extends CropBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

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

    // based on super.randomTick and TorchflowerCropBlock
    @Override
    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(3) != 0) {
            if (serverLevel.getRawBrightness(blockPos, 0) >= 9) {
                int i = this.getAge(blockState);
                if (i < this.getMaxAge()) {
                    float f = getGrowthSpeed(this, serverLevel, blockPos);
                    if (randomSource.nextInt((int)(25.0F / f) + 1) == 0) {
                        placeFlower(i + 1, serverLevel, blockPos);
                    }
                }
            }
        }
    }

    // based on super.growCrops
    @Override
    public void growCrops(Level level, BlockPos blockPos, BlockState blockState) {
        int i = this.getAge(blockState) + this.getBonemealAgeIncrease(level);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        placeFlower(i, level, blockPos);
    }

    private void placeFlower(int age, Level level, BlockPos blockPos) {
        if (age < MAX_AGE) {
            level.setBlock(blockPos, this.getStateForAge(age), 2);
            return;
        }

        if (GROWN_BLOCK.get() instanceof DoublePlantBlock) {
            BlockPos abovePos = blockPos.above();
            BlockState aboveState = level.getBlockState(abovePos);

            boolean canPlace = !level.isOutsideBuildHeight(abovePos) && aboveState.isAir();
            if (!canPlace) return;

            level.setBlock(blockPos, GROWN_BLOCK.get().defaultBlockState(), 2);
            level.setBlock(abovePos, GROWN_BLOCK.get().defaultBlockState().cycle(DoublePlantBlock.HALF), 2);
        } else {
            level.setBlock(blockPos, GROWN_BLOCK.get().defaultBlockState(), 2);
        }
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return 1;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        int newAge = this.getAge(blockState) + 1;
        if (newAge >= MAX_AGE && GROWN_BLOCK.get() instanceof DoublePlantBlock) {
            BlockPos abovePos = blockPos.above();
            BlockState aboveState = levelReader.getBlockState(abovePos);
            return super.isValidBonemealTarget(levelReader, blockPos, blockState) &&
                   !levelReader.isOutsideBuildHeight(abovePos) && aboveState.isAir();
        } else {
            return super.isValidBonemealTarget(levelReader, blockPos, blockState);
        }
    }
}
