package one.dqu.additionaladditions.item;

import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.item.configurable.ConfigurableItem;
import one.dqu.additionaladditions.misc.FluidHelper;
import one.dqu.additionaladditions.registry.AAMisc;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WateringCanItem extends ConfigurableItem {
    public WateringCanItem(Properties settings) {
        super(settings, builder -> {});
    }

    private int getWaterLevel(ItemStack stack) {
        return stack.getOrDefault(AAMisc.WATER_LEVEL_COMPONENT.get(), 0);
    }

    private void setWaterLevel(ItemStack stack, int level) {
        int maxWaterLevel = Config.WATERING_CAN.get().maxWaterLevel();
        stack.set(AAMisc.WATER_LEVEL_COMPONENT.get(), Mth.clamp(level, 0, maxWaterLevel));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!Config.WATERING_CAN.get().enabled()) {
            return InteractionResultHolder.fail(stack);
        }

        BlockHitResult hitResult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.fail(stack);
        }

        BlockPos pos = hitResult.getBlockPos();
        BlockState state = world.getBlockState(pos);
        BlockPos posBelow = pos.relative(Direction.DOWN);
        BlockState stateBelow = world.getBlockState(posBelow);

        int waterLevel = getWaterLevel(stack);
        int maxWaterLevel = Config.WATERING_CAN.get().maxWaterLevel();

        // watering
        if (waterLevel > 0 || player.isCreative()) {
            if (state.getBlock() instanceof BonemealableBlock fertilizable && !(state.getBlock() instanceof GrassBlock)) {
                player.playSound(SoundEvents.BONE_MEAL_USE, 1.0F, 1.5F);
                if (world.isClientSide()) {
                    return InteractionResultHolder.success(stack);
                }

                if (fertilizable.isBonemealSuccess(world, world.random, pos, state)) {
                    if (world.random.nextFloat() < 0.25) {
                        fertilizable.performBonemeal((ServerLevel) world, world.random, pos, state);
                    }
                }

                if (stateBelow.getBlock() instanceof FarmBlock) {
                    world.setBlockAndUpdate(posBelow, stateBelow.setValue(BlockStateProperties.MOISTURE, FarmBlock.MAX_MOISTURE));
                }

                if (!player.isCreative()) {
                    setWaterLevel(stack, waterLevel - 1);
                }
                return InteractionResultHolder.success(stack);
            }

            if (state.getBlock() instanceof FarmBlock) {
                player.playSound(SoundEvents.BOTTLE_EMPTY, 1.0F, 1.0F);
                if (world.isClientSide()) {
                    return InteractionResultHolder.success(stack);
                }

                world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.MOISTURE, FarmBlock.MAX_MOISTURE));
                if (!player.isCreative()) {
                    setWaterLevel(stack, waterLevel - 1);
                }
                return InteractionResultHolder.success(stack);
            }
        }

        // filling
        if (waterLevel < maxWaterLevel) {
            int waterNeeded = maxWaterLevel - waterLevel;
            int millibuckets = waterNeeded * 100;

            FluidHelper.Transaction transaction = FluidHelper.transaction(player, pos, hitResult.getDirection(), millibuckets, Fluids.WATER);

            int waterGained = transaction.amount() / 100;
            if (waterGained > 0) {
                if (!world.isClientSide()) {
                    transaction.commitAmount(waterGained * 100);
                    setWaterLevel(stack, waterLevel + waterGained);
                }
                return InteractionResultHolder.success(stack);
            }
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return Config.WATERING_CAN.get().enabled() ? FastColor.ARGB32.color(0, 65, 135, 235) : Mth.color(235, 135, 65);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int waterLevel = getWaterLevel(stack);
        int maxWaterLevel = Config.WATERING_CAN.get().maxWaterLevel();
        return Config.WATERING_CAN.get().enabled() ? Math.min(Math.round(13 * waterLevel / (float) maxWaterLevel), 13) : 13;
    }
}
