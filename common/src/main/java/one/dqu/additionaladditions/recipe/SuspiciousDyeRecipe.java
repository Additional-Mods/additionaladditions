package one.dqu.additionaladditions.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.feature.dye.SuspiciousDyeItem;
import one.dqu.additionaladditions.feature.glint.GlintColor;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.registry.AATags;

import java.util.List;

public class SuspiciousDyeRecipe extends CustomRecipe {
    public static final SuspiciousDyeRecipe INSTANCE = new SuspiciousDyeRecipe();
    public static final RecipeSerializer<SuspiciousDyeRecipe> SERIALIZER = new RecipeSerializer<>(
            MapCodec.unit(INSTANCE),
            StreamCodec.unit(INSTANCE)
    );

    public SuspiciousDyeRecipe() {
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        int glint = 0;
        int dye = 0;

        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack stack = recipeInput.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(AATags.SUSPICIOUS_DYES)) {
                    dye++;
                    continue;
                }
                if (stack.hasFoil() && stack.is(AATags.C_ENCHANTABLE)) {
                    glint++;
                    continue;
                }

                return false;
            }
        }

        return glint == 1 && dye == 1;
    }

    @Override
    public ItemStack assemble(CraftingInput recipeInput) {
        ItemStack itemStack = recipeInput.items().stream().filter(item -> !item.isEmpty() && !item.is(AATags.SUSPICIOUS_DYES)).findFirst().orElse(ItemStack.EMPTY);
        ItemStack dye = recipeInput.items().stream().filter(item -> !item.isEmpty() && item.is(AATags.SUSPICIOUS_DYES)).findFirst().orElse(ItemStack.EMPTY);

        if (itemStack.isEmpty() || dye.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (dye.getItem() instanceof SuspiciousDyeItem suspiciousDyeItem) {
            DyeColor color = suspiciousDyeItem.getDyeColor();
            ItemStack copyStack = itemStack.copy();
            copyStack.set(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(color));
            return copyStack;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return AAMisc.SUSPICIOUS_DYE_RECIPE_SERIALIZER.get();
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(new ShapelessCraftingRecipeDisplay(
                List.of(
                        new SlotDisplay.TagSlotDisplay(AATags.C_ENCHANTABLE),
                        new SlotDisplay.TagSlotDisplay(AATags.SUSPICIOUS_DYES)
                ),
                SlotDisplay.Empty.INSTANCE,
                new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
        ));
    }
}
