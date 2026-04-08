package one.dqu.additionaladditions.misc;

import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.display.DynamicDisplayGenerator;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import me.shedaniel.rei.plugin.common.displays.brewing.DefaultBrewingDisplay;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
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
        if (Config.ALBUM.get().enabled() && Config.RECIPE_VIEWER.get().albumDyeing()) {
            registerAlbumRecipes(registry);
        }
        if (Config.ROSE_GOLD.get().enabled() && Config.RECIPE_VIEWER.get().roseGold()) {
            roseGoldTransmuteRecipe(registry);
        }
        brewingRecipes(registry);
        if (Config.SUSPICIOUS_DYE.get().enabled() && Config.RECIPE_VIEWER.get().suspiciousDyeing()) {
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

    private static class SuspiciousDyeDisplayGenerator implements DynamicDisplayGenerator<DefaultCraftingDisplay<?>> {

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
        public Optional<List<DefaultCraftingDisplay<?>>> getRecipeFor(EntryStack<?> entry) {
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
        public Optional<List<DefaultCraftingDisplay<?>>> getUsageFor(EntryStack<?> entry) {
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

        private DefaultCraftingDisplay<?> createDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
            return new DefaultCraftingDisplay(inputs, outputs, Optional.empty()) {
                @Override
                public int getWidth() {
                    return 2;
                }

                @Override
                public int getHeight() {
                    return 1;
                }
            };
        }
    }

    // copy pasted from JEICompat
    private void registerAlbumRecipes(DisplayRegistry registry) {
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();

        // copied from albumdyerecipe
        final Map<DyeColor, Item> dyeToAlbumMap = Map.ofEntries(
                Map.entry(DyeColor.WHITE, AAItems.WHITE_ALBUM.get()),
                Map.entry(DyeColor.LIGHT_GRAY, AAItems.LIGHT_GRAY_ALBUM.get()),
                Map.entry(DyeColor.GRAY, AAItems.GRAY_ALBUM.get()),
                Map.entry(DyeColor.BLACK, AAItems.BLACK_ALBUM.get()),
                Map.entry(DyeColor.BROWN, AAItems.BROWN_ALBUM.get()),
                Map.entry(DyeColor.RED, AAItems.RED_ALBUM.get()),
                Map.entry(DyeColor.ORANGE, AAItems.ORANGE_ALBUM.get()),
                Map.entry(DyeColor.YELLOW, AAItems.YELLOW_ALBUM.get()),
                Map.entry(DyeColor.LIME, AAItems.LIME_ALBUM.get()),
                Map.entry(DyeColor.GREEN, AAItems.GREEN_ALBUM.get()),
                Map.entry(DyeColor.CYAN, AAItems.CYAN_ALBUM.get()),
                Map.entry(DyeColor.LIGHT_BLUE, AAItems.LIGHT_BLUE_ALBUM.get()),
                Map.entry(DyeColor.BLUE, AAItems.BLUE_ALBUM.get()),
                Map.entry(DyeColor.PURPLE, AAItems.PURPLE_ALBUM.get()),
                Map.entry(DyeColor.MAGENTA, AAItems.MAGENTA_ALBUM.get()),
                Map.entry(DyeColor.PINK, AAItems.PINK_ALBUM.get())
        );

        for (Map.Entry<DyeColor, Item> entry : dyeToAlbumMap.entrySet()) {
            DyeColor color = entry.getKey();
            Item result = entry.getValue();

            Ingredient dyeIngredient = Ingredient.of(DyeItem.byColor(color));

            // list of albums excluding the result album (cant dye to same color)
            List<ItemStack> validAlbums = new ArrayList<>();
            var albums = BuiltInRegistries.ITEM.getTagOrEmpty(AAMisc.ALBUMS_TAG);
            for (Holder<Item> albumItem : albums) {
                if (albumItem.value() != result) {
                    validAlbums.add(new ItemStack(albumItem.value()));
                }
            }
            Ingredient albumIngredient = Ingredient.of(validAlbums.toArray(new ItemStack[0]));

            NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, albumIngredient, dyeIngredient);
            ShapelessRecipe recipe = new ShapelessRecipe(
                    "additionaladditions:/jei_albums",
                    CraftingBookCategory.MISC,
                    new ItemStack(result),
                    inputs
            );

            ResourceLocation location = ResourceLocation.fromNamespaceAndPath("additionaladditions", "/jei_album_" + color.getName());
            recipes.add(new RecipeHolder<>(location, recipe));
        }

        for (RecipeHolder<CraftingRecipe> recipeHolder : recipes) {
            registry.add(DefaultCraftingDisplay.of(recipeHolder));
        }
    }

    // copy pasted from JEICompat
    private void roseGoldTransmuteRecipe(DisplayRegistry registry) {
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();

        // copied from RoseGoldTransmuteRecipe
        final Map<Item, Item> transmuteMap = Map.of(
                Items.IRON_SWORD, AAItems.ROSE_GOLD_SWORD.get(),
                Items.IRON_SHOVEL, AAItems.ROSE_GOLD_SHOVEL.get(),
                Items.IRON_PICKAXE, AAItems.ROSE_GOLD_PICKAXE.get(),
                Items.IRON_AXE, AAItems.ROSE_GOLD_AXE.get(),
                Items.IRON_HOE, AAItems.ROSE_GOLD_HOE.get(),
                Items.IRON_HELMET, AAItems.ROSE_GOLD_HELMET.get(),
                Items.IRON_CHESTPLATE, AAItems.ROSE_GOLD_CHESTPLATE.get(),
                Items.IRON_LEGGINGS, AAItems.ROSE_GOLD_LEGGINGS.get(),
                Items.IRON_BOOTS, AAItems.ROSE_GOLD_BOOTS.get(),
                Items.IRON_HORSE_ARMOR, AAItems.ROSE_GOLD_HORSE_ARMOR.get()
        );

        for (Map.Entry<Item, Item> entry : transmuteMap.entrySet()) {
            Item ironItem = entry.getKey();
            String ironId = BuiltInRegistries.ITEM.getKey(ironItem).getPath();
            Item roseGoldItem = entry.getValue();

            Ingredient ironIngredient = Ingredient.of(ironItem);
            Ingredient roseGoldIngotIngredient = Ingredient.of(AAItems.ROSE_GOLD_INGOT.get());

            NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, ironIngredient, roseGoldIngotIngredient);
            ShapelessRecipe recipe = new ShapelessRecipe(
                    "additionaladditions:/jei_rose_gold_transmute_" + ironId,
                    CraftingBookCategory.EQUIPMENT,
                    new ItemStack(roseGoldItem),
                    inputs
            );

            ResourceLocation location = ResourceLocation.fromNamespaceAndPath("additionaladditions", "/jei_rose_gold_transmute_" + ironId);
            recipes.add(new RecipeHolder<>(location, recipe));
        }

        for (RecipeHolder<CraftingRecipe> recipeHolder : recipes) {
            registry.add(DefaultCraftingDisplay.of(recipeHolder));
        }
    }

    private void brewingRecipes(DisplayRegistry registry) {
        if (!ModCompatibility.isClientSide()) return;

        List<BrewingRecipe> recipes = ModCompatibility.Client.getClientLevel().getRecipeManager()
                .getAllRecipesFor(AAMisc.BREWING_RECIPE_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();

        for (BrewingRecipe recipe : recipes) {
            registry.add(new DefaultBrewingDisplay(recipe.getInput(), recipe.getIngredient(), recipe.getResult()));
        }
    }
}
