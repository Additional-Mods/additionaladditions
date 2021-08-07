package dqu.additionaladditions;

import dqu.additionaladditions.block.BlockRegistry;
import dqu.additionaladditions.entity.EntityRegistry;
import dqu.additionaladditions.item.ItemRegistry;
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

@Environment(EnvType.CLIENT)
public class AdditionalAdditionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.COPPER_PATINA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ROPE_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.GLOW_STICK_BLOCK, RenderLayer.getCutout());

        EntityRendererRegistry.INSTANCE.register(EntityRegistry.GLOW_STICK_ENTITY_ENTITY_TYPE, FlyingItemEntityRenderer::new);

        FabricModelPredicateProviderRegistry.register(ItemRegistry.CROSSBOW_WITH_SPYGLASS, new Identifier("pull"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.0F;
            return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
        });

        FabricModelPredicateProviderRegistry.register(ItemRegistry.CROSSBOW_WITH_SPYGLASS, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.0F;
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });

        FabricModelPredicateProviderRegistry.register(ItemRegistry.CROSSBOW_WITH_SPYGLASS, new Identifier("charged"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            return CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
        });

        FabricModelPredicateProviderRegistry.register(ItemRegistry.CROSSBOW_WITH_SPYGLASS, new Identifier("firework"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            return CrossbowItem.hasProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });
    }
}
