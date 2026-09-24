package one.dqu.additionaladditions.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.network.ConfigSyncS2CPayload;
import one.dqu.additionaladditions.core.util.CreativeAdder;
import one.dqu.additionaladditions.core.util.LootAdder;
import one.dqu.additionaladditions.core.util.LootTableExtension;
import one.dqu.additionaladditions.core.util.fabric.RegistrarImpl;
import one.dqu.additionaladditions.registry.AAMisc;


public final class AdditionalAdditionsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AdditionalAdditions.init();
        RegistrarImpl.runDeferred();

        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.TRIM, AAMisc.GLINT_COLOR_COMPONENT.get());

        // creative adder
        CreativeModeTabEvents.MODIFY_OUTPUT_ALL.register((tab, entries) -> {
            BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).ifPresentOrElse(key -> {
                CreativeAdder.getEntries(key).forEach(entry -> {
                    if (!entry.condition().get()) return;
                    if (entry.before()) {
                        entries.insertBefore(entry.anchor(), entry.item().get());
                    } else {
                        entries.insertAfter(entry.anchor(), entry.item().get());
                    }
                });
            }, () -> AdditionalAdditions.LOGGER.warn("[{}] Unknown creative tab: {}", AdditionalAdditions.NAMESPACE, tab.getDisplayName()));
        });

        // loot handler
        LootTableEvents.MODIFY.register(((resourceKey, builder, lootTableSource, provider) -> {
            LootAdder.INSTANCE.inject(resourceKey.identifier(), provider, builder::pool, () -> ((LootTableExtension) builder).additionaladditions$clearPools());
        }));

        // config sync
        PayloadTypeRegistry.clientboundConfiguration().register(ConfigSyncS2CPayload.TYPE, ConfigSyncS2CPayload.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ConfigSyncS2CPayload.TYPE, ConfigSyncS2CPayload.STREAM_CODEC);
        ServerConfigurationConnectionEvents.CONFIGURE.register((listener, server) -> {
            ServerConfigurationNetworking.send(listener, ConfigSyncS2CPayload.create());
        });
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
            if (!success) return;
            ConfigSyncS2CPayload payload = ConfigSyncS2CPayload.create();
            server.getPlayerList().getPlayers().forEach(player -> ServerPlayNetworking.send(player, payload));
        });

        // recipe sync
        RecipeSynchronization.synchronizeRecipeSerializer(AAMisc.SUSPICIOUS_DYE_RECIPE_SERIALIZER.get());
    }
}
