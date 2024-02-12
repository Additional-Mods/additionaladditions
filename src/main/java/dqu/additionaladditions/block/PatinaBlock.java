package dqu.additionaladditions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.FallingBlock;

public class PatinaBlock extends FallingBlock {
    public static final MapCodec<PatinaBlock> CODEC = simpleCodec(PatinaBlock::new);

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    public PatinaBlock(Properties properties) {
        super(properties);
    }
}
