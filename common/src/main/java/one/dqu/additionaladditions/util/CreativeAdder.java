package one.dqu.additionaladditions.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.*;
import java.util.function.Supplier;

public class CreativeAdder {
    private static final Map<ResourceKey<CreativeModeTab>, List<CreativeEntry>> entries = new HashMap<>();

    public static void add(ResourceKey<CreativeModeTab> tab, Supplier<Boolean> condition, ItemLike after, Supplier<? extends Item> item) {
        CreativeEntry entry = new CreativeEntry(condition, false, after, item);
        entries.computeIfAbsent(tab, k -> new ArrayList<>()).addFirst(entry);
    }

    public static void addBefore(ResourceKey<CreativeModeTab> tab, Supplier<Boolean> condition, ItemLike after, Supplier<? extends Item> item) {
        CreativeEntry entry = new CreativeEntry(condition, true, after, item);
        entries.computeIfAbsent(tab, k -> new ArrayList<>()).addLast(entry);
    }

    public static List<CreativeEntry> getEntries(ResourceKey<CreativeModeTab> tab) {
        return Collections.unmodifiableList(entries.getOrDefault(tab, Collections.emptyList()));
    }

    public record CreativeEntry(Supplier<Boolean> condition, boolean before, ItemLike anchor, Supplier<? extends Item> item) {}
}
