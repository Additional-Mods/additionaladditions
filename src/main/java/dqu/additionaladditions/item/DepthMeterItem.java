package dqu.additionaladditions.item;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DepthMeterItem extends Item {
    public DepthMeterItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!Config.getBool(ConfigValues.DEPTH_METER, "enabled")) {
            return super.use(world, user, hand);
        }

        if (!Config.getBool(ConfigValues.DEPTH_METER, "displayElevationAlways")) {
            ItemStack itemStack = user.getStackInHand(hand);
            user.sendMessage(new TranslatableText("depth_meter.elevation", user.getBlockY()), true);
            return TypedActionResult.success(itemStack, world.isClient());
        }

        return super.use(world, user, hand);
    }
}
