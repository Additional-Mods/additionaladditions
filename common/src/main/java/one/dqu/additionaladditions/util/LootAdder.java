package one.dqu.additionaladditions.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.function.Consumer;

/**
 * Manages custom loot table injections loaded from data packs.
 * <p>
 * Not using Global Loot Modifiers because Fabric doesn't have an equivalent and is code based.
 * This is a shared solution that still has injections data driven.
 * <p>
 * The injection files are loaded from "data/additionaladditions/loot_table/injections/".
 * <p>
 * Format:
 * ```
 * {
 *     "target": "namespace:path/to/loot_table",
 *     "entries": [
 *         { vanilla loot entry format }
 *     ]
 * }
 * ```
 * <p>
 * A single loot pool is created per target loot table, containing all injected entries.
 * Amount of rolls is hardcoded to: between 1 and 3 for chests, exactly 1 for others.
 * It's not ideal, but I'm not sure of a better way to handle it without overcomplicating things for the mod.
 */
public class LootAdder {
    public static final LootAdder INSTANCE = new LootAdder();

    public static final Codec<LootPoolEntryContainer> ENTRY_CODEC = BuiltInRegistries.LOOT_POOL_ENTRY_TYPE.byNameCodec().dispatch(
            "type",
            LootPoolEntryContainer::getType,
            LootPoolEntryType::codec
    );

    public record LootInjection(ResourceLocation target, List<LootPoolEntryContainer> entries) {
        public static final Codec<LootInjection> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        ResourceLocation.CODEC.fieldOf("target").forGetter(LootInjection::target),
                        Codec.list(ENTRY_CODEC).fieldOf("entries").forGetter(LootInjection::entries)
                ).apply(instance, LootInjection::new)
        );
    }

    private Map<ResourceLocation, List<JsonElement>> injections = new HashMap<>();

    /**
     * Loads loot table injection file JSONs from the resource manager.
     * Disabled features are filtered out based on {@link Toggleable} properties.
     */
    public void prepare(ResourceManager resourceManager) {
        Map<ResourceLocation, List<JsonElement>> map = new HashMap<>();

        Map<ResourceLocation, Resource> resources = resourceManager.listResources("loot_table/injections", location ->
                location.getNamespace().equals(AdditionalAdditions.NAMESPACE) && location.getPath().endsWith(".json")
        );

        for (var entry : resources.entrySet()) {
            ResourceLocation location = entry.getKey();
            Resource resource = entry.getValue();
            String path = location.getPath().substring("loot_table/injections/".length(), location.getPath().length() - ".json".length());

            // check if the feature is enabled based on path

            // e.g. "music_discs/0308/injection.json" -> checks for config property "music_discs/0308"
            // if it's toggleable and disabled, skip the injection

            int lastSlash = path.lastIndexOf('/');
            String feature = (lastSlash == -1) ? path : path.substring(0, lastSlash);

            ConfigProperty<?> property = ConfigProperty.getAll().stream()
                    .filter(p -> p.path().getPath().equals(feature))
                    .findFirst().orElse(null);

            if (property == null) {
                AdditionalAdditions.LOGGER.warn("[{}] No config property found for loot injection: {}", AdditionalAdditions.NAMESPACE, location);
            } else if (property.get() instanceof Toggleable toggleable && !toggleable.enabled()) {
                continue;
            }

            // parse the loot injection into json element, and extract target table location

            try (Reader reader = resource.openAsReader()) {
                JsonElement json = JsonParser.parseReader(reader);

                if (!json.isJsonObject() || !json.getAsJsonObject().has("target")) {
                    AdditionalAdditions.LOGGER.error("[{}] Invalid loot injection file (missing target): {}", AdditionalAdditions.NAMESPACE, location);
                    continue;
                }

                DataResult<ResourceLocation> targetResult = ResourceLocation.CODEC.parse(JsonOps.INSTANCE, json.getAsJsonObject().get("target"));
                if (targetResult.result().isEmpty()) {
                    AdditionalAdditions.LOGGER.error("[{}] Invalid target in loot injection file: {}: {}", AdditionalAdditions.NAMESPACE, location, targetResult.error().get().message());
                    continue;
                }

                ResourceLocation target = targetResult.result().get();
                map.computeIfAbsent(target, k -> new ArrayList<>()).add(json);
            } catch (IOException e) {
                AdditionalAdditions.LOGGER.error("[{}] Failed to read loot injection file: {}", AdditionalAdditions.NAMESPACE, location, e);
            }
        }

        AdditionalAdditions.LOGGER.info("[{}] Loaded {} loot injections.", AdditionalAdditions.NAMESPACE, map.values().stream().mapToInt(List::size).sum());

        this.injections = map;
    }

    /**
     * Applies loaded injections for a given loot table.
     * This creates a new loot pool containing all injected entries and passes it to the consumer.
     *
     * @param target the loot table to inject into
     * @param registries holder lookup provider for registry access
     * @param consumer receives the created loot pool
     */
    public void inject(ResourceLocation target, HolderLookup.Provider registries, Consumer<LootPool> consumer) {
        List<JsonElement> injections = this.injections.get(target);

        if (injections == null) {
            return;
        }

        LootPool.Builder pool = LootPool.lootPool();

        if (target.getPath().startsWith("chests/")) {
            pool.setRolls(UniformGenerator.between(1.0F, 3.0F));
        } else {
            pool.setRolls(ConstantValue.exactly(1.0F));
        }

        for (JsonElement json : injections) {
            DataResult<LootInjection> injection = LootInjection.CODEC.parse(RegistryOps.create(JsonOps.INSTANCE, registries), json);
            if (injection.result().isEmpty()) {
                AdditionalAdditions.LOGGER.error("[{}] Failed to parse loot injection for target {}: {}", AdditionalAdditions.NAMESPACE, target, injection.error().get().message());
                continue;
            }

            for (LootPoolEntryContainer entry : injection.result().get().entries()) {
                pool.add(new BuiltLootPoolEntry(entry));
            }
        }

        consumer.accept(pool.build());
    }

    /**
     * Wrapper builder for already built loot pool entries.
     * This is because LootPool.Builder only accepts builder entries,
     * but entries are built when deserialized.
     */
    static class BuiltLootPoolEntry extends LootPoolEntryContainer.Builder<BuiltLootPoolEntry> {
        private final LootPoolEntryContainer entry;

        public BuiltLootPoolEntry(LootPoolEntryContainer entry) {
            this.entry = entry;
        }

        @Override
        protected BuiltLootPoolEntry getThis() {
            return this;
        }

        @Override
        public LootPoolEntryContainer build() {
            return this.entry;
        }
    }
}
