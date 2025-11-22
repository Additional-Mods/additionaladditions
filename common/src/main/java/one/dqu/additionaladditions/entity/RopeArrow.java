package one.dqu.additionaladditions.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import one.dqu.additionaladditions.registry.AdditionalBlocks;
import one.dqu.additionaladditions.registry.AdditionalEntities;
import org.jetbrains.annotations.Nullable;

public class RopeArrow extends AbstractArrow {
    private boolean isPlacingRopes = false;
    private BlockPos startPos = null;
    private int ropesPlaced = 0;
    private int tickCounter = 0;

    public RopeArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public RopeArrow(Level level, LivingEntity livingEntity, ItemStack itemStack, @Nullable ItemStack itemStack2) {
        super(AdditionalEntities.ROPE_ARROW.get(), livingEntity, level, itemStack, itemStack2);
    }

    public RopeArrow(Level level, double d, double e, double f, ItemStack itemStack, @Nullable ItemStack itemStack2) {
        super(AdditionalEntities.ROPE_ARROW.get(), d, e, f, level, itemStack, itemStack2);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return Items.ARROW.getDefaultInstance();
    }

    // i don't know how to make it not pick up rope arrows otherwise
    @Override
    protected ItemStack getPickupItem() {
        return super.getPickupItem().transmuteCopy(Items.ARROW);
    }

    @Override
    public ItemStack getPickupItemStackOrigin() {
        return super.getPickupItemStackOrigin().transmuteCopy(Items.ARROW);
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);

        if (this.level().isClientSide()) {
            return;
        }

        if (isPlacingRopes) {
            return;
        }

        BlockPos pos = hitResult.getBlockPos().relative(hitResult.getDirection());
        BlockState state = this.level().getBlockState(pos);
        if (!AdditionalBlocks.ROPE_BLOCK.get().canSurvive(state, this.level(), pos)) {
            return;
        }

        // start placing ropes
        this.startPos = pos;
        this.isPlacingRopes = true;
        this.ropesPlaced = 0;
        this.tickCounter = 0;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide() && isPlacingRopes && startPos != null) {
            tickCounter++;

            if (tickCounter < 2) {
                return;
            }

            tickCounter = 0;

            if (ropesPlaced == 8) {
                isPlacingRopes = false;
                return;
            }

            BlockPos downPos = startPos.below(ropesPlaced);
            BlockState downState = this.level().getBlockState(downPos);

            boolean canReplace = downState.is(BlockTags.REPLACEABLE) || downState.getFluidState().is(FluidTags.WATER);
            if (!canReplace) {
                // drop remaining ropes
                int remaining = 8 - ropesPlaced;
                if (remaining > 0) {
                    ItemStack stack = AdditionalBlocks.ROPE_BLOCK_ITEM.get().getDefaultInstance();
                    stack.setCount(remaining);
                    ItemEntity itemEntity = new ItemEntity(this.level(), downPos.getCenter().x, downPos.getCenter().y + 1, downPos.getCenter().z, stack);
                    this.level().addFreshEntity(itemEntity);
                }
                isPlacingRopes = false;
                return;
            }

            boolean isWaterlogged = this.level().getFluidState(downPos).is(Fluids.WATER);
            BlockState ropeState = AdditionalBlocks.ROPE_BLOCK.get().defaultBlockState()
                    .setValue(BlockStateProperties.WATERLOGGED, isWaterlogged);
            this.level().setBlockAndUpdate(downPos, ropeState);

            this.level().playSound(null, downPos, SoundType.WOOL.getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
            ropesPlaced++;
        }
    }
}
