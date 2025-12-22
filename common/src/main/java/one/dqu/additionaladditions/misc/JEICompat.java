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
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.glint.GlintColor;
import one.dqu.additionaladditions.item.SuspiciousDyeItem;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@JeiPlugin
public class JEICompat implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "jei_plugin");
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration event) {
        if (Config.SUSPICIOUS_DYES.get().enabled()) {
            event.getCraftingCategory().addExtension(SuspiciousDyeRecipe.class, new SuspiciousDyeExtension());
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration event) {
        if (Config.ALBUM.get().enabled()) {
            albumDyeRecipe(event);
        }
        if (Config.ROSE_GOLD.get().enabled()) {
            roseGoldTransmuteRecipe(event);
        }
    }

    // AlbumDyeRecipe
    private void albumDyeRecipe(IRecipeRegistration event) {
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

        event.addRecipes(RecipeTypes.CRAFTING, recipes);
    }

    // RoseGoldTransmuteRecipe
    private void roseGoldTransmuteRecipe(IRecipeRegistration event) {
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

        event.addRecipes(RecipeTypes.CRAFTING, recipes);
    }

    private static class SuspiciousDyeExtension implements ICraftingCategoryExtension<SuspiciousDyeRecipe> {
        @Override
        public void setRecipe(RecipeHolder<SuspiciousDyeRecipe> recipeHolder, IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
            List<ItemStack> validFoilItems = new ArrayList<>();
            for (Item item : BuiltInRegistries.ITEM) {
                ItemStack stack = new ItemStack(item);
                if (stack.is(AAMisc.SUSPICIOUS_DYES_TAG)) continue;
                if (stack.hasFoil()) {
                    validFoilItems.add(stack);
                } else if (stack.isEnchantable()) {
                    stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                    validFoilItems.add(stack);
                }
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
