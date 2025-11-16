package dqu.additionaladditions.misc;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum CreativeAdder {

    TOOLS_AND_UTILITIES(),
    INGREDIENTS(),
    BUILDING_BLOCKS(),
    REDSTONE_BLOCKS(),
    FOOD_AND_DRINKS(),
    FUNCTIONAL_BLOCKS(),
    COMBAT();

    private final List<CreativeEntry> entries = new ArrayList<>();

    CreativeAdder() {}

    public void add(Supplier<Boolean> condition, ItemLike after, ItemLike... items) {
        CreativeEntry entry = new CreativeEntry(condition, false, after, items);
        this.entries.addFirst(entry);
    }

    public void addBefore(Supplier<Boolean> condition, ItemLike after, ItemLike... items) {
        CreativeEntry entry = new CreativeEntry(condition, true, after, items);
        this.entries.addLast(entry);
    }

    public void pushAllTo(FabricItemGroupEntries content) {
        for (CreativeEntry entry : entries) {
            if (entry.condition.get()) {
                if (entry.before) {
                    content.addBefore(entry.anchor, entry.items);
                } else {
                    content.addAfter(entry.anchor, entry.items);
                }
            }
        }
    }

    private record CreativeEntry(Supplier<Boolean> condition, boolean before, ItemLike anchor, ItemLike... items) {}
}
