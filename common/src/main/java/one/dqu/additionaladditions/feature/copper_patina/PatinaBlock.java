package one.dqu.additionaladditions.feature.copper_patina;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PatinaBlock extends FallingBlock {
    public PatinaBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getDustColor(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return 0x4FC18E;
    }
}
