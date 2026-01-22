package one.dqu.additionaladditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import one.dqu.additionaladditions.registry.AAMisc;

public class TintedRedstoneLampBlock extends RedstoneLampBlock {
    public TintedRedstoneLampBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (level.isClientSide) return;

        boolean lit = blockState.getValue(LIT);
        if (lit != level.hasNeighborSignal(blockPos)) {
            if (lit) {
                level.scheduleTick(blockPos, this, 4);
            } else {
                BlockState newState = blockState.cycle(LIT);
                level.setBlock(blockPos, newState, 2);

                if (newState.getValue(LIT)) {
                    AABB area = new AABB(blockPos).inflate(10);
                    for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area)) {
                        AAMisc.ACTIVATE_TINTED_LAMP_TRIGGER.get().trigger(player);
                    }
                }
            }
        }
    }
}
