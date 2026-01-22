package one.dqu.additionaladditions.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import one.dqu.additionaladditions.misc.BrewingRecipe;
import one.dqu.additionaladditions.registry.AAMisc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Mixin for the brewing stand block entity to recognize custom {@link BrewingRecipe} recipes.
 */
@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {
    /** We need level to get the recipe manager and the methods we inject into are static, so it is stored here */
    @Unique
    private static final ThreadLocal<Level> level = new ThreadLocal<>();

    /** Stores the level into the mixin threadlocal level */
    @Inject(method = "serverTick", at = @At("HEAD"))
    private static void additionaladditions$setLevel(Level level, BlockPos blockPos, BlockState blockState, BrewingStandBlockEntity brewingStandBlockEntity, CallbackInfo ci) {
        BrewingStandBlockEntityMixin.level.set(level);
    }

    /** Checks if the items in the brewing stand are a valid brewing recipe */
    @Inject(method = "isBrewable", at = @At("HEAD"), cancellable = true)
    private static void additionaladditions$isBrewable(PotionBrewing potionBrewing, NonNullList<ItemStack> items, CallbackInfoReturnable<Boolean> cir) {
        Level level = BrewingStandBlockEntityMixin.level.get();
        if (level == null) return;

        ItemStack ingredient = items.get(3);
        if (ingredient.isEmpty()) return;

        for (int i = 0; i < 3; i++) {
            ItemStack input = items.get(i);
            if (!input.isEmpty()) {
                Optional<RecipeHolder<BrewingRecipe>> recipe = level.getRecipeManager().getRecipeFor(
                        AAMisc.BREWING_RECIPE_TYPE.get(),
                        new BrewingRecipe.BrewingRecipeInput(input, ingredient),
                        level
                );

                if (recipe.isPresent()) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }

    /** Performs the recipe craft/assembly, runs after the brew progress completed. */
    @Inject(method = "doBrew", at = @At("HEAD"), cancellable = true)
    private static void additionaladditions$doBrew(Level level, BlockPos blockPos, NonNullList<ItemStack> items, CallbackInfo ci) {
        ItemStack ingredient = items.get(3);

        boolean brewed = false;

        for (int i = 0; i < 3; i++) {
            ItemStack input = items.get(i);
            if (!input.isEmpty()) {
                BrewingRecipe.BrewingRecipeInput recipeInput = new BrewingRecipe.BrewingRecipeInput(input, ingredient);

                Optional<RecipeHolder<BrewingRecipe>> recipe = level.getRecipeManager().getRecipeFor(
                        AAMisc.BREWING_RECIPE_TYPE.get(), recipeInput, level
                );

                if (recipe.isPresent()) {
                    items.set(i, recipe.get().value().assemble(recipeInput, level.registryAccess()));
                    brewed = true;
                }
            }
        }

        if (!brewed) {
            return;
        }

        ingredient.shrink(1);

        if (ingredient.getItem().hasCraftingRemainingItem()) {
            ItemStack remainder = new ItemStack(ingredient.getItem().getCraftingRemainingItem());
            if (ingredient.isEmpty()) {
                ingredient = remainder;
            } else {
                Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), remainder);
            }
        }

        items.set(3, ingredient);
        level.levelEvent(LevelEvent.SOUND_BREWING_STAND_BREW, blockPos, 0);
        ci.cancel();
    }

    /** Makes custom ingredient items placeable into the ingredient slot  */
    @Inject(method = "canPlaceItem", at = @At("HEAD"), cancellable = true)
    private void additionaladditions$canPlaceItem(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (slot != 3) {
            return;
        }

        Level level = ((BlockEntity) (Object) this).getLevel();

        boolean isCustomIngredient = level.getRecipeManager()
                .getAllRecipesFor(AAMisc.BREWING_RECIPE_TYPE.get())
                .stream()
                .anyMatch(recipe -> recipe.value().getIngredient().test(stack));

        if (isCustomIngredient) {
            cir.setReturnValue(true);
        }
    }
}
