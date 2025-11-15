package dqu.additionaladditions.glint;

import com.mojang.blaze3d.platform.NativeImage;
import dqu.additionaladditions.AdditionalAdditions;
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

import java.util.Locale;

@Environment(EnvType.CLIENT)
public class GlintResourceGenerator {
    /**
     * Generates tinted enchantment glint textures and registers them with the texture manager.
     */
    public static void generateResources(ResourceManager resourceManager) {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();

        try {
            Resource resource = resourceManager.getResource(ItemRenderer.ENCHANTED_GLINT_ITEM).orElseThrow();
            NativeImage image = NativeImage.read(resource.open());

            for (DyeColor color : DyeColor.values()) {
                NativeImage tinted = generateTintedTexture(image, color.getTextColor());
                ResourceLocation tintedLocation = ResourceLocation.tryBuild(
                        AdditionalAdditions.namespace,
                        "textures/misc/enchanted_item_glint_" + color.getName().toLowerCase(Locale.ROOT) + ".png"
                );
                textureManager.register(tintedLocation, new DynamicTexture(tinted));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate colored enchantment glint resources", e);
        }
    }

    /**
     * Tints the given image by gray-scaling it and multiplying by the tint color.
     */
    private static NativeImage generateTintedTexture(NativeImage image, int tint) {
        int width = image.getWidth();
        int height = image.getHeight();
        NativeImage tintedImage = new NativeImage(width, height, false);

        int tintR = (tint >> 16) & 0xFF;
        int tintG = (tint >> 8) & 0xFF;
        int tintB = tint & 0xFF;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = image.getPixelRGBA(x, y);
                int alpha = (pixel >> 24) & 0xFF;

                int pixelR = (pixel >> 16) & 0xFF;
                int pixelG = (pixel >> 8) & 0xFF;
                int pixelB = pixel & 0xFF;

                int gray = (int) (0.299 * pixelR + 0.587 * pixelG + 0.114 * pixelB);

                int tintedR = Math.min(255, (gray * tintR * 2) / 255);
                int tintedG = Math.min(255, (gray * tintG * 2) / 255);
                int tintedB = Math.min(255, (gray * tintB * 2) / 255);

                int tintedPixel = (alpha << 24) | (tintedB << 16) | (tintedG << 8) | tintedR;
                tintedImage.setPixelRGBA(x, y, tintedPixel);
            }
        }

        return tintedImage;
    }
}
