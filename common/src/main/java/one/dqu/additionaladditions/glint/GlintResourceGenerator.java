package one.dqu.additionaladditions.glint;

import com.mojang.blaze3d.platform.NativeImage;
import one.dqu.additionaladditions.AdditionalAdditions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class GlintResourceGenerator {
    private static final Map<DyeColor, Float> FIDDLES = new EnumMap<>(DyeColor.class);

    /**
     * Generates tinted enchantment glint textures and registers them with the texture manager.
     */
    public static void generateResources(ResourceManager resourceManager) {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();

        try {
            Resource itemResource = resourceManager.getResource(ItemRenderer.ENCHANTED_GLINT_ITEM).orElseThrow();
            Resource entityResource = resourceManager.getResource(ItemRenderer.ENCHANTED_GLINT_ENTITY).orElseThrow();
            NativeImage itemImage = NativeImage.read(itemResource.open());
            NativeImage entityImage = NativeImage.read(entityResource.open());

            for (DyeColor color : DyeColor.values()) {
                NativeImage tintedItem = generateTintedTexture(itemImage, color, false);
                ResourceLocation tintedItemLocation = ResourceLocation.tryBuild(
                        AdditionalAdditions.NAMESPACE,
                        "textures/util/enchanted_item_glint_" + color.getName().toLowerCase(Locale.ROOT) + ".png"
                );
                textureManager.register(tintedItemLocation, new DynamicTexture(tintedItem));

                NativeImage tintedEntity = generateTintedTexture(entityImage, color, true);
                ResourceLocation tintedEntityLocation = ResourceLocation.tryBuild(
                        AdditionalAdditions.NAMESPACE,
                        "textures/util/enchanted_entity_glint_" + color.getName().toLowerCase(Locale.ROOT) + ".png"
                );
                textureManager.register(tintedEntityLocation, new DynamicTexture(tintedEntity));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate colored enchantment glint resources", e);
        }
    }

    /**
     * Tints the given image by gray-scaling it and multiplying by the tint color.
     */
    private static NativeImage generateTintedTexture(NativeImage image, DyeColor color, boolean armor) {
        int width = image.getWidth();
        int height = image.getHeight();
        NativeImage tintedImage = new NativeImage(width, height, false);

        int tint = color.getTextColor();
        int tintR = (tint >> 16) & 0xFF;
        int tintG = (tint >> 8) & 0xFF;
        int tintB = tint & 0xFF;

        float fiddle = FIDDLES.getOrDefault(color, 1.5F);
        if (armor) fiddle *= 1.2F;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = image.getPixelRGBA(x, y);
                int alpha = (pixel >> 24) & 0xFF;

                int pixelR = (pixel >> 16) & 0xFF;
                int pixelG = (pixel >> 8) & 0xFF;
                int pixelB = pixel & 0xFF;

                int gray = (int) (0.299 * pixelR + 0.587 * pixelG + 0.114 * pixelB);

                int tintedR = Math.min(255, (int) ((gray * tintR * fiddle) / 255));
                int tintedG = Math.min(255, (int) ((gray * tintG * fiddle) / 255));
                int tintedB = Math.min(255, (int) ((gray * tintB * fiddle) / 255));

                int tintedPixel = (alpha << 24) | (tintedB << 16) | (tintedG << 8) | tintedR;
                tintedImage.setPixelRGBA(x, y, tintedPixel);
            }
        }

        return tintedImage;
    }

    static {
        // Manually adjust the fiddles so the brightness is more consistent
        // I tried calculating these automatically but it didn't work so if you figure it out please please please PR thanks
        FIDDLES.put(DyeColor.YELLOW, 1.6F);
        FIDDLES.put(DyeColor.PINK, 2.0F);
        FIDDLES.put(DyeColor.RED, 2.0F);
        FIDDLES.put(DyeColor.ORANGE, 2.0F);
        FIDDLES.put(DyeColor.BROWN, 3.5F);
    }
}
