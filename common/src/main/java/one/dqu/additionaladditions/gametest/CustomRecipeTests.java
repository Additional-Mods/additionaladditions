package one.dqu.additionaladditions.gametest;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintColor;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;

import java.util.List;
import java.util.Optional;

public class CustomRecipeTests {
    // dyes an enchanted item with suspicious dye, adding a glint_color component
    public static void suspiciousDyeing(GameTestHelper ctx) {
        ItemStack stack = new ItemStack(Items.NETHERITE_CHESTPLATE);
        Holder<Enchantment> enchantment = ctx.getLevel().registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING);
        stack.enchant(enchantment, 1);

        ItemStack dye = AAItems.WHITE_SUSPICIOUS_DYE.get().getDefaultInstance();

        CraftingInput input = CraftingInput.of(2, 1, List.of(stack, dye));
        Optional<RecipeHolder<CraftingRecipe>> recipe = ctx.getLevel().recipeAccess()
                .getRecipeFor(RecipeType.CRAFTING, input, ctx.getLevel());
        ctx.assertTrue(recipe.isPresent(), Component.literal("no crafting recipe matched suspicious dye + enchanted gear"));

        ItemStack result = recipe.get().value().assemble(input);
        ctx.assertTrue(result.is(Items.NETHERITE_CHESTPLATE), Component.literal("result is not the same equipment item"));

        GlintColor glint = result.get(AAMisc.GLINT_COLOR_COMPONENT.get());
        ctx.assertTrue(glint != null && glint.color() == DyeColor.WHITE, Component.literal("result is missing the white glint_color component"));

        ctx.succeed();
    }
}
