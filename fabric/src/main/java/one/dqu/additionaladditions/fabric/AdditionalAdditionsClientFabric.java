package one.dqu.additionaladditions.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.ConfigLoader;
import one.dqu.additionaladditions.config.network.ConfigSyncS2CPayload;
import one.dqu.additionaladditions.entity.RopeArrowRenderer;
import one.dqu.additionaladditions.glint.GlintResourceGenerator;
import one.dqu.additionaladditions.item.PocketJukeboxItem;
import one.dqu.additionaladditions.misc.PocketJukeboxPlayer;
import one.dqu.additionaladditions.registry.AdditionalBlocks;
import one.dqu.additionaladditions.registry.AdditionalEntities;
import one.dqu.additionaladditions.registry.AdditionalItems;

public final class AdditionalAdditionsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // pocket jukebox
        ClientTickEvents.END_CLIENT_TICK.register((blah) -> PocketJukeboxPlayer.INSTANCE.tick());

        // config sync
        ClientConfigurationNetworking.registerGlobalReceiver(ConfigSyncS2CPayload.TYPE, (packet, context) -> {
            context.client().execute(() -> {
                ConfigLoader.apply(packet.config());
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
        BlockRenderLayerMap.INSTANCE.putBlock(AdditionalBlocks.COPPER_PATINA.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AdditionalBlocks.ROPE_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AdditionalBlocks.GLOW_STICK_BLOCK.get(), RenderType.cutout());

        // entity renderers
        EntityRendererRegistry.register(AdditionalEntities.GLOW_STICK.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(AdditionalEntities.ROPE_ARROW.get(), RopeArrowRenderer::new);

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
