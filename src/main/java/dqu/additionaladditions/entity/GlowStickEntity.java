package dqu.additionaladditions.entity;

import dqu.additionaladditions.registry.AdditionalBlocks;
import dqu.additionaladditions.block.GlowStickBlock;
import dqu.additionaladditions.registry.AdditionalItems;
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
import dqu.additionaladditions.registry.AdditionalEntities;

public class GlowStickEntity extends ThrowableItemProjectile {
    public GlowStickEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public GlowStickEntity(Level world, LivingEntity owner) {
        super(AdditionalEntities.GLOW_STICK_ENTITY_ENTITY_TYPE, owner, world);
    }

    public GlowStickEntity(Level world, double x, double y, double z) {
        super(AdditionalEntities.GLOW_STICK_ENTITY_ENTITY_TYPE, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return AdditionalItems.GLOW_STICK_ITEM;
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide()) {
            this.remove(RemovalReason.DISCARDED);
            BlockPos pos = BlockPos.containing(this.getX(), this.getY(), this.getZ());
            if (this.level.getBlockState(pos).isAir()) {
                this.level.setBlockAndUpdate(pos, AdditionalBlocks.GLOW_STICK_BLOCK.defaultBlockState()
                    .setValue(GlowStickBlock.FLIPPED, level.getRandom().nextBoolean()));
                this.level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.GLASS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else {
                ItemStack stack = new ItemStack(AdditionalItems.GLOW_STICK_ITEM, 1);
                ItemEntity entity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), stack);
                this.level.addFreshEntity(entity);
            }
        }
    }
}
