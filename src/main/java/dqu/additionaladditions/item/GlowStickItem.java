package dqu.additionaladditions.item;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.entity.GlowStickEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GlowStickItem extends Item {
    public GlowStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!Config.get("GlowStick")) { return TypedActionResult.fail(itemStack); }
        if (!world.isClient()) {
            GlowStickEntity glowStickEntity = new GlowStickEntity(world, user);
            glowStickEntity.setItem(itemStack);
            glowStickEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(glowStickEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.isCreative()) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
