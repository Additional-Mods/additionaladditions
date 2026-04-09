package one.dqu.additionaladditions.recipe;

import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.display.DynamicDisplayGenerator;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.plugin.common.displays.crafting.CraftingDisplay;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.feature.glint.GlintColor;
import one.dqu.additionaladditions.item.SuspiciousDyeItem;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.util.ConventionalTags;

import java.util.*;
import java.util.function.Supplier;

public class REICompat implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        if (Config.SUSPICIOUS_DYE.get().enabled()) {
            registry.registerDisplayGenerator(BuiltinPlugin.CRAFTING, new SuspiciousDyeDisplayGenerator());
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

    private static class SuspiciousDyeDisplayGenerator implements DynamicDisplayGenerator<CraftingDisplay> {

        private List<ItemStack> getValidFoilItems() {
            List<ItemStack> items = new ArrayList<>();
            for (var holder : BuiltInRegistries.ITEM.getTagOrEmpty(ConventionalTags.ENCHANTABLE)) {
                ItemStack stack = new ItemStack(holder);
                if (!stack.is(AAMisc.SUSPICIOUS_DYES_TAG)) {
                    stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                    items.add(stack);
                }
            }
            return items;
        }

        private List<ItemStack> getAllDyes() {
            List<ItemStack> dyes = new ArrayList<>();
            for (var holder : BuiltInRegistries.ITEM.getTagOrEmpty(AAMisc.SUSPICIOUS_DYES_TAG)) {
                dyes.add(new ItemStack(holder));
            }
            return dyes;
        }

        @Override
        public Optional<List<CraftingDisplay>> getRecipeFor(EntryStack<?> entry) {
            if (!(entry.getValue() instanceof ItemStack output)) return Optional.empty();
            if (output.getItem() instanceof SuspiciousDyeItem) return Optional.empty();

            // check if the output has a glint color component. if so, this is a suspicious dye result
            GlintColor glintColor = output.get(AAMisc.GLINT_COLOR_COMPONENT.get());
            if (glintColor == null) return Optional.empty();

            DyeColor color = glintColor.color();

            // find the matching dye
            ItemStack dyeStack = null;
            for (ItemStack dye : getAllDyes()) {
                if (dye.getItem() instanceof SuspiciousDyeItem dyeItem && dyeItem.getDyeColor() == color) {
                    dyeStack = dye;
                    break;
                }
            }
            if (dyeStack == null) return Optional.empty();

            // the focused item is the output, so fix the foil item to match
            ItemStack baseItem = output.copy();
            baseItem.remove(AAMisc.GLINT_COLOR_COMPONENT.get());

            EntryIngredient slot1 = EntryIngredients.of(baseItem);
            EntryIngredient slot2 = EntryIngredients.of(dyeStack);
            EntryIngredient outputSlot = EntryIngredients.of(output);

            List<EntryIngredient> inputs = List.of(slot1, slot2);
            List<EntryIngredient> outputs = List.of(outputSlot);

            return Optional.of(List.of(createDisplay(inputs, outputs)));
        }

        @Override
        public Optional<List<CraftingDisplay>> getUsageFor(EntryStack<?> entry) {
            if (!(entry.getValue() instanceof ItemStack stack)) return Optional.empty();

            // looking up usage of a suspicious dye
            if (stack.getItem() instanceof SuspiciousDyeItem dyeItem) {
                DyeColor color = dyeItem.getDyeColor();
                List<ItemStack> validItems = getValidFoilItems();

                List<ItemStack> results = new ArrayList<>();
                for (ItemStack item : validItems) {
                    ItemStack result = item.copy();
                    result.set(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(color));
                    results.add(result);
                }

                EntryIngredient slot1 = EntryIngredients.ofItemStacks(validItems);
                EntryIngredient slot2 = EntryIngredients.of(stack);
                EntryIngredient outputSlot = EntryIngredients.ofItemStacks(results);

                return Optional.of(List.of(createDisplay(List.of(slot1, slot2), List.of(outputSlot))));
            }

            // looking up usage of an enchantable item
            if (stack.is(ConventionalTags.ENCHANTABLE) && !stack.is(AAMisc.SUSPICIOUS_DYES_TAG)) {
                List<ItemStack> dyes = getAllDyes();

                ItemStack foilStack = stack.copy();
                foilStack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);

                List<ItemStack> results = new ArrayList<>();
                for (ItemStack dye : dyes) {
                    if (dye.getItem() instanceof SuspiciousDyeItem dyeItem) {
                        ItemStack result = foilStack.copy();
                        result.set(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(dyeItem.getDyeColor()));
                        results.add(result);
                    }
                }

                EntryIngredient slot1 = EntryIngredients.of(foilStack);
                EntryIngredient slot2 = EntryIngredients.ofItemStacks(dyes);
                EntryIngredient outputSlot = EntryIngredients.ofItemStacks(results);

                return Optional.of(List.of(createDisplay(List.of(slot1, slot2), List.of(outputSlot))));
            }

            return Optional.empty();
        }

        private CraftingDisplay createDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
            return new DefaultCraftingDisplay(inputs, outputs, Optional.empty()) {
                @Override
                public int getWidth() {
                    return 2;
                }

                @Override
                public int getHeight() {
                    return 1;
                }

                @Override
                public boolean isShapeless() {
                    return true;
                }

                @Override
                public DisplaySerializer<? extends Display> getSerializer() {
                    return null;
                }
            };
        }
    }
}
