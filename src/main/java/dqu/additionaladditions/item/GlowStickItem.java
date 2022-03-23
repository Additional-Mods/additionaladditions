package dqu.additionaladditions.item;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.entity.GlowStickEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GlowStickItem extends Item {
    public GlowStickItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (!Config.getBool(ConfigValues.GLOW_STICK)) { return InteractionResultHolder.fail(itemStack); }
        if (!world.isClientSide()) {
            GlowStickEntity glowStickEntity = new GlowStickEntity(world, user);
            glowStickEntity.setItem(itemStack);
            glowStickEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 0F);
            world.addFreshEntity(glowStickEntity);
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        if (!user.isCreative()) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }
}
