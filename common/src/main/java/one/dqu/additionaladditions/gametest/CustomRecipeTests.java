package one.dqu.additionaladditions.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintColor;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;

import java.util.List;
import java.util.Optional;

public class CustomRecipeTests {
    private static boolean containerHas(GameTestHelper ctx, BlockPos relPos, Item item) {
        BlockEntity be = ctx.getLevel().getBlockEntity(ctx.absolutePos(relPos));
        if (!(be instanceof Container container)) return false;
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (container.getItem(i).is(item)) return true;
        }
        return false;
    }

    // brews a water bottle + cottonshiver into white suspicious dye
    public static void brewing(GameTestHelper ctx) {
        BlockPos floor = new BlockPos(2, 1, 2);
        BlockPos bottomHopper = new BlockPos(2, 2, 2);
        BlockPos stand = new BlockPos(2, 3, 2);
        BlockPos topHopper = new BlockPos(2, 4, 2);
        BlockPos sideHopper = new BlockPos(3, 3, 2);
        BlockPos redstone = new BlockPos(1, 2, 2);

        // brewing setup with hoppers
        ctx.setBlock(floor, Blocks.STONE);
        ctx.setBlock(bottomHopper, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, Direction.DOWN));
        ctx.setBlock(stand, Blocks.BREWING_STAND);
        ctx.setBlock(topHopper, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, Direction.DOWN));
        ctx.setBlock(sideHopper, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, Direction.WEST));
        ctx.setBlock(redstone, Blocks.REDSTONE_BLOCK); // lock the bottom hopper so it cant pull the bottle out of the stand

        HopperBlockEntity top = ctx.getBlockEntity(topHopper, HopperBlockEntity.class);
        top.setItem(0, new ItemStack(AAItems.COTTONSHIVER.get()));
        top.setChanged();

        HopperBlockEntity side = ctx.getBlockEntity(sideHopper, HopperBlockEntity.class);
        side.setItem(0, new ItemStack(Items.BLAZE_POWDER));
        side.setItem(1, PotionContents.createItemStack(Items.POTION, Potions.WATER));
        side.setChanged();

        Item dye = AAItems.WHITE_SUSPICIOUS_DYE.get();

        ctx.startSequence()
                .thenWaitUntil(() -> ctx.assertTrue(containerHas(ctx, stand, dye), Component.literal("brewing did not produce white suspicious dye")))
                .thenExecute(() -> ctx.setBlock(redstone, Blocks.AIR))
                .thenWaitUntil(() -> ctx.assertTrue(containerHas(ctx, bottomHopper, dye), Component.literal("bottom hopper did not receive the dye")))
                .thenSucceed();
    }

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
