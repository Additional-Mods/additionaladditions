package dqu.additionaladditions.entity;

import dqu.additionaladditions.registry.AdditionalBlocks;
import dqu.additionaladditions.block.GlowStickBlock;
import dqu.additionaladditions.registry.AdditionalItems;
import dqu.additionaladditions.registry.AdditionalEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GlowStickEntity extends ThrownItemEntity {
    public GlowStickEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public GlowStickEntity(World world, LivingEntity owner) {
        super(AdditionalEntities.GLOW_STICK_ENTITY_ENTITY_TYPE, owner, world);
    }

    public GlowStickEntity(World world, double x, double y, double z) {
        super(AdditionalEntities.GLOW_STICK_ENTITY_ENTITY_TYPE, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return AdditionalItems.GLOW_STICK_ITEM;
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient()) {
            this.remove(RemovalReason.DISCARDED);
            BlockPos pos = new BlockPos(this.getX(), this.getY(), this.getZ());
            if (this.world.getBlockState(pos).isAir()) {
                this.world.setBlockState(pos, AdditionalBlocks.GLOW_STICK_BLOCK.getDefaultState()
                    .with(GlowStickBlock.FLIPPED, world.getRandom().nextBoolean()));
                this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            } else {
                ItemStack stack = new ItemStack(AdditionalItems.GLOW_STICK_ITEM, 1);
                ItemEntity entity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);
                this.world.spawnEntity(entity);
            }
        }
    }
}
