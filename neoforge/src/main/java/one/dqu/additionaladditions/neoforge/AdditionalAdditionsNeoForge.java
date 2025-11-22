package one.dqu.additionaladditions.neoforge;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterConfigurationTasksEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.ConfigLoader;
import one.dqu.additionaladditions.config.network.ConfigSyncS2CPayload;
import one.dqu.additionaladditions.config.network.neoforge.ConfigSyncTask;
import one.dqu.additionaladditions.entity.RopeArrowRenderer;
import one.dqu.additionaladditions.glint.GlintResourceGenerator;
import one.dqu.additionaladditions.item.PocketJukeboxItem;
import one.dqu.additionaladditions.registry.AdditionalBlocks;
import one.dqu.additionaladditions.registry.AdditionalEntities;
import one.dqu.additionaladditions.registry.AdditionalItems;
import one.dqu.additionaladditions.util.CreativeAdder;
import one.dqu.additionaladditions.util.Registrar;
import one.dqu.additionaladditions.util.neoforge.AdditionalLootModifier;
import one.dqu.additionaladditions.util.neoforge.RegistrarImpl;

@Mod(AdditionalAdditions.NAMESPACE)
public final class AdditionalAdditionsNeoForge {
    public AdditionalAdditionsNeoForge(IEventBus modEventBus) {
        AdditionalAdditions.init();

        // Global loot modifier
        var GLM_REGISTRY = Registrar.wrap(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS);
        GLM_REGISTRY.register(
                ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "loot_modifier"),
                AdditionalLootModifier.CODEC
        );

        modEventBus.addListener(FMLCommonSetupEvent.class, this::onSetupEvent);
        modEventBus.addListener(BuildCreativeModeTabContentsEvent.class, this::onBuildCreativeTabContents);
        modEventBus.addListener(RegisterPayloadHandlersEvent.class, this::onRegisterPayloadHandlers);
        modEventBus.addListener(RegisterConfigurationTasksEvent.class, this::onRegisterConfigurationTasks);

        if (FMLEnvironment.dist.isClient()) {
            NeoForge.EVENT_BUS.addListener(ClientPlayerNetworkEvent.LoggingOut.class, this::onClientLogout);
            modEventBus.addListener(RegisterClientReloadListenersEvent.class, this::onRegisterClientReloadListeners);
            modEventBus.addListener(FMLClientSetupEvent.class, this::onClientSetup);
            modEventBus.addListener(EntityRenderersEvent.RegisterRenderers.class, this::onRegisterEntityRenderers);
        }

        RegistrarImpl.registerAll(modEventBus);
    }

    // EVENT HANDLERS

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

    private void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.configurationToClient(
            ConfigSyncS2CPayload.TYPE,
            ConfigSyncS2CPayload.STREAM_CODEC,
            (payload, context) -> context.enqueueWork(() -> {
                ConfigLoader.apply(payload.config());
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
            AdditionalEntities.GLOW_STICK.get(),
            ThrownItemRenderer::new
        );

        event.registerEntityRenderer(
            AdditionalEntities.ROPE_ARROW.get(),
            RopeArrowRenderer::new
        );
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        // Block render layers
        ItemBlockRenderTypes.setRenderLayer(AdditionalBlocks.COPPER_PATINA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AdditionalBlocks.ROPE_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AdditionalBlocks.GLOW_STICK_BLOCK.get(), RenderType.cutout());

        // Item predicates
        ItemProperties.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS.get(), ResourceLocation.withDefaultNamespace("pull"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.0F;
            return livingEntity.getUseItem() != itemStack ? 0.0F : (itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0F;
        });

        ItemProperties.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS.get(), ResourceLocation.withDefaultNamespace("pulling"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.0F;
            return livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
        });

        ItemProperties.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS.get(), ResourceLocation.withDefaultNamespace("charged"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            return CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
        });

        ItemProperties.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS.get(), ResourceLocation.withDefaultNamespace("firework"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            return itemStack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).getItems().stream().anyMatch(stack -> stack.is(Items.FIREWORK_ROCKET)) ? 1.0F : 0.0F;
        });

        ItemProperties.register(AdditionalItems.POCKET_JUKEBOX_ITEM.get(), ResourceLocation.withDefaultNamespace("disc"), ((itemStack, clientWorld, livingEntity, worldSeed) -> {
            return PocketJukeboxItem.hasDisc(itemStack) ? 1.0F : 0.0F;
        }));

        ItemProperties.register(AdditionalItems.BAROMETER.get(), ResourceLocation.withDefaultNamespace("angle"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.3125F;
            Level world = livingEntity.level();
            if (world == null) return 0.3125F;

            float sea = world.getSeaLevel();
            float height = livingEntity.getBlockY();
            float top = world.getMaxBuildHeight();
            float bottom = world.getMinBuildHeight();

            if (height > top) return 0;
            if (height < bottom) return 1;

            if (height >= sea) {
                double val = (height / (2 * (sea - top))) + 0.25 - ((sea + top) / (4 * (sea - top)));
                return (float) val;
            } else {
                double val = (height / (2 * (bottom - sea))) + 0.75 - ((bottom + sea) / (4 * (bottom - sea)));
                return (float) val;
            }
        });
    }
}
