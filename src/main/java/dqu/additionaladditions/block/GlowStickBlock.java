package dqu.additionaladditions.block;

import dqu.additionaladditions.Registrar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class GlowStickBlock extends FallingBlock {
    public static final VoxelShape shape = Block.createCuboidShape(2, 0, 2,14, 2, 14);
    public static final BooleanProperty FLIPPED = BooleanProperty.of("flipped");

    public GlowStickBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(FLIPPED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FLIPPED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return shape;
    }

    @Override
    public Item asItem() {
        return Registrar.GLOW_STICK_ITEM;
    }
}
