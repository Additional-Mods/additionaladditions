package one.dqu.additionaladditions.core.datagen.template.recipe;

import net.minecraft.core.HolderGetter;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public sealed interface RecipeInput {
    Ingredient ingredient(HolderGetter<Item> registries);

    record OfItem(ItemLike item) implements RecipeInput {
        @Override
        public Ingredient ingredient(HolderGetter<Item> registries) {
            return Ingredient.of(item);
        }
    }

    record OfTag(TagKey<Item> tag) implements RecipeInput {
        @Override
        public Ingredient ingredient(HolderGetter<Item> registries) {
            return Ingredient.of(registries.getOrThrow(tag));
        }
    }

    static RecipeInput of(ItemLike item) {
        return new OfItem(item);
    }

    static RecipeInput of(TagKey<Item> tag) {
        return new OfTag(tag);
    }
}
