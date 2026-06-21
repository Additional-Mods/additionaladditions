package one.dqu.additionaladditions.core.datagen.template.recipe;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public final class RecipeEntry {
    private final BiFunction<HolderGetter<Item>, ItemLike, RecipeBuilder> factory;
    private final List<RecipeInput> unlocks = new ArrayList<>();

    // null means the recipe id defaults to the result item path
    private @Nullable String name = null;

    // null means the recipe produces the owning item entry
    private @Nullable ItemLike result = null;

    // recipe book group, null means no group
    private @Nullable String group = null;

    public RecipeEntry(BiFunction<HolderGetter<Item>, ItemLike, RecipeBuilder> factory) {
        this.factory = factory;
    }

    // recipe book unlocks
    public RecipeEntry unlockedBy(RecipeInput... inputs) {
        unlocks.addAll(Arrays.asList(inputs));
        return this;
    }

    public RecipeEntry unlockedBy(ItemLike... items) {
        for (ItemLike item : items) unlocks.add(RecipeInput.of(item));
        return this;
    }

    public RecipeEntry unlockedBy(TagKey<Item> tag) {
        unlocks.add(RecipeInput.of(tag));
        return this;
    }

    // recipe book group
    public RecipeEntry group(String group) {
        this.group = group;
        return this;
    }

    /**
     * Determines recipe ID / path.
     *
     * @param name recipe ID. %s is replaced with the result's path. (e.g. "%s_from_smoking")
     */
    public RecipeEntry named(String name) {
        this.name = name;
        return this;
    }

    // produce a different item than the owning entry
    public RecipeEntry result(ItemLike result) {
        this.result = result;
        return this;
    }

    public RecipeBuilder builder(HolderGetter<Item> registries, ItemLike result) {
        return factory.apply(registries, result);
    }

    public List<RecipeInput> unlocks() {
        return unlocks;
    }

    public String name() {
        return name;
    }

    public ItemLike result() {
        return result;
    }

    public String group() {
        return group;
    }
}
