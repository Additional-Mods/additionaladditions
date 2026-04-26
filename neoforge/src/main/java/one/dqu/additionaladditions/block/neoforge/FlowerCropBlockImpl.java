package one.dqu.additionaladditions.block.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FlowerCropBlockImpl {
    public static float getGrowthSpeedPlatform(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return CropBlock.getGrowthSpeed(blockState, blockGetter, blockPos);
    }
}
