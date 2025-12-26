package one.dqu.additionaladditions.util;

import one.dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LootAdder {
    private static final List<LootEntry> ENTRIES = new ArrayList<>();
    private static final Map<ResourceKey<LootTable>, List<BatchedLootEntry>> BATCHED = new HashMap<>();

    private record BatchedLootEntry(Supplier<? extends Item> item, Supplier<Boolean> condition, BiConsumer<HolderLookup.Provider, LootPoolSingletonContainer.Builder<?>> builder) {}

    public static void addBatched(ResourceKey<LootTable> table, Supplier<? extends Item> item, Supplier<Boolean> condition, BiConsumer<HolderLookup.Provider, LootPoolSingletonContainer.Builder<?>> builder) {
        BATCHED.computeIfAbsent(table, k -> new ArrayList<>()).add(new BatchedLootEntry(item, condition, builder));
    }

    public static void processBatched() {
        BATCHED.forEach((table, batchedEntries) -> {
            register(table, () -> true, (registries) -> {
                LootPool.Builder pool = LootPool.lootPool().setRolls(UniformGenerator.between(1, 2));
                for (BatchedLootEntry entry : batchedEntries) {
                    if (entry.condition.get()) {
                        var builder = LootItem.lootTableItem(entry.item.get());
                        entry.builder.accept(registries, builder);
                        pool.add(builder);
                    }
                }
                return pool;
            });
        });
        BATCHED.clear();
    }

    public static void register(ResourceKey<?> table, Supplier<Boolean> condition, Function<HolderLookup.Provider, LootPool.Builder> poolFactory) {
        LootEntry entry = new LootEntry(table.location(), condition, poolFactory);
        ENTRIES.add(entry);
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
        AdditionalAdditions.LOGGER.info("[" + AdditionalAdditions.NAMESPACE + "] Adding " + ENTRIES.size() + " loot pools");
    }

    public static void handle(ResourceLocation id, LootTable.Builder table, HolderLookup.Provider registries) {
        for (LootEntry entry : ENTRIES) {
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
