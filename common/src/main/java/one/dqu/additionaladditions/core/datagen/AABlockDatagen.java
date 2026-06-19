package one.dqu.additionaladditions.core.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import one.dqu.additionaladditions.AdditionalAdditions;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class AABlockDatagen {
    private static final List<Entry> entries = AdditionalAdditions.DATAGEN ? new ArrayList<>() : null;
    private static BlockModelGenerators currentGen;

    public record Entry(
            Identifier id,
            Supplier<? extends Block> block,
            List<TagKey<Block>> tags,
            @Nullable Consumer<Block> model // template is null for blocks whose models/blockstates should be manual
    ) {
    }

    public static void register(Entry entry) {
        if (entries == null) return;
        entries.add(entry);
    }

    public static List<Entry> entries() {
        return entries == null ? List.of() : entries;
    }

    public static BlockModelGenerators currentGen() {
        return currentGen;
    }

    // for neoforge
    public static Stream<? extends Holder<Block>> knownBlocks() {
        if (entries == null) return Stream.empty();
        return entries.stream()
                .filter(e -> e.model() != null)
                .map(e -> e.block().get().builtInRegistryHolder());
    }

    public static void generateBlockStateModels(BlockModelGenerators gen) {
        if (entries == null) return;
        currentGen = gen;
        try {
            for (Entry entry : entries) {
                if (entry.model() != null) {
                    entry.model().accept(entry.block().get());
                }
            }
        } finally {
            currentGen = null;
        }
    }
}
