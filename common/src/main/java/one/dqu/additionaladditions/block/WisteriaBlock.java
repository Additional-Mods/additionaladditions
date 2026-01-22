package one.dqu.additionaladditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WisteriaBlock extends VineBlock {
    public WisteriaBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        // no spreading
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
}
