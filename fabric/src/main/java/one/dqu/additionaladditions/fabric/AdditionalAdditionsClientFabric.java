package one.dqu.additionaladditions.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigLoader;
import one.dqu.additionaladditions.config.network.ConfigSyncS2CPayload;
import one.dqu.additionaladditions.entity.RopeArrowRenderer;
import one.dqu.additionaladditions.glint.GlintResourceGenerator;
import one.dqu.additionaladditions.item.PocketJukeboxItem;
import one.dqu.additionaladditions.misc.PocketJukeboxPlayer;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAEntities;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.util.fabric.ModCompatibilityImpl;

public final class AdditionalAdditionsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
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

        // model predicates
        ItemProperties.register(AAItems.CROSSBOW_WITH_SPYGLASS.get(), ResourceLocation.withDefaultNamespace("pull"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.0F;
            return livingEntity.getUseItem() != itemStack ? 0.0F : (itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0F;
        });

        ItemProperties.register(AAItems.CROSSBOW_WITH_SPYGLASS.get(), ResourceLocation.withDefaultNamespace("pulling"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.0F;
            return livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
        });

        ItemProperties.register(AAItems.CROSSBOW_WITH_SPYGLASS.get(), ResourceLocation.withDefaultNamespace("charged"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            return CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
        });

        ItemProperties.register(AAItems.CROSSBOW_WITH_SPYGLASS.get(), ResourceLocation.withDefaultNamespace("firework"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            return itemStack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).getItems().stream().anyMatch(stack -> stack.is(Items.FIREWORK_ROCKET)) ? 1.0F : 0.0F;
        });

        ItemProperties.register(AAItems.POCKET_JUKEBOX_ITEM.get(), ResourceLocation.withDefaultNamespace("disc"), ((itemStack, clientWorld, livingEntity, worldSeed) -> {
            return PocketJukeboxItem.hasDisc(itemStack) ? 1.0F : 0.0F;
        }));

        ClampedItemPropertyFunction albumFunction = (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (!itemStack.has(AAMisc.ALBUM_CONTENTS_COMPONENT.get())) {
                return 0.0F;
            }
            return itemStack.get(AAMisc.ALBUM_CONTENTS_COMPONENT.get()).items().isEmpty() ? 0.0F : 1.0F;
        };
        ResourceLocation albumLocation = ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "disc");
        ItemProperties.register(AAItems.ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.WHITE_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.LIGHT_GRAY_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.GRAY_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.BLACK_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.BROWN_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.RED_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.ORANGE_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.YELLOW_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.LIME_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.GREEN_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.CYAN_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.LIGHT_BLUE_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.BLUE_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.PURPLE_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.MAGENTA_ALBUM.get(), albumLocation, albumFunction);
        ItemProperties.register(AAItems.PINK_ALBUM.get(), albumLocation, albumFunction);

        ItemProperties.register(AAItems.BAROMETER.get(), ResourceLocation.withDefaultNamespace("angle"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
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

        // mod compatibility
        ClientLifecycleEvents.CLIENT_STARTED.register((minecraft) -> {
            ModCompatibilityImpl.showToasts();
        });
    }
}
