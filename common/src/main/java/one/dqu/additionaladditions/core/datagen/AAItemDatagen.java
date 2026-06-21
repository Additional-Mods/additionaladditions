package one.dqu.additionaladditions.core.datagen;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.core.datagen.template.recipe.RecipeEntry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class AAItemDatagen {
    private static final List<Entry> entries = AdditionalAdditions.DATAGEN ? new ArrayList<>() : null;
    private static ItemModelGenerators currentGen;

    public record Entry(
            Identifier id,
            Supplier<? extends Item> item,

            // template is null for items whose models/item template defs should be manual
            @Nullable Consumer<Item> model,

            // recipes are associated with item entries. used by AARecipeDatagenProvider
            List<RecipeEntry> recipes,

            List<TagKey<Item>> tags
    ) {
    }

    public static void register(Entry entry) {
        if (entries == null) return;
        entries.add(entry);
    }

    public static List<Entry> entries() {
        return entries == null ? List.of() : entries;
    }

    public static ItemModelGenerators currentGen() {
        return currentGen;
    }

    // for neoforge
    public static Stream<? extends Holder<Item>> knownItems() {
        if (entries == null) return Stream.empty();
        return entries.stream()
                // keep block items so that ModelProvider handles them in finalizeAndValidate
                .filter(e -> e.model() != null
                        || (BuiltInRegistries.ITEM.getValue(e.id()) instanceof BlockItem && !AABlockDatagen.isManuallyModeled(e.id())))
                .map(e -> BuiltInRegistries.ITEM.getValue(e.id()).builtInRegistryHolder());
    }

    public static void generateItemModels(ItemModelGenerators gen) {
        if (entries == null) return;
        currentGen = gen;

        for (Entry entry : entries) {
            if (entry.model() != null) {
                entry.model().accept(entry.item().get());
            }
        }
    }
}
