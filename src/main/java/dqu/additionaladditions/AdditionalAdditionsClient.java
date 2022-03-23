package dqu.additionaladditions;

import dqu.additionaladditions.item.PocketJukeboxItem;
import dqu.additionaladditions.registry.AdditionalBlocks;
import dqu.additionaladditions.registry.AdditionalEntities;
import dqu.additionaladditions.registry.AdditionalItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class AdditionalAdditionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(AdditionalBlocks.COPPER_PATINA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AdditionalBlocks.ROPE_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AdditionalBlocks.GLOW_STICK_BLOCK, RenderLayer.getCutout());

        EntityRendererRegistry.INSTANCE.register(AdditionalEntities.GLOW_STICK_ENTITY_ENTITY_TYPE, FlyingItemEntityRenderer::new);

        FabricModelPredicateProviderRegistry.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS, new Identifier("pull"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.0F;
            return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
        });

        FabricModelPredicateProviderRegistry.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.0F;
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });

        FabricModelPredicateProviderRegistry.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS, new Identifier("charged"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            return CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
        });

        FabricModelPredicateProviderRegistry.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS, new Identifier("firework"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            return CrossbowItem.hasProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });

        FabricModelPredicateProviderRegistry.register(AdditionalItems.POCKET_JUKEBOX_ITEM, new Identifier("disc"), ((itemStack, clientWorld, livingEntity, worldSeed) -> {
            return PocketJukeboxItem.hasDisc(itemStack) ? 1.0F : 0.0F;
        }));

        FabricModelPredicateProviderRegistry.register(AdditionalItems.DEPTH_METER_ITEM, new Identifier("angle"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.3125F;
            World world = livingEntity.world;
            if (world == null) return 0.3125F;

            float sea = world.getSeaLevel();
            float height = livingEntity.getBlockY();
            float top = world.getTopY();
            float bottom = world.getBottomY();

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
