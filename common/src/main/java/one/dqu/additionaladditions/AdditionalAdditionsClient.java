package one.dqu.additionaladditions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.item.PocketJukeboxItem;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;

@Environment(EnvType.CLIENT)
public final class AdditionalAdditionsClient {
    public static boolean requestingInHandModel = false;

    public static void init() {
        itemProperties();
    }

    private static void itemProperties() {
        ItemProperties.register(AAItems.CROSSBOW_WITH_SPYGLASS.get(), ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "hand"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            return requestingInHandModel ? 1.0F : 0.0F;
        });

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
            Entity entity = livingEntity != null ? livingEntity : itemStack.getEntityRepresentation();
            if (entity == null) return 0.3125F;
            Level world = entity.level();

            float sea = world.getSeaLevel();
            float height = entity.getBlockY();
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
