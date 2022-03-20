package dqu.additionaladditions.item;

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
        ItemStack itemStack = user.getStackInHand(hand);
        user.sendMessage(new TranslatableText("depth_meter.elevation", user.getBlockY()), true);
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
