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
import net.minecraft.world.phys.EntityHitResult;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAEntities;
import one.dqu.additionaladditions.registry.AAItems;
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
        super(AAEntities.ROPE_ARROW.get(), livingEntity, level, itemStack, itemStack2);
    }

    public RopeArrow(Level level, double d, double e, double f, ItemStack itemStack, @Nullable ItemStack itemStack2) {
        super(AAEntities.ROPE_ARROW.get(), d, e, f, level, itemStack, itemStack2);
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

        // start placing ropes
        this.startPos = hitResult.getBlockPos().relative(hitResult.getDirection());
        this.isPlacingRopes = true;
        this.ropesPlaced = 0;
        this.tickCounter = 0;
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (ropesPlaced < 8 && this.pickup == Pickup.ALLOWED) {
            ItemStack stack = AAItems.ROPE.get().getDefaultInstance();
            stack.setCount(8 - ropesPlaced);
            ItemEntity itemEntity = new ItemEntity(this.level(), hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, stack);
            this.level().addFreshEntity(itemEntity);
            ropesPlaced = 8;
        }
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

            // prevents dupes with multishot crossbows / infinity enchants
            // this breaks the arrows in creative mode but oh well
            if (this.pickup != Pickup.ALLOWED) {
                isPlacingRopes = false;
                return;
            }

            BlockPos downPos = startPos.below(ropesPlaced);
            BlockState downState = this.level().getBlockState(downPos);

            boolean canReplace = downState.is(BlockTags.REPLACEABLE) || downState.getFluidState().is(FluidTags.WATER);
            boolean canSurvive = AABlocks.ROPE_BLOCK.get().canSurvive(downState, this.level(), downPos);
            if (!canReplace || !canSurvive) {
                // drop remaining ropes
                int remaining = 8 - ropesPlaced;
                if (remaining > 0) {
                    ItemStack stack = AAItems.ROPE.get().getDefaultInstance();
                    stack.setCount(remaining);
                    int up = canReplace ? 0 : 1;
                    ItemEntity itemEntity = new ItemEntity(this.level(), downPos.getCenter().x, downPos.getCenter().y + up, downPos.getCenter().z, stack);
                    this.level().addFreshEntity(itemEntity);
                }
                isPlacingRopes = false;
                return;
            }

            boolean isWaterlogged = this.level().getFluidState(downPos).is(Fluids.WATER);
            BlockState ropeState = AABlocks.ROPE_BLOCK.get().defaultBlockState()
                    .setValue(BlockStateProperties.WATERLOGGED, isWaterlogged);

            this.level().setBlockAndUpdate(downPos, ropeState);
            this.level().playSound(null, downPos, SoundType.WOOL.getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
            ropesPlaced++;
        }
    }
}
