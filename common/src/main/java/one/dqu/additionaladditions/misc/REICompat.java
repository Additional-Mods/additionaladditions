package one.dqu.additionaladditions.misc;

import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.brewing.DefaultBrewingDisplay;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.*;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.feature.glint.GlintColor;
import one.dqu.additionaladditions.item.SuspiciousDyeItem;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.util.ConventionalTags;
import one.dqu.additionaladditions.util.ModCompatibility;

import java.util.*;
import java.util.function.Supplier;

public class REICompat implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        brewingRecipes(registry);
        if (Config.SUSPICIOUS_DYE.get().enabled()) {
            suspiciousDye(registry);
        }
    }

    @Override
    public void registerBasicEntryFiltering(BasicFilteringRule<?> rule) {
        List<EntryStack<ItemStack>> toHide = ConfigProperty.getAll()
                .stream()
                .filter(property -> property.get() instanceof Toggleable toggleable && !toggleable.enabled())
                .map(AAItems::fromConfigProperty)
                .flatMap(Collection::stream)
                .map(Supplier::get)
                .map(ItemStack::new)
                .map(s -> EntryStack.of(VanillaEntryTypes.ITEM, s))
                .toList();
        rule.hide(toHide);
    }

    private void brewingRecipes(DisplayRegistry registry) {
        if (!ModCompatibility.isClientSide()) return;

        @SuppressWarnings("unchecked")
        List<BrewingRecipe> recipes = ((RecipeManager) ModCompatibility.Client.getClientLevel().recipeAccess()).getRecipes().stream()
                .filter(h -> h.value().getType() == AAMisc.BREWING_RECIPE_TYPE.get())
                .map(h -> (BrewingRecipe) h.value())
                .toList();

        for (BrewingRecipe recipe : recipes) {
            ItemStack input = PotionContents.createItemStack(Items.POTION, recipe.getPotion());
            registry.add(new DefaultBrewingDisplay(
                    EntryIngredients.of(input),
                    EntryIngredients.ofIngredient(recipe.getIngredient()),
                    EntryStacks.of(recipe.getResult())
            ));
        }
    }

    private void suspiciousDye(DisplayRegistry registry) {
        List<ItemStack> validItems = new ArrayList<>();
        for (var holder : BuiltInRegistries.ITEM.getTagOrEmpty(ConventionalTags.ENCHANTABLE)) {
            ItemStack stack = new ItemStack(holder);
            if (!stack.is(AAMisc.SUSPICIOUS_DYES_TAG)) {
                stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                validItems.add(stack);
            }
        }

        List<ItemStack> dyeStacks = new ArrayList<>();
        for (var holder : BuiltInRegistries.ITEM.getTagOrEmpty(AAMisc.SUSPICIOUS_DYES_TAG)) {
            dyeStacks.add(new ItemStack(holder));
        }

        for (ItemStack dyeStack : dyeStacks) {
            if (dyeStack.getItem() instanceof SuspiciousDyeItem dyeItem) {
                DyeColor color = dyeItem.getDyeColor();

                List<ItemStack> outputs = new ArrayList<>();
                for (ItemStack input : validItems) {
                    ItemStack result = input.copy();
                    result.set(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(color));
                    outputs.add(result);
                }

                EntryIngredient slot1 = EntryIngredients.ofItemStacks(validItems);
                EntryIngredient slot2 = EntryIngredients.of(dyeStack);
                EntryIngredient outputSlot = EntryIngredients.ofItemStacks(outputs);

                List<EntryIngredient> inputs = List.of(slot1, slot2);
                List<EntryIngredient> outputList = List.of(outputSlot);

                registry.add(new DefaultCraftingDisplay(inputs, outputList, Optional.empty()) {
                    @Override
                    public int getWidth() {
                        return 2;
                    }

                    @Override
                    public int getHeight() {
                        return 1;
                    }
                });
            }
        }
    }
}
