package one.dqu.additionaladditions.item;

import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.item.configurable.ConfigurableItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class WrenchItem extends ConfigurableItem {
    public WrenchItem(Properties settings) {
        super(settings, builder -> {
            builder.set(DataComponents.MAX_DAMAGE, Config.WRENCH.get().durability());
        });
    }

    private boolean tryPlacing(BlockPos pos, BlockState state, Level world, ItemStack stack, Optional<Player> player, Optional<InteractionHand> hand) {
        if (state.canSurvive(world, pos) && state.getBlock().defaultDestroyTime() >= 0) {
            world.setBlockAndUpdate(pos, state);

            if (world.isClientSide()) {
                return true;
            }

            player.ifPresentOrElse((pl) -> {
                if (!pl.isCreative()) {
                    EquipmentSlot slot = hand.map(h -> h == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND)
                            .orElse(EquipmentSlot.MAINHAND);
                    stack.hurtAndBreak(1, pl, slot);
                }
            }, () -> {
                if (world instanceof ServerLevel serverLevel) {
                    stack.hurtAndBreak(1, serverLevel, null, item -> {});
                }
            });

            world.playSound(null, pos, SoundEvents.SPYGLASS_USE, SoundSource.AMBIENT, 2.0F, 1.0F);

            return true;
        }
        return false;
    }

    private InteractionResult rotate(BlockPos pos, BlockState state, Level world, ItemStack stack, Optional<Player> player, Optional<InteractionHand> hand) {
        if (!Config.WRENCH.get().enabled()) { return InteractionResult.FAIL; }

        if (world.isClientSide()) return InteractionResult.PASS;
        if (state.getBlock() instanceof ChestBlock || state.getBlock() instanceof BedBlock) return InteractionResult.PASS;

        if (state.hasProperty(BlockStateProperties.FACING)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.FACING), world, stack, player, hand)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (state.hasProperty(BlockStateProperties.FACING_HOPPER)) {
            BlockState newstate = state.cycle(BlockStateProperties.FACING_HOPPER);
            if (tryPlacing(pos, newstate, world, stack, player, hand)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.HORIZONTAL_FACING), world, stack, player, hand)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (state.hasProperty(BlockStateProperties.AXIS)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.AXIS), world, stack, player, hand)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (state.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            if (tryPlacing(pos, state.cycle(BlockStateProperties.HORIZONTAL_AXIS), world, stack, player, hand)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (state.getBlock() instanceof SlabBlock) {
            BlockState newState = state;
            if (state.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.DOUBLE)) {
                return InteractionResult.PASS;
            }
            if (state.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.BOTTOM)) {
                newState = state.setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP);
            }
            if (state.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.TOP)) {
                newState = state.setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM);
            }

            if (tryPlacing(pos, newState, world, stack, player, hand)) {
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        return rotate(
                context.getClickedPos(),
                context.getLevel().getBlockState(context.getClickedPos()),
                context.getLevel(),
                context.getItemInHand(),
                Optional.ofNullable(context.getPlayer()),
                Optional.of(context.getHand())
        );
    }

    public void dispenserUse(Level world, BlockPos pos, BlockState state, ItemStack stack) {
        rotate(pos, state, world, stack, Optional.empty(), Optional.empty());
    }
}