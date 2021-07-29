package dqu.additionaladditions.item;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class WateringCan extends Item {
    public WateringCan(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        BlockHitResult hitResult = raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (hitResult.getType() != HitResult.Type.BLOCK) return TypedActionResult.fail(stack);
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof Fertilizable fertilizable && !(state.getBlock() instanceof GrassBlock)) {
            if (stack.getDamage() > 0 || player.isCreative()) {
                player.playSound(SoundEvents.ITEM_BONE_MEAL_USE, 1.0F, 1.5F);
                if (world.isClient()) return TypedActionResult.success(stack);
                if (fertilizable.canGrow(world, world.random, pos, state)) {
                    if (world.random.nextFloat() < 0.25)
                        fertilizable.grow((ServerWorld) world, world.random, pos, state);
                    stack.setDamage(stack.getDamage()-10);
                    return TypedActionResult.success(stack);
                }
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
        return MathHelper.packRgb(65, 135, 235);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.min(Math.round(13 * stack.getDamage() / 100.0F), 13);
    }
}
