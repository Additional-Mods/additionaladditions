package one.dqu.additionaladditions.fabric;

import com.google.gson.JsonElement;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.network.ConfigSyncS2CPayload;
import one.dqu.additionaladditions.util.CreativeAdder;
import one.dqu.additionaladditions.util.LootAdder;
import one.dqu.additionaladditions.util.fabric.RegistrarImpl;

import java.util.Map;
import java.util.stream.Collectors;

public final class AdditionalAdditionsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AdditionalAdditions.init();
        RegistrarImpl.runDeferred();

        // creative adder
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((tab, entries) -> {
            BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).ifPresentOrElse(key -> {
                CreativeAdder.getEntries(key).forEach(entry -> {
                    if (!entry.condition().get()) return;
                    if (entry.before()) {
                        entries.addBefore(entry.anchor(), entry.item().get());
                    } else {
                        entries.addAfter(entry.anchor(), entry.item().get());
                    }
                });
            }, () -> AdditionalAdditions.LOGGER.warn("[{}] Unknown creative tab: {}", AdditionalAdditions.NAMESPACE, tab.getDisplayName()));
        });

        // loot handler
        LootTableEvents.MODIFY.register(((resourceKey, builder, lootTableSource, provider) -> {
            LootAdder.handle(resourceKey.location(), builder, provider);
        }));
        LootAdder.postInit();

        // config sync
        PayloadTypeRegistry.configurationS2C().register(ConfigSyncS2CPayload.TYPE, ConfigSyncS2CPayload.STREAM_CODEC);
        ServerConfigurationConnectionEvents.CONFIGURE.register((listener, server) -> {
            Map<ResourceLocation, JsonElement> map = ConfigProperty.getAll().stream()
                    .collect(Collectors.toMap(
                            ConfigProperty::path,
                            property -> property.serialize().getOrThrow()
                    ));
            ServerConfigurationNetworking.send(listener, new ConfigSyncS2CPayload(map));
        });
    }
}
