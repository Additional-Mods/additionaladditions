package one.dqu.additionaladditions.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.client.BarometerAngleProperty;
import one.dqu.additionaladditions.client.HasDiscProperty;
import one.dqu.additionaladditions.config.io.ConfigLoader;
import one.dqu.additionaladditions.config.network.ConfigSyncS2CPayload;
import one.dqu.additionaladditions.core.util.fabric.ModCompatibilityImpl;
import one.dqu.additionaladditions.feature.pocket_jukebox.PocketJukeboxPlayer;
import one.dqu.additionaladditions.feature.rope.RopeArrowRenderer;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintResourceGenerator;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAEntities;

import java.util.List;

public final class AdditionalAdditionsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // item model properties
        ConditionalItemModelProperties.ID_MAPPER.put(
                Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "has_disc"),
                HasDiscProperty.MAP_CODEC
        );
        RangeSelectItemModelProperties.ID_MAPPER.put(
                Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "barometer_angle"),
                BarometerAngleProperty.MAP_CODEC
        );

        // pocket jukebox
        ClientTickEvents.END_CLIENT_TICK.register((blah) -> PocketJukeboxPlayer.INSTANCE.tick());

        // config sync
        ClientConfigurationNetworking.registerGlobalReceiver(ConfigSyncS2CPayload.TYPE, (packet, context) -> {
            context.client().execute(() -> {
                if (!ConfigLoader.applyFromServer(packet.config())) {
                    context.responseSender().disconnect(Component.translatable("additionaladditions.gui.config.disconnect"));
                }
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(ConfigSyncS2CPayload.TYPE, (packet, context) -> {
            context.client().execute(() -> {
                if (!ConfigLoader.applyFromServer(packet.config())) {
                    context.responseSender().disconnect(Component.translatable("additionaladditions.gui.config.disconnect"));
                }
            });
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            client.execute(() -> {
                ConfigLoader.load();
                AdditionalAdditions.LOGGER.info("[{}] Reverted to local config", AdditionalAdditions.NAMESPACE);
            });
        });

        // color providers
        BlockTintSource lotusTint = BlockTintSources.constant(-9321636, -14647248);
        BlockColorRegistry.register(List.of(lotusTint), AABlocks.LOTUS_LILY.get(), AABlocks.LOTUS_LILY_CROP.get());

        // entity renderers
        EntityRendererRegistry.register(AAEntities.GLOW_STICK.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(AAEntities.ROPE_ARROW.get(), RopeArrowRenderer::new);

        // resource managers
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public void onResourceManagerReload(ResourceManager resourceManager) {
                GlintResourceGenerator.generateResources(resourceManager);
            }

            @Override
            public Identifier getFabricId() {
                return Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "glint_resource_generator");
            }
        });

        // mod compatibility
        ClientLifecycleEvents.CLIENT_STARTED.register((minecraft) -> {
            ModCompatibilityImpl.showToasts();
        });
    }
}
