package dqu.additionaladditions.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

// Modified RedstoneWireBlock
public class CopperPatinaBlock extends Block {
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_NORTH;
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_EAST;
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_SOUTH;
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_WEST;
    public static final IntegerProperty POWER;
    public static final Map<Direction, EnumProperty<RedstoneSide>> DIRECTION_TO_WIRE_CONNECTION_PROPERTY;
    protected static final int field_31222 = 1;
    protected static final int field_31223 = 3;
    protected static final int field_31224 = 13;
    protected static final int field_31225 = 3;
    protected static final int field_31226 = 13;
    private static final VoxelShape DOT_SHAPE;
    private static final Map<Direction, VoxelShape> field_24414;
    private static final Map<Direction, VoxelShape> field_24415;
    private static final Map<BlockState, VoxelShape> SHAPES;
    private static final Vec3[] COLORS;
    private static final float field_31221 = 0.2F;
    private final BlockState dotState;
    private boolean wiresGivePower = true;

    public CopperPatinaBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(WIRE_CONNECTION_NORTH, RedstoneSide.NONE)).setValue(WIRE_CONNECTION_EAST, RedstoneSide.NONE)).setValue(WIRE_CONNECTION_SOUTH, RedstoneSide.NONE)).setValue(WIRE_CONNECTION_WEST, RedstoneSide.NONE)).setValue(POWER, 0));
        this.dotState = (BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(WIRE_CONNECTION_NORTH, RedstoneSide.SIDE)).setValue(WIRE_CONNECTION_EAST, RedstoneSide.SIDE)).setValue(WIRE_CONNECTION_SOUTH, RedstoneSide.SIDE)).setValue(WIRE_CONNECTION_WEST, RedstoneSide.SIDE);
        UnmodifiableIterator var2 = this.getStateDefinition().getPossibleStates().iterator();

        while(var2.hasNext()) {
            BlockState blockState = (BlockState)var2.next();
            if ((Integer)blockState.getValue(POWER) == 0) {
                SHAPES.put(blockState, this.getShapeForState(blockState));
            }
        }

    }

    private VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelShape = DOT_SHAPE;
        Iterator var3 = Plane.HORIZONTAL.iterator();

        while(var3.hasNext()) {
            Direction direction = (Direction)var3.next();
            RedstoneSide wireConnection = (RedstoneSide)state.getValue((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection == RedstoneSide.SIDE) {
                voxelShape = Shapes.or(voxelShape, (VoxelShape)field_24414.get(direction));
            } else if (wireConnection == RedstoneSide.UP) {
                voxelShape = Shapes.or(voxelShape, (VoxelShape)field_24415.get(direction));
            }
        }

        return voxelShape;
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (VoxelShape)SHAPES.get(state.setValue(POWER, 0));
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.getPlacementState(ctx.getLevel(), this.dotState, ctx.getClickedPos());
    }

    private BlockState getPlacementState(BlockGetter world, BlockState state, BlockPos pos) {
        boolean bl = isNotConnected(state);
        state = this.method_27843(world, (BlockState)this.defaultBlockState().setValue(POWER, (Integer)state.getValue(POWER)), pos);
        if (bl && isNotConnected(state)) {
            return state;
        } else {
            boolean bl2 = ((RedstoneSide)state.getValue(WIRE_CONNECTION_NORTH)).isConnected();
            boolean bl3 = ((RedstoneSide)state.getValue(WIRE_CONNECTION_SOUTH)).isConnected();
            boolean bl4 = ((RedstoneSide)state.getValue(WIRE_CONNECTION_EAST)).isConnected();
            boolean bl5 = ((RedstoneSide)state.getValue(WIRE_CONNECTION_WEST)).isConnected();
            boolean bl6 = !bl2 && !bl3;
            boolean bl7 = !bl4 && !bl5;
            if (!bl5 && bl6) {
                state = (BlockState)state.setValue(WIRE_CONNECTION_WEST, RedstoneSide.SIDE);
            }

            if (!bl4 && bl6) {
                state = (BlockState)state.setValue(WIRE_CONNECTION_EAST, RedstoneSide.SIDE);
            }

            if (!bl2 && bl7) {
                state = (BlockState)state.setValue(WIRE_CONNECTION_NORTH, RedstoneSide.SIDE);
            }

            if (!bl3 && bl7) {
                state = (BlockState)state.setValue(WIRE_CONNECTION_SOUTH, RedstoneSide.SIDE);
            }

            return state;
        }
    }

    private BlockState method_27843(BlockGetter world, BlockState state, BlockPos pos) {
        boolean bl = !world.getBlockState(pos.above()).isRedstoneConductor(world, pos);
        Iterator var5 = Plane.HORIZONTAL.iterator();

        while(var5.hasNext()) {
            Direction direction = (Direction)var5.next();
            if (!((RedstoneSide)state.getValue((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected()) {
                RedstoneSide wireConnection = this.getRenderConnectionType(world, pos, direction, bl);
                state = (BlockState)state.setValue((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection);
            }
        }

        return state;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            return state;
        } else if (direction == Direction.UP) {
            return this.getPlacementState(world, state, pos);
        } else {
            RedstoneSide wireConnection = this.getRenderConnectionType(world, pos, direction);
            return wireConnection.isConnected() == ((RedstoneSide)state.getValue((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected() && !isFullyConnected(state) ? (BlockState)state.setValue((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection) : this.getPlacementState(world, (BlockState)((BlockState)this.dotState.setValue(POWER, (Integer)state.getValue(POWER))).setValue((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection), pos);
        }
    }

    private static boolean isFullyConnected(BlockState state) {
        return ((RedstoneSide)state.getValue(WIRE_CONNECTION_NORTH)).isConnected() && ((RedstoneSide)state.getValue(WIRE_CONNECTION_SOUTH)).isConnected() && ((RedstoneSide)state.getValue(WIRE_CONNECTION_EAST)).isConnected() && ((RedstoneSide)state.getValue(WIRE_CONNECTION_WEST)).isConnected();
    }

    private static boolean isNotConnected(BlockState state) {
        return !((RedstoneSide)state.getValue(WIRE_CONNECTION_NORTH)).isConnected() && !((RedstoneSide)state.getValue(WIRE_CONNECTION_SOUTH)).isConnected() && !((RedstoneSide)state.getValue(WIRE_CONNECTION_EAST)).isConnected() && !((RedstoneSide)state.getValue(WIRE_CONNECTION_WEST)).isConnected();
    }

    public void updateIndirectNeighbourShapes(BlockState state, LevelAccessor world, BlockPos pos, int flags, int maxUpdateDepth) {
        MutableBlockPos mutable = new MutableBlockPos();
        Iterator var7 = Plane.HORIZONTAL.iterator();

        while(var7.hasNext()) {
            Direction direction = (Direction)var7.next();
            RedstoneSide wireConnection = (RedstoneSide)state.getValue((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection != RedstoneSide.NONE && !world.getBlockState(mutable.setWithOffset(pos, direction)).is(this)) {
                mutable.move(Direction.DOWN);
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.is(Blocks.OBSERVER)) {
                    BlockPos blockPos = mutable.relative(direction.getOpposite());
                    BlockState blockState2 = blockState.updateShape(direction.getOpposite(), world.getBlockState(blockPos), world, mutable, blockPos);
                    updateOrDestroy(blockState, blockState2, world, mutable, flags, maxUpdateDepth);
                }

                mutable.setWithOffset(pos, direction).move(Direction.UP);
                BlockState blockState3 = world.getBlockState(mutable);
                if (!blockState3.is(Blocks.OBSERVER)) {
                    BlockPos blockPos2 = mutable.relative(direction.getOpposite());
                    BlockState blockState4 = blockState3.updateShape(direction.getOpposite(), world.getBlockState(blockPos2), world, mutable, blockPos2);
                    updateOrDestroy(blockState3, blockState4, world, mutable, flags, maxUpdateDepth);
                }
            }
        }

    }

    private RedstoneSide getRenderConnectionType(BlockGetter world, BlockPos pos, Direction direction) {
        return this.getRenderConnectionType(world, pos, direction, !world.getBlockState(pos.above()).isRedstoneConductor(world, pos));
    }

    private RedstoneSide getRenderConnectionType(BlockGetter world, BlockPos pos, Direction direction, boolean bl) {
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (bl) {
            boolean bl2 = this.canRunOnTop(world, blockPos, blockState);
            if (bl2 && connectsTo(world.getBlockState(blockPos.above()))) {
                if (blockState.isFaceSturdy(world, blockPos, direction.getOpposite())) {
                    return RedstoneSide.UP;
                }

                return RedstoneSide.SIDE;
            }
        }

        return !connectsTo(blockState, direction) && (blockState.isRedstoneConductor(world, blockPos) || !connectsTo(world.getBlockState(blockPos.below()))) ? RedstoneSide.NONE : RedstoneSide.SIDE;
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (!Config.getBool(ConfigValues.COPPER_PATINA)) return false;

        BlockPos blockPos = pos.below();
        BlockState blockState = world.getBlockState(blockPos);
        return this.canRunOnTop(world, blockPos, blockState);
    }

    private boolean canRunOnTop(BlockGetter world, BlockPos pos, BlockState floor) {
        return floor.isFaceSturdy(world, pos, Direction.UP) || floor.is(Blocks.HOPPER);
    }

    private void update(Level world, BlockPos pos, BlockState state) {
        int i = this.getReceivedRedstonePower(world, pos);
        if (!Config.getBool(ConfigValues.COPPER_PATINA)) i = 0;
        if ((Integer)state.getValue(POWER) != i) {
            if (world.getBlockState(pos) == state) {
                world.setBlock(pos, (BlockState)state.setValue(POWER, i), 2);
            }

            Set<BlockPos> set = Sets.newHashSet();
            set.add(pos);
            Direction[] var6 = Direction.values();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Direction direction = var6[var8];
                set.add(pos.relative(direction));
            }

            Iterator var10 = set.iterator();

            while(var10.hasNext()) {
                BlockPos blockPos = (BlockPos)var10.next();
                world.updateNeighborsAt(blockPos, this);
            }
        }

    }

    private int getReceivedRedstonePower(Level world, BlockPos pos) {
        this.wiresGivePower = false;

        int i = 0;
        Direction[] dirs = UPDATE_SHAPE_ORDER;
        int dircount = dirs.length;

        for (int o = 0; o < dircount; ++o) {
            Direction dir = dirs[o];
            BlockPos posi = pos.relative(dir);
            BlockState state = world.getBlockState(posi);
            int j = 0;
            if (!state.is(Blocks.REDSTONE_WIRE)) {
                j = world.getSignal(posi, dir);
            }
            if (j >= 15) {
                i = 15;
                break;
            }
            if (j > i) i = j;
        }

        this.wiresGivePower = true;

        int j = 0;
        if (i < 15) {
            Iterator var5 = Plane.HORIZONTAL.iterator();

            while(true) {
                while(var5.hasNext()) {
                    Direction direction = (Direction)var5.next();
                    BlockPos blockPos = pos.relative(direction);
                    BlockState blockState = world.getBlockState(blockPos);
                    j = Math.max(j, this.increasePower(blockState));
                    BlockPos blockPos2 = pos.above();
                    if (blockState.isRedstoneConductor(world, blockPos) && !world.getBlockState(blockPos2).isRedstoneConductor(world, blockPos2)) {
                        j = Math.max(j, this.increasePower(world.getBlockState(blockPos.above())));
                    } else if (!blockState.isRedstoneConductor(world, blockPos)) {
                        j = Math.max(j, this.increasePower(world.getBlockState(blockPos.below())));
                    }
                }

                return Math.max(i, j - 2);
            }
        } else {
            return Math.max(i, j - 2);
        }
    }

    private int increasePower(BlockState state) {
        return state.is(AdditionalBlocks.COPPER_PATINA) ? (Integer) state.getValue(POWER) : 0;
    }

    private void updateNeighbors(Level world, BlockPos pos) {
        if (world.getBlockState(pos).is(this)) {
            world.updateNeighborsAt(pos, this);
            Direction[] var3 = Direction.values();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Direction direction = var3[var5];
                world.updateNeighborsAt(pos.relative(direction), this);
            }

        }
    }

    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.is(state.getBlock()) && !world.isClientSide) {
            this.update(world, pos, state);
            Iterator var6 = Plane.VERTICAL.iterator();

            while(var6.hasNext()) {
                Direction direction = (Direction)var6.next();
                world.updateNeighborsAt(pos.relative(direction), this);
            }

            this.updateOffsetNeighbors(world, pos);
        }
    }

    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved && !state.is(newState.getBlock())) {
            super.onRemove(state, world, pos, newState, moved);
            if (!world.isClientSide) {
                Direction[] var6 = Direction.values();
                int var7 = var6.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    Direction direction = var6[var8];
                    world.updateNeighborsAt(pos.relative(direction), this);
                }

                this.update(world, pos, state);
                this.updateOffsetNeighbors(world, pos);
            }
        }
    }

    private void updateOffsetNeighbors(Level world, BlockPos pos) {
        Iterator var3 = Plane.HORIZONTAL.iterator();

        Direction direction2;
        while(var3.hasNext()) {
            direction2 = (Direction)var3.next();
            this.updateNeighbors(world, pos.relative(direction2));
        }

        var3 = Plane.HORIZONTAL.iterator();

        while(var3.hasNext()) {
            direction2 = (Direction)var3.next();
            BlockPos blockPos = pos.relative(direction2);
            if (world.getBlockState(blockPos).isRedstoneConductor(world, blockPos)) {
                this.updateNeighbors(world, blockPos.above());
            } else {
                this.updateNeighbors(world, blockPos.below());
            }
        }

    }

    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClientSide) {
            if (state.canSurvive(world, pos)) {
                this.update(world, pos, state);
            } else {
                dropResources(state, world, pos);
                world.removeBlock(pos, false);
            }

        }
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return this.wiresGivePower && !state.is(Blocks.REDSTONE_WIRE) ? state.getSignal(world, pos, direction) : 0;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        if (this.wiresGivePower && direction != Direction.DOWN) {
            int i = (Integer)state.getValue(POWER);
            if (i == 0 || state.is(Blocks.REDSTONE_WIRE)) {
                return 0;
            } else {
                return direction != Direction.UP && !((RedstoneSide)this.getPlacementState(world, state, pos).getValue((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction.getOpposite()))).isConnected() ? 0 : i;
            }
        } else {
            return 0;
        }
    }

    protected static boolean connectsTo(BlockState state) {
        return connectsTo(state, (Direction)null);
    }

    protected static boolean connectsTo(BlockState state, @Nullable Direction dir) {
        if (state.is(AdditionalBlocks.COPPER_PATINA)) {
            return true;
        } else if (state.is(Blocks.REDSTONE_WIRE)) {
            return false;
        } else if (state.is(Blocks.REPEATER)) {
            Direction direction = (Direction)state.getValue(RepeaterBlock.FACING);
            return direction == dir || direction.getOpposite() == dir;
        } else if (state.is(Blocks.OBSERVER)) {
            return dir == state.getValue(ObserverBlock.FACING);
        } else {
            return state.isSignalSource() && dir != null;
        }
    }

    public boolean isSignalSource(BlockState state) {
        return this.wiresGivePower;
    }

    public static int getWireColor(int powerLevel) {
        Vec3 vec3d = COLORS[powerLevel];
        return Mth.color((float)vec3d.x(), (float)vec3d.y(), (float)vec3d.z());
    }

    private void addPoweredParticles(Level world, Random random, BlockPos pos, Vec3 color, Direction direction, Direction direction2, float f, float g) {
        // Do not add powered particles
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        int i = (Integer)state.getValue(POWER);
        if (i != 0) {
            Iterator var6 = Plane.HORIZONTAL.iterator();

            while(var6.hasNext()) {
                Direction direction = (Direction)var6.next();
                RedstoneSide wireConnection = (RedstoneSide)state.getValue((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
                switch(wireConnection) {
                    case UP:
                        this.addPoweredParticles(world, random, pos, COLORS[i], direction, Direction.UP, -0.5F, 0.5F);
                    case SIDE:
                        this.addPoweredParticles(world, random, pos, COLORS[i], Direction.DOWN, direction, 0.0F, 0.5F);
                        break;
                    case NONE:
                    default:
                        this.addPoweredParticles(world, random, pos, COLORS[i], Direction.DOWN, direction, 0.0F, 0.3F);
                }
            }

        }
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        switch(rotation) {
            case CLOCKWISE_180:
                return (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(WIRE_CONNECTION_NORTH, (RedstoneSide)state.getValue(WIRE_CONNECTION_SOUTH))).setValue(WIRE_CONNECTION_EAST, (RedstoneSide)state.getValue(WIRE_CONNECTION_WEST))).setValue(WIRE_CONNECTION_SOUTH, (RedstoneSide)state.getValue(WIRE_CONNECTION_NORTH))).setValue(WIRE_CONNECTION_WEST, (RedstoneSide)state.getValue(WIRE_CONNECTION_EAST));
            case COUNTERCLOCKWISE_90:
                return (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(WIRE_CONNECTION_NORTH, (RedstoneSide)state.getValue(WIRE_CONNECTION_EAST))).setValue(WIRE_CONNECTION_EAST, (RedstoneSide)state.getValue(WIRE_CONNECTION_SOUTH))).setValue(WIRE_CONNECTION_SOUTH, (RedstoneSide)state.getValue(WIRE_CONNECTION_WEST))).setValue(WIRE_CONNECTION_WEST, (RedstoneSide)state.getValue(WIRE_CONNECTION_NORTH));
            case CLOCKWISE_90:
                return (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(WIRE_CONNECTION_NORTH, (RedstoneSide)state.getValue(WIRE_CONNECTION_WEST))).setValue(WIRE_CONNECTION_EAST, (RedstoneSide)state.getValue(WIRE_CONNECTION_NORTH))).setValue(WIRE_CONNECTION_SOUTH, (RedstoneSide)state.getValue(WIRE_CONNECTION_EAST))).setValue(WIRE_CONNECTION_WEST, (RedstoneSide)state.getValue(WIRE_CONNECTION_SOUTH));
            default:
                return state;
        }
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        switch(mirror) {
            case LEFT_RIGHT:
                return (BlockState)((BlockState)state.setValue(WIRE_CONNECTION_NORTH, (RedstoneSide)state.getValue(WIRE_CONNECTION_SOUTH))).setValue(WIRE_CONNECTION_SOUTH, (RedstoneSide)state.getValue(WIRE_CONNECTION_NORTH));
            case FRONT_BACK:
                return (BlockState)((BlockState)state.setValue(WIRE_CONNECTION_EAST, (RedstoneSide)state.getValue(WIRE_CONNECTION_WEST))).setValue(WIRE_CONNECTION_WEST, (RedstoneSide)state.getValue(WIRE_CONNECTION_EAST));
            default:
                return super.mirror(state, mirror);
        }
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WIRE_CONNECTION_NORTH, WIRE_CONNECTION_EAST, WIRE_CONNECTION_SOUTH, WIRE_CONNECTION_WEST, POWER});
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            if (isFullyConnected(state) || isNotConnected(state)) {
                BlockState blockState = isFullyConnected(state) ? this.defaultBlockState() : this.dotState;
                blockState = (BlockState)blockState.setValue(POWER, (Integer)state.getValue(POWER));
                blockState = this.getPlacementState(world, blockState, pos);
                if (blockState != state) {
                    world.setBlock(pos, blockState, 3);
                    this.updateForNewState(world, pos, state, blockState);
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.PASS;
        }
    }

    private void updateForNewState(Level world, BlockPos pos, BlockState oldState, BlockState newState) {
        Iterator var5 = Plane.HORIZONTAL.iterator();

        while(var5.hasNext()) {
            Direction direction = (Direction)var5.next();
            BlockPos blockPos = pos.relative(direction);
            if (((RedstoneSide)oldState.getValue((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected() != ((RedstoneSide)newState.getValue((Property)DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected() && world.getBlockState(blockPos).isRedstoneConductor(world, blockPos)) {
                world.updateNeighborsAtExceptFromFacing(blockPos, newState.getBlock(), direction.getOpposite());
            }
        }

    }

    static {
        WIRE_CONNECTION_NORTH = BlockStateProperties.NORTH_REDSTONE;
        WIRE_CONNECTION_EAST = BlockStateProperties.EAST_REDSTONE;
        WIRE_CONNECTION_SOUTH = BlockStateProperties.SOUTH_REDSTONE;
        WIRE_CONNECTION_WEST = BlockStateProperties.WEST_REDSTONE;
        POWER = BlockStateProperties.POWER;
        DIRECTION_TO_WIRE_CONNECTION_PROPERTY = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, WIRE_CONNECTION_NORTH, Direction.EAST, WIRE_CONNECTION_EAST, Direction.SOUTH, WIRE_CONNECTION_SOUTH, Direction.WEST, WIRE_CONNECTION_WEST));
        DOT_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
        field_24414 = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, Block.box(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, Block.box(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
        field_24415 = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Shapes.or((VoxelShape)field_24414.get(Direction.NORTH), Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, Shapes.or((VoxelShape)field_24414.get(Direction.SOUTH), Block.box(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, Shapes.or((VoxelShape)field_24414.get(Direction.EAST), Block.box(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, Shapes.or((VoxelShape)field_24414.get(Direction.WEST), Block.box(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
        SHAPES = Maps.newHashMap();
        COLORS = (Vec3[])Util.make(new Vec3[16], (vec3ds) -> {
            for(int i = 0; i <= 15; ++i) {
                float f = (float)i / 15.0F;
                float g = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
                float h = Mth.clamp(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
                float j = Mth.clamp(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
                vec3ds[i] = new Vec3((double)g, (double)h, (double)j);
            }

        });
    }
}
