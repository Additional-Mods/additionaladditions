package one.dqu.additionaladditions.item;

import one.dqu.additionaladditions.config.Config;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
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
        if (!Config.DEPTH_METER.get().enabled()) {
            return super.use(world, user, hand);
        }

        if (!Config.DEPTH_METER_DISPLAY_ALWAYS.get().enabled()) {
            ItemStack itemStack = user.getItemInHand(hand);
            user.displayClientMessage(MutableComponent.create(new TranslatableContents("depth_meter.elevation", null, new String[]{String.valueOf(user.getBlockY())})), true);
            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        }

        return super.use(world, user, hand);
    }
}
