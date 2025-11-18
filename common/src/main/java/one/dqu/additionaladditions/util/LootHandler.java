package one.dqu.additionaladditions.util;

import one.dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class LootHandler {
    private static final List<LootEntry> entries = new ArrayList<>();

    public static void register(ResourceKey<?> table, Supplier<Boolean> condition, Function<HolderLookup.Provider, LootPool.Builder> poolFactory) {
        LootEntry entry = new LootEntry(table.location(), condition, poolFactory);
        entries.add(entry);
    }

    public static void register(List<ResourceKey<?>> tables, Supplier<Boolean> condition, Function<HolderLookup.Provider, LootPool.Builder> poolFactory) {
        for (ResourceKey<?> table : tables) {
            register(table, condition, poolFactory);
        }
    }

    public static void register(ResourceKey<?> table, Supplier<Boolean> condition, LootPool.Builder pool) {
        register(table, condition, provider -> pool);
    }

    public static void register(List<ResourceKey<?>> tables, Supplier<Boolean> condition, LootPool.Builder pool) {
        register(tables, condition, provider -> pool);
    }

    public static void postInit() {
        AdditionalAdditions.LOGGER.info("[" + AdditionalAdditions.NAMESPACE + "] Adding " + entries.size() + " loot pools");
    }

    public static void handle(ResourceLocation id, LootTable.Builder table, HolderLookup.Provider registries) {
        for (LootEntry entry : entries) {
            if (entry.table.equals(id)) {
                if (!entry.condition.get()) continue;

                table.withPool(entry.poolFactory.apply(registries));
            }
        }
    }

    private record LootEntry(
            ResourceLocation table,
            Supplier<Boolean> condition,
            Function<HolderLookup.Provider, LootPool.Builder> poolFactory
    ) {}
}
