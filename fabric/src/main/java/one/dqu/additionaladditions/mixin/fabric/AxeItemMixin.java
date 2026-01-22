package one.dqu.additionaladditions.mixin.fabric;

import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.registry.AABlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Drops copper patina when scraping copper blocks with an axe.
 */
@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Inject(method = "evaluateNewBlockState", at = @At(value = "FIELD", target = "Lnet/minecraft/sounds/SoundEvents;AXE_SCRAPE:Lnet/minecraft/sounds/SoundEvent;"))
    private void spawnCopperPatina(Level world, BlockPos blockPos, Player player, BlockState blockState, CallbackInfoReturnable<Optional<BlockState>> cir) {
        if (!Config.COPPER_PATINA.get().enabled()) return;
        ItemStack stack = new ItemStack(AABlocks.COPPER_PATINA.get().asItem());
        ItemEntity itemEntity = new ItemEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
        world.addFreshEntity(itemEntity);
    }
}
