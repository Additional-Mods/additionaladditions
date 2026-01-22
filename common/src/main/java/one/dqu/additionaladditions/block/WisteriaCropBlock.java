package one.dqu.additionaladditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import one.dqu.additionaladditions.registry.AABlocks;

public class WisteriaCropBlock extends VineBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;

    public WisteriaCropBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(UP, false)
                        .setValue(NORTH, false)
                        .setValue(EAST, false)
                        .setValue(SOUTH, false)
                        .setValue(WEST, false)
                        .setValue(AGE, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }

    public BlockState getStateForAge(BlockState currentState, int age) {
        BlockState newState = age >= 2 ? AABlocks.WISTERIA.get().defaultBlockState()
                : this.defaultBlockState().setValue(AGE, age);;

        return newState
                .setValue(UP, currentState.getValue(UP))
                .setValue(NORTH, currentState.getValue(NORTH))
                .setValue(EAST, currentState.getValue(EAST))
                .setValue(SOUTH, currentState.getValue(SOUTH))
                .setValue(WEST, currentState.getValue(WEST));
    }

    @Override
    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (serverLevel.getRawBrightness(blockPos, 0) >= 9) {
            int age = blockState.getValue(AGE);
            if (age < 2) {
                if (randomSource.nextInt(6) == 0) {
                    serverLevel.setBlock(blockPos, this.getStateForAge(blockState, age + 1), 2);
                }
            }
        }
    }

    @Override
    protected boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
        if (blockPlaceContext.getItemInHand().is(this.asItem())) {
            BlockState clickedState = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());
            if (clickedState.is(this)) {
                int faces = 0;
                for (var property : PROPERTY_BY_DIRECTION.values()) {
                    if (clickedState.getValue(property)) {
                        faces++;
                    }
                }
                return faces < PROPERTY_BY_DIRECTION.size();
            }
        }
        return false;
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return levelReader.getRawBrightness(blockPos, 0) >= 8 && super.canSurvive(blockState, levelReader, blockPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(AGE) < 2;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        int age = blockState.getValue(AGE);
        if (age < 2) {
            serverLevel.setBlock(blockPos, this.getStateForAge(blockState, age + 1), 2);
        }
    }
}
