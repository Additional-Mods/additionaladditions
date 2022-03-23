package dqu.additionaladditions.item;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WateringCanItem extends Item {
    public WateringCanItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!Config.getBool(ConfigValues.WATERING_CAN)) { return InteractionResultHolder.fail(stack); }
        BlockHitResult hitResult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hitResult.getType() != HitResult.Type.BLOCK) return InteractionResultHolder.fail(stack);
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = world.getBlockState(pos);
        BlockPos posBelow = pos.relative(Direction.DOWN);
        BlockState stateBelow = world.getBlockState(posBelow);

        if (stack.getDamageValue() > 0 || player.isCreative()) {
            if (state.getBlock() instanceof BonemealableBlock fertilizable && !(state.getBlock() instanceof GrassBlock)) {
                player.playSound(SoundEvents.BONE_MEAL_USE, 1.0F, 1.5F);
                if (world.isClientSide()) return InteractionResultHolder.success(stack);
                if (fertilizable.isBonemealSuccess(world, world.random, pos, state)) {
                    if (world.random.nextFloat() < 0.25)
                        fertilizable.performBonemeal((ServerLevel) world, world.random, pos, state);
                }

                if (stateBelow.getBlock() instanceof FarmBlock) {
                    world.setBlockAndUpdate(posBelow, stateBelow.setValue(BlockStateProperties.MOISTURE, FarmBlock.MAX_MOISTURE));
                }

                stack.setDamageValue(stack.getDamageValue() - 10);
                return InteractionResultHolder.success(stack);
            }

            if (state.getBlock() instanceof FarmBlock) {
                if (world.isClientSide()) return InteractionResultHolder.success(stack);
                world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.MOISTURE, FarmBlock.MAX_MOISTURE));
                stack.setDamageValue(stack.getDamageValue() - 10);
                return InteractionResultHolder.success(stack);
            }
        }

        if (state.getBlock() instanceof BucketPickup fluid && state.getMaterial() == Material.WATER) {
            if (stack.getDamageValue() == 100) return InteractionResultHolder.fail(stack);
            fluid.getPickupSound().ifPresent((sound) -> player.playSound(sound, 1.0F, 1.0F));
            if (!world.isClientSide()) {
                stack.setDamageValue(100);
                fluid.pickupBlock(world, pos, state);
                player.swing(hand);
            }
            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return Config.getBool(ConfigValues.WATERING_CAN) ? Mth.color(65, 135, 235) : Mth.color(235, 135, 65);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Config.getBool(ConfigValues.WATERING_CAN) ? Math.min(Math.round(13 * stack.getDamageValue() / 100.0F), 13) : 13;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        int water = (int) (stack.getDamageValue() * 0.1);
        String tooltipText = String.format(": %s / 10", water);
        tooltip.add(new TranslatableComponent("block.minecraft.water").append(tooltipText).withStyle(ChatFormatting.AQUA) );
    }
}
