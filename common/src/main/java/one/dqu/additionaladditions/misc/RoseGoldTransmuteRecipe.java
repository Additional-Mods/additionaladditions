package one.dqu.additionaladditions.misc;

import one.dqu.additionaladditions.registry.AAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.registry.AAMisc;

import java.util.Arrays;
import java.util.Map;

public class RoseGoldTransmuteRecipe extends CustomRecipe {
    public RoseGoldTransmuteRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        final Item[] ironEquipment = {
            Items.IRON_SWORD,
            Items.IRON_SHOVEL,
            Items.IRON_PICKAXE,
            Items.IRON_AXE,
            Items.IRON_HOE,
            Items.IRON_HELMET,
            Items.IRON_CHESTPLATE,
            Items.IRON_LEGGINGS,
            Items.IRON_BOOTS,
            Items.IRON_HORSE_ARMOR
        };

        int roseGoldCount = 0;
        int ironEquipmentCount = 0;
        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack stack = recipeInput.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(AAItems.ROSE_GOLD_INGOT.get())) {
                    roseGoldCount++;
                    continue;
                }

                if (Arrays.stream(ironEquipment).anyMatch(stack::is)) {
                    ironEquipmentCount++;
                    continue;
                }

                return false;
            }
        }

        return roseGoldCount == 1 && ironEquipmentCount == 1;
    }

    @Override
    public ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        ItemStack itemStack = recipeInput.items().stream().filter(item -> !item.isEmpty() && !item.is(AAItems.ROSE_GOLD_INGOT.get())).findFirst().orElse(ItemStack.EMPTY);
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

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

        Item resultItem = transmuteMap.get(itemStack.getItem());
        if (resultItem == null) {
            return ItemStack.EMPTY;
        }

        return itemStack.transmuteCopy(resultItem);
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i * j >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AAMisc.ROSE_GOLD_TRANSMUTE_RECIPE_SERIALIZER.get();
    }
}
