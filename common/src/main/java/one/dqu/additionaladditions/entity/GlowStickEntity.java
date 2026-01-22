package one.dqu.additionaladditions.entity;

import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import one.dqu.additionaladditions.registry.AAEntities;

public class GlowStickEntity extends ThrowableItemProjectile {
    public GlowStickEntity(EntityType<? extends GlowStickEntity> entityType, Level world) {
        super(entityType, world);
    }

    public GlowStickEntity(Level world, LivingEntity owner) {
        super(AAEntities.GLOW_STICK.get(), owner, world);
    }

    public GlowStickEntity(Level world, double x, double y, double z) {
        super(AAEntities.GLOW_STICK.get(), x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return AAItems.GLOW_STICK_ITEM.get();
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);

        if (!this.level().isClientSide()) {
            this.remove(RemovalReason.DISCARDED);

            BlockPos pos = BlockPos.containing(this.getX(), this.getY(), this.getZ());

            if (this.level().getBlockState(pos).canBeReplaced()) {
                this.level().setBlockAndUpdate(pos, AABlocks.GLOW_STICK_BLOCK.get().defaultBlockState());
                this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.GLASS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else {
                ItemStack stack = new ItemStack(AAItems.GLOW_STICK_ITEM.get(), 1);
                ItemEntity entity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), stack);
                this.level().addFreshEntity(entity);
            }
        }
    }
}
