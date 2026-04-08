package one.dqu.additionaladditions.misc;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay.TagSlotDisplay;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.feature.glint.GlintColor;
import one.dqu.additionaladditions.item.SuspiciousDyeItem;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.util.ConventionalTags;
import one.dqu.additionaladditions.util.ModCompatibility;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

@JeiPlugin
public class JEICompat implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "jei_plugin");
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

        manager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, toHide);
    }

   // BrewingRecipe
    private void brewingRecipes(IRecipeRegistration event) {
        if (!ModCompatibility.isClientSide()) return;

        IVanillaRecipeFactory factory = event.getVanillaRecipeFactory();

        @SuppressWarnings("unchecked")
        List<RecipeHolder<BrewingRecipe>> recipes = ((RecipeManager) ModCompatibility.Client.getClientLevel().recipeAccess()).getRecipes().stream()
                .filter(h -> h.value().getType() == AAMisc.BREWING_RECIPE_TYPE.get())
                .map(h -> (RecipeHolder<BrewingRecipe>) h)
                .toList();

        List<IJeiBrewingRecipe> jeiRecipes = recipes
                .stream()
                .map(holder -> {
                    BrewingRecipe recipe = holder.value();
                    ResourceLocation id = holder.id().location();

                    ItemStack input = PotionContents.createItemStack(Items.POTION, recipe.getPotion());
                    List<ItemStack> ingredients = BuiltInRegistries.ITEM.stream()
                            .map(ItemStack::new)
                            .filter(recipe.getIngredient()::test)
                            .toList();
                    ItemStack output = recipe.getResult();

                    return factory.createBrewingRecipe(List.of(input), ingredients, output, id);
                })
                .toList();

        event.addRecipes(RecipeTypes.BREWING, jeiRecipes);
    }

    private static class SuspiciousDyeExtension implements ICraftingCategoryExtension<SuspiciousDyeRecipe> {
        @Override
        public List<SlotDisplay> getIngredients(RecipeHolder<SuspiciousDyeRecipe> recipeHolder) {
            return List.of(
                    new TagSlotDisplay(ConventionalTags.ENCHANTABLE),
                    new TagSlotDisplay(AAMisc.SUSPICIOUS_DYES_TAG)
            );
        }

        public void setRecipe(RecipeHolder<SuspiciousDyeRecipe> recipeHolder, IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
            List<ItemStack> validFoilItems = new ArrayList<>();
            for (Holder<Item> item : BuiltInRegistries.ITEM.getTagOrEmpty(ConventionalTags.ENCHANTABLE)) {
                ItemStack stack = new ItemStack(item);
                if (stack.is(AAMisc.SUSPICIOUS_DYES_TAG)) continue;
                stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                validFoilItems.add(stack);
            }

            List<ItemStack> allDyes = new ArrayList<>();
            var dyeTag = BuiltInRegistries.ITEM.getTagOrEmpty(AAMisc.SUSPICIOUS_DYES_TAG);
            for (var h : dyeTag) {
                allDyes.add(new ItemStack(h));
            }

            List<ItemStack> foilInputs = new ArrayList<>(validFoilItems);
            List<ItemStack> dyeInputs = new ArrayList<>(allDyes);

            focuses.getItemStackFocuses(RecipeIngredientRole.INPUT).forEach(focus -> {
                ItemStack focusStack = focus.getTypedValue().getIngredient();
                if (focusStack.is(AAMisc.SUSPICIOUS_DYES_TAG)) {
                    dyeInputs.removeIf(s -> !ItemStack.isSameItem(s, focusStack));
                } else {
                    foilInputs.removeIf(s -> !ItemStack.isSameItem(s, focusStack));
                }
            });

            focuses.getItemStackFocuses(RecipeIngredientRole.OUTPUT).forEach(focus -> {
                ItemStack focusStack = focus.getTypedValue().getIngredient();
                foilInputs.removeIf(s -> !ItemStack.isSameItem(s, focusStack));
            });

            Set<ItemStack> finalFoilInputs = new LinkedHashSet<>();
            List<ItemStack> finalDyeInputs = new ArrayList<>();
            List<ItemStack> finalOutputs = new ArrayList<>();

            for (ItemStack dye : dyeInputs) {
                if (dye.getItem() instanceof SuspiciousDyeItem dyeItem) {
                    DyeColor color = dyeItem.getDyeColor();

                    finalDyeInputs.add(dye);

                    for (ItemStack foil : foilInputs) {
                        finalFoilInputs.add(foil);
                        ItemStack result = foil.copy();
                        result.set(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(color));
                        finalOutputs.add(result);
                    }
                }
            }

            List<List<ItemStack>> inputs = Arrays.asList(new ArrayList<>(finalFoilInputs), finalDyeInputs);

            craftingGridHelper.createAndSetInputs(builder, VanillaTypes.ITEM_STACK, inputs, 0, 0);
            craftingGridHelper.createAndSetOutputs(builder, VanillaTypes.ITEM_STACK, finalOutputs);
        }

        @Override
        public int getWidth(RecipeHolder recipeHolder) {
            return 2;
        }

        @Override
        public int getHeight(RecipeHolder recipeHolder) {
            return 2;
        }
    }
}
