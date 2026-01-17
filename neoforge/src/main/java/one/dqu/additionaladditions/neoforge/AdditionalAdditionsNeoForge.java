package one.dqu.additionaladditions.neoforge;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.network.event.RegisterConfigurationTasksEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.AdditionalAdditionsClient;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.io.ConfigLoader;
import one.dqu.additionaladditions.config.network.ConfigSyncS2CPayload;
import one.dqu.additionaladditions.config.network.neoforge.ConfigSyncTask;
import one.dqu.additionaladditions.entity.RopeArrowRenderer;
import one.dqu.additionaladditions.feature.glint.GlintResourceGenerator;
import one.dqu.additionaladditions.feature.PocketJukeboxPlayer;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAEntities;
import one.dqu.additionaladditions.util.CreativeAdder;
import one.dqu.additionaladditions.util.LootAdder;
import one.dqu.additionaladditions.util.neoforge.ModCompatibilityImpl;
import one.dqu.additionaladditions.util.neoforge.RegistrarImpl;

@Mod(AdditionalAdditions.NAMESPACE)
public final class AdditionalAdditionsNeoForge {
    public AdditionalAdditionsNeoForge(IEventBus modEventBus) {
        AdditionalAdditions.init();

        modEventBus.addListener(FMLCommonSetupEvent.class, this::onSetupEvent);
        modEventBus.addListener(BuildCreativeModeTabContentsEvent.class, this::onBuildCreativeTabContents);
        modEventBus.addListener(RegisterPayloadHandlersEvent.class, this::onRegisterPayloadHandlers);
        modEventBus.addListener(RegisterConfigurationTasksEvent.class, this::onRegisterConfigurationTasks);

        if (FMLEnvironment.dist.isClient()) {
            NeoForge.EVENT_BUS.addListener(ClientPlayerNetworkEvent.LoggingOut.class, this::onClientLogout);
            NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, this::onClientEndTick);
            modEventBus.addListener(RegisterClientReloadListenersEvent.class, this::onRegisterClientReloadListeners);
            modEventBus.addListener(FMLClientSetupEvent.class, this::onClientSetup);
            modEventBus.addListener(EntityRenderersEvent.RegisterRenderers.class, this::onRegisterEntityRenderers);
            modEventBus.addListener(RegisterColorHandlersEvent.Block.class, this::registerBlockColors);
        }

        if (!FMLEnvironment.dist.isClient()) {
            NeoForge.EVENT_BUS.addListener(LootTableLoadEvent.class, this::onLootTableLoad);
        }

        RegistrarImpl.registerAll(modEventBus);
    }

    // EVENT HANDLERS

    private void onClientEndTick(ClientTickEvent.Post event) {
        PocketJukeboxPlayer.INSTANCE.tick();
    }

    private void onSetupEvent(FMLCommonSetupEvent event) {
        RegistrarImpl.runDeferred();
    }

    private void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(event.getTab()).ifPresentOrElse(
            key -> CreativeAdder.getEntries(key).forEach(entry -> addCreativeTabEntry(event, entry)),
            () -> AdditionalAdditions.LOGGER.warn("[{}] Unknown creative tab: {}",
                AdditionalAdditions.NAMESPACE, event.getTab().getDisplayName())
        );
    }

    private void addCreativeTabEntry(BuildCreativeModeTabContentsEvent event, CreativeAdder.CreativeEntry entry) {
        if (!entry.condition().get()) return;

        var anchorStack = entry.anchor().asItem().getDefaultInstance();
        var itemStack = entry.item().get().getDefaultInstance();
        var visibility = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

        if (entry.before()) {
            event.insertBefore(anchorStack, itemStack, visibility);
        } else {
            event.insertAfter(anchorStack, itemStack, visibility);
        }
    }

    private void onLootTableLoad(LootTableLoadEvent event) {
        LootAdder.INSTANCE.inject(
                event.getKey().location(), event.getRegistries(), event.getTable()::addPool
        );
    }

    private void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.configurationToClient(
            ConfigSyncS2CPayload.TYPE,
            ConfigSyncS2CPayload.STREAM_CODEC,
            (payload, context) -> context.enqueueWork(() -> {
                var config = payload.config();
                int version = ConfigLoader.readVersion(config).version();
                if (version != Config.VERSION.get().version()) {
                    AdditionalAdditions.LOGGER.warn("[{}] Received incompatible config version from server, disconnecting. (server: {}, client: {})", AdditionalAdditions.NAMESPACE, version, Config.VERSION.get().version());
                    context.disconnect(Component.translatable("additionaladditions.gui.config.disconnect"));
                    return;
                }
                ConfigLoader.apply(config);
                AdditionalAdditions.LOGGER.info("[{}] Loaded config from server", AdditionalAdditions.NAMESPACE);
            })
        );
    }

    private void onRegisterConfigurationTasks(RegisterConfigurationTasksEvent event) {
        event.register(new ConfigSyncTask(event.getListener()));
    }

    private void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        ConfigLoader.load();
        AdditionalAdditions.LOGGER.info("[{}] Reverted to local config", AdditionalAdditions.NAMESPACE);
    }

    private void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new SimplePreparableReloadListener<Void>() {
            @Override
            protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
                return null;
            }

            @Override
            protected void apply(Void unused, ResourceManager resourceManager, ProfilerFiller profiler) {
                GlintResourceGenerator.generateResources(resourceManager);
            }
        });
    }

    private void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
            AAEntities.GLOW_STICK.get(),
            ThrownItemRenderer::new
        );

        event.registerEntityRenderer(
            AAEntities.ROPE_ARROW.get(),
            RopeArrowRenderer::new
        );
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        // common client init
        AdditionalAdditionsClient.init();

        // Block render layers
        ItemBlockRenderTypes.setRenderLayer(AABlocks.COPPER_PATINA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.ROPE_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.GLOW_STICK_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.COTTONSHIVER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.COTTONSHIVER_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.MUDFLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.MUDFLOWER_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.CRIMSON_BLOSSOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.CRIMSON_BLOSSOM_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.AMBER_BLOSSOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.AMBER_BLOSSOM_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.BULBUS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.BULBUS_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.SAWTOOTH_FERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.SAWTOOTH_FERN_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.FROSTLEAF.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.FROSTLEAF_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.WISTERIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.WISTERIA_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.SPIKEBLOSSOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.SPIKEBLOSSOM_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.SNAPDRAGON.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.SNAPDRAGON_CROP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.LOTUS_LILY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AABlocks.LOTUS_LILY_CROP.get(), RenderType.cutout());

        // mod compatibility
        event.enqueueWork(ModCompatibilityImpl::showToasts);
    }

    private void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, getter, pos, tintIndex) -> {
            return getter != null && pos != null ? -14647248 : -9321636;
        }, AABlocks.LOTUS_LILY.get(), AABlocks.LOTUS_LILY_CROP.get());
    }
}
