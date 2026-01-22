package one.dqu.additionaladditions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.shapes.Shapes;
import one.dqu.additionaladditions.registry.AAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlowStickBlock extends FallingBlock {
    public static final VoxelShape shape = Block.box(0, 0, 0, 16, 1, 16);
    public static final MapCodec<GlowStickBlock> CODEC = simpleCodec(GlowStickBlock::new);

    public GlowStickBlock(Properties settings) {
        super(settings);
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
}
