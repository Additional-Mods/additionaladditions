package one.dqu.additionaladditions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PatinaBlock extends FallingBlock {
    public static final MapCodec<PatinaBlock> CODEC = simpleCodec(PatinaBlock::new);

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    public PatinaBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getDustColor(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return 0x4FC18E;
    }
}
