package dqu.additionaladditions.item;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DepthMeterItem extends Item {
    public DepthMeterItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!Config.getBool(ConfigValues.DEPTH_METER, "enabled")) {
            return super.use(world, user, hand);
        }

        if (!Config.getBool(ConfigValues.DEPTH_METER, "displayElevationAlways")) {
            ItemStack itemStack = user.getItemInHand(hand);
            user.displayClientMessage(new TranslatableComponent("depth_meter.elevation", user.getBlockY()), true);
            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        }

        return super.use(world, user, hand);
    }
}
