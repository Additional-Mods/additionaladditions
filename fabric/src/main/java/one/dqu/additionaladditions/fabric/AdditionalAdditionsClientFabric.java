package one.dqu.additionaladditions.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.AdditionalAdditionsClient;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.io.ConfigLoader;
import one.dqu.additionaladditions.config.network.ConfigSyncS2CPayload;
import one.dqu.additionaladditions.entity.RopeArrowRenderer;
import one.dqu.additionaladditions.feature.glint.GlintResourceGenerator;
import one.dqu.additionaladditions.feature.PocketJukeboxPlayer;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAEntities;
import one.dqu.additionaladditions.util.fabric.ModCompatibilityImpl;

public final class AdditionalAdditionsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // common client init
        AdditionalAdditionsClient.init();

        // pocket jukebox
        ClientTickEvents.END_CLIENT_TICK.register((blah) -> PocketJukeboxPlayer.INSTANCE.tick());

        // config sync
        ClientConfigurationNetworking.registerGlobalReceiver(ConfigSyncS2CPayload.TYPE, (packet, context) -> {
            context.client().execute(() -> {
                var config = packet.config();
                int version = ConfigLoader.readVersion(config).version();
                if (version != Config.VERSION.get().version()) {
                    AdditionalAdditions.LOGGER.warn("[{}] Received incompatible config version from server, disconnecting. (server: {}, client: {})", AdditionalAdditions.NAMESPACE, version, Config.VERSION.get().version());
                    context.responseSender().disconnect(Component.translatable("additionaladditions.gui.config.disconnect"));
                    return;
                }
                ConfigLoader.apply(config);
                AdditionalAdditions.LOGGER.info("[{}] Loaded config from server", AdditionalAdditions.NAMESPACE);
            });
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            client.execute(() -> {
                ConfigLoader.load();
                AdditionalAdditions.LOGGER.info("[{}] Reverted to local config", AdditionalAdditions.NAMESPACE);
            });
        });

        // render types
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.COPPER_PATINA.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.ROPE_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.GLOW_STICK_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.COTTONSHIVER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.COTTONSHIVER_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.MUDFLOWER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.MUDFLOWER_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.CRIMSON_BLOSSOM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.CRIMSON_BLOSSOM_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.AMBER_BLOSSOM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.AMBER_BLOSSOM_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.BULBUS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.BULBUS_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.SAWTOOTH_FERN.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.SAWTOOTH_FERN_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.FROSTLEAF.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.FROSTLEAF_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.WISTERIA.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.WISTERIA_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.SPIKEBLOSSOM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.SPIKEBLOSSOM_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.SNAPDRAGON.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.SNAPDRAGON_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.LOTUS_LILY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AABlocks.LOTUS_LILY_CROP.get(), RenderType.cutout());

        // color providers
        ColorProviderRegistry.BLOCK.register((state, getter, pos, tintIndex) -> {
            return getter != null && pos != null ? -14647248 : -9321636;
        }, AABlocks.LOTUS_LILY.get(), AABlocks.LOTUS_LILY_CROP.get());

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
            public ResourceLocation getFabricId() {
                return ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "glint_resource_generator");
            }
        });

        // mod compatibility
        ClientLifecycleEvents.CLIENT_STARTED.register((minecraft) -> {
            ModCompatibilityImpl.showToasts();
        });
    }
}
