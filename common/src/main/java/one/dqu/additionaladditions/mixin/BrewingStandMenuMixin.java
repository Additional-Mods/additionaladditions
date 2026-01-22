package one.dqu.additionaladditions.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import one.dqu.additionaladditions.misc.BrewingRecipe;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.util.ModCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the brewing stand menu to recognize custom {@link BrewingRecipe} recipes.
 */
@Mixin(targets = "net.minecraft.world.inventory.BrewingStandMenu$IngredientsSlot")
public class BrewingStandMenuMixin {
    @Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
    private void additionaladditions$mayPlace(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        Level level = null;
        if (ModCompatibility.isClientSide()) {
            level = ModCompatibility.Client.getClientLevel();
        } else {
            Container container = ((Slot) (Object) this).container;
            if (container instanceof BrewingStandBlockEntity brewingStand) {
                level = brewingStand.getLevel();
            }
        }
        if (level == null) return;

        boolean isValidIngredient = level.getRecipeManager()
                .getAllRecipesFor(AAMisc.BREWING_RECIPE_TYPE.get())
                .stream()
                .anyMatch(recipe -> recipe.value().getIngredient().test(itemStack));

        if (isValidIngredient) {
            cir.setReturnValue(true);
        }
    }
}
