package one.dqu.additionaladditions.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.registry.AAItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Drops copper patina when scraping copper blocks with an axe.
 */
@Mixin(BlockTransformer.class)
public class BlockTransformerMixin {
    @Inject(method = "transformBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/component/BlockTransformer$TransformParticle;send(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;)V"))
    private void spawnCopperPatina(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir, @Local(name = "oldBlockState") BlockState oldBlockState, @Local(name = "newBlockState") BlockState newBlockState) {
        if (!Config.COPPER_PATINA.get().enabled()) return;
        if (WeatheringCopper.getPrevious(oldBlockState.getBlock()).filter(newBlockState::is).isEmpty()) return;
        Block.popResource(context.getLevel(), context.getClickedPos(), new ItemStack(AAItems.COPPER_PATINA.get()));
    }
}
