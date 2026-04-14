package one.dqu.additionaladditions.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.registry.AAMisc;

/**
 * A recipe type that allows for data-driven brewing stand recipes.
 * <p>
 * This was implemented because:
 * 1. This allows for recipes to be customized by the user.
 * 2. Vanilla brewing recipes don't support the "water bottle -> custom item" recipes
 *   (water bottle is a Items.POTION, using a container recipe with that would match all potions and a mix recipe requires the output to also be a potion type).
 * <p>
 * Format example:
 * <pre>{@code
 * {
 *   "type": "additionaladditions:brewing",
 *   "potion": "minecraft:water",
 *   "ingredient": "minecraft:nether_wart",
 *   "result": { "id": "minecraft:golden_apple" }
 * }
 * }</pre>
 */
public class BrewingRecipe implements Recipe<BrewingRecipe.BrewingRecipeInput> {
    public static final MapCodec<BrewingRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Potion.CODEC.fieldOf("potion").forGetter(BrewingRecipe::getPotion),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(BrewingRecipe::getIngredient),
                    ItemStack.CODEC.fieldOf("result").forGetter(BrewingRecipe::getResult)
            ).apply(instance, BrewingRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BrewingRecipe> STREAM_CODEC = StreamCodec.composite(
            Potion.STREAM_CODEC, BrewingRecipe::getPotion,
            Ingredient.CONTENTS_STREAM_CODEC, BrewingRecipe::getIngredient,
            ItemStack.STREAM_CODEC, BrewingRecipe::getResult,
            BrewingRecipe::new
    );

    public static final RecipeSerializer<BrewingRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    private final Holder<Potion> potion;
    private final Ingredient ingredient;
    private final ItemStack result;

    /**
     * @param potion potion component of the item in the three bottom slots (bottles)
     * @param ingredient item in the top slot, the brewing ingredient
     * @param result the resulting item in the bottom slot
     */
    public BrewingRecipe(Holder<Potion> potion, Ingredient ingredient, ItemStack result) {
        this.potion = potion;
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    public boolean matches(BrewingRecipeInput recipeInput, Level level) {
        PotionContents contents = recipeInput.input().get(DataComponents.POTION_CONTENTS);
        return contents != null && contents.is(potion) && ingredient.test(recipeInput.ingredient());
    }

    @Override
    public ItemStack assemble(BrewingRecipeInput recipeInput) {
        return result.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<BrewingRecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<BrewingRecipeInput>> getType() {
        return AAMisc.BREWING_RECIPE_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    // prevents console warns from recipe book validation
    @Override
    public boolean isSpecial() {
        return true;
    }

    public ItemStack getResult() {
        return result;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Holder<Potion> getPotion() {
        return potion;
    }

    public record BrewingRecipeInput(
            ItemStack input, ItemStack ingredient
    ) implements RecipeInput {
        @Override
        public ItemStack getItem(int i) {
            return i == 0 ? input : ingredient;
        }

        @Override
        public int size() {
            return 2;
        }
    }

}
