package dqu.additionaladditions.item;

import dqu.additionaladditions.Config;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WateringCanItem extends Item {
    public WateringCanItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!Config.get("WateringCan")) { return TypedActionResult.fail(stack); }
        BlockHitResult hitResult = raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (hitResult.getType() != HitResult.Type.BLOCK) return TypedActionResult.fail(stack);
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = world.getBlockState(pos);
        BlockPos posBelow = pos.offset(Direction.DOWN);
        BlockState stateBelow = world.getBlockState(posBelow);

        if (stack.getDamage() > 0 || player.isCreative()) {
            if (state.getBlock() instanceof Fertilizable fertilizable && !(state.getBlock() instanceof GrassBlock)) {
                player.playSound(SoundEvents.ITEM_BONE_MEAL_USE, 1.0F, 1.5F);
                if (world.isClient()) return TypedActionResult.success(stack);
                if (fertilizable.canGrow(world, world.random, pos, state)) {
                    if (world.random.nextFloat() < 0.25)
                        fertilizable.grow((ServerWorld) world, world.random, pos, state);
                }

                if (stateBelow.getBlock() instanceof FarmlandBlock) {
                    world.setBlockState(posBelow, stateBelow.with(Properties.MOISTURE, FarmlandBlock.MAX_MOISTURE));
                }

                stack.setDamage(stack.getDamage() - 10);
                return TypedActionResult.success(stack);
            }

            if (state.getBlock() instanceof FarmlandBlock) {
                if (world.isClient()) return TypedActionResult.success(stack);
                world.setBlockState(pos, state.with(Properties.MOISTURE, FarmlandBlock.MAX_MOISTURE));
                stack.setDamage(stack.getDamage() - 10);
                return TypedActionResult.success(stack);
            }
        }

        if (state.getBlock() instanceof FluidDrainable fluid && state.getMaterial() == Material.WATER) {
            if (stack.getDamage() == 100) return TypedActionResult.fail(stack);
            fluid.getBucketFillSound().ifPresent((sound) -> player.playSound(sound, 1.0F, 1.0F));
            if (!world.isClient()) {
                stack.setDamage(100);
                fluid.tryDrainFluid(world, pos, state);
                player.swingHand(hand);
            }
            return TypedActionResult.success(stack);
        }

        return TypedActionResult.fail(stack);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return Config.get("WateringCan") ? MathHelper.packRgb(65, 135, 235) : MathHelper.packRgb(235, 135, 65);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Config.get("WateringCan") ? Math.min(Math.round(13 * stack.getDamage() / 100.0F), 13) : 13;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        int water = (int) (stack.getDamage() * 0.1);
        String tooltipText = String.format(": %s / 10", water);
        tooltip.add(new TranslatableText("block.minecraft.water").append(tooltipText).formatted(Formatting.AQUA) );
    }
}
