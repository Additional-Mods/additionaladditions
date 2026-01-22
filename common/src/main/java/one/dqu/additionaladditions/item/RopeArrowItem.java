package one.dqu.additionaladditions.item;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.entity.RopeArrow;
import org.jetbrains.annotations.Nullable;

public class RopeArrowItem extends ArrowItem {
    public RopeArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity livingEntity, @Nullable ItemStack itemStack2) {
        return new RopeArrow(level, livingEntity, itemStack, itemStack2);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        RopeArrow ropeArrow = new RopeArrow(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1), null);
        ropeArrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return ropeArrow;
    }
}
