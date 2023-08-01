package dqu.additionaladditions.misc;

import dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LootHandler {
    private static final List<LootEntry> entries = new ArrayList<>();

    public static void register(ResourceLocation table, Supplier<Boolean> condition, LootPool.Builder pool) {
        LootEntry entry = new LootEntry(table, condition, pool);
        entries.add(entry);
    }

    public static void register(List<ResourceLocation> tables, Supplier<Boolean> condition, LootPool.Builder pool) {
        for (ResourceLocation table : tables) {
            register(table, condition, pool);
        }
    }

    public static void postInit() {
        AdditionalAdditions.LOGGER.info("[" + AdditionalAdditions.namespace + "] Adding " + entries.size() + " loot pools");
    }

    public static void handle(ResourceLocation id, LootTable.Builder table) {
        for (LootEntry entry : entries) {
            if (entry.table.equals(id)) {
                if (!entry.condition.get()) continue;

                table.withPool(entry.pool);
            }
        }
    }

    private record LootEntry(
            ResourceLocation table,
            Supplier<Boolean> condition,
            LootPool.Builder pool
    ) {}
}
