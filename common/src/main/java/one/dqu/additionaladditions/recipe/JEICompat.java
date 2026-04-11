package one.dqu.additionaladditions.recipe;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.feature.glint.GlintColor;
import one.dqu.additionaladditions.item.SuspiciousDyeItem;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.core.util.ConventionalTags;
import one.dqu.additionaladditions.core.util.ModCompatibility;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

@JeiPlugin
public class JEICompat implements IModPlugin {
    @Override
    public @NotNull Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "jei_plugin");
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration event) {
        // its bugged with emi so
        // noinspection ConstantValue
        if (Config.SUSPICIOUS_DYE.get().enabled() && !ModCompatibility.isModPresent("emi")) {
            event.getCraftingCategory().addExtension(SuspiciousDyeRecipe.class, new SuspiciousDyeExtension());
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration event) {
        brewingRecipes(event);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IIngredientManager manager = jeiRuntime.getIngredientManager();

        List<ItemStack> toHide = ConfigProperty.getAll()
                .stream()
                .filter(property -> property.get() instanceof Toggleable toggleable && !toggleable.enabled())
                .map(AAItems::fromConfigProperty)
                .flatMap(Collection::stream)
                .map(Supplier::get)
                .map(ItemStack::new)
                .toList();

        if (!toHide.isEmpty()) {
            manager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, toHide);
        }
    }

   // BrewingRecipe
    private void brewingRecipes(IRecipeRegistration event) {
        if (!ModCompatibility.isClientSide()) return;

        IVanillaRecipeFactory factory = event.getVanillaRecipeFactory();

        List<IJeiBrewingRecipe> jeiRecipes = BrewingRecipeCache.get()
                .stream()
                .map(holder -> {
                    BrewingRecipe recipe = holder.value();
                    Identifier id = holder.id().identifier();

                    ItemStack input = PotionContents.createItemStack(Items.POTION, recipe.getPotion());
                    List<ItemStack> ingredients = BuiltInRegistries.ITEM.stream()
                            .map(ItemStack::new)
                            .filter(recipe.getIngredient()::test)
                            .toList();
                    ItemStack output = recipe.getResult();

                    return factory.createBrewingRecipe(ingredients, List.of(input), output, id);
                })
                .toList();

        event.addRecipes(RecipeTypes.BREWING, jeiRecipes);
    }

    private static class SuspiciousDyeExtension implements ICraftingCategoryExtension<SuspiciousDyeRecipe> {
        @Override
        public List<SlotDisplay> getIngredients(RecipeHolder<SuspiciousDyeRecipe> recipeHolder) {
            List<SlotDisplay> foilStacks = new ArrayList<>();
            for (Holder<Item> item : BuiltInRegistries.ITEM.getTagOrEmpty(ConventionalTags.ENCHANTABLE)) {
                ItemStack stack = new ItemStack(item);
                if (stack.is(AAMisc.SUSPICIOUS_DYES_TAG)) continue;
                stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                foilStacks.add(new SlotDisplay.ItemStackSlotDisplay(stack));
            }

            return List.of(
                    new SlotDisplay.Composite(foilStacks),
                    new SlotDisplay.TagSlotDisplay(AAMisc.SUSPICIOUS_DYES_TAG)
            );
        }

        @Override
        public void onDisplayedIngredientsUpdate(
                RecipeHolder<SuspiciousDyeRecipe> recipeHolder,
                List<IRecipeSlotDrawable> recipeSlots,
                IFocusGroup focuses
        ) {
            ItemStack foil = ItemStack.EMPTY;
            ItemStack dye = ItemStack.EMPTY;

            for (IRecipeSlotDrawable slot : recipeSlots) {
                if (slot.getRole() != RecipeIngredientRole.INPUT) continue;
                ItemStack stack = slot.getDisplayedIngredient(VanillaTypes.ITEM_STACK).orElse(ItemStack.EMPTY);
                if (stack.isEmpty()) continue;
                if (stack.is(AAMisc.SUSPICIOUS_DYES_TAG)) {
                    dye = stack;
                } else {
                    foil = stack;
                }
            }

            if (foil.isEmpty() || dye.isEmpty()) return;
            if (!(dye.getItem() instanceof SuspiciousDyeItem dyeItem)) return;

            DyeColor color = dyeItem.getDyeColor();
            ItemStack result = foil.copy();
            result.set(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(color));

            for (IRecipeSlotDrawable slot : recipeSlots) {
                if (slot.getRole() != RecipeIngredientRole.OUTPUT) continue;
                slot.createDisplayOverrides().add(result);
            }
        }
    }
}
