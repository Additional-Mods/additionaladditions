package one.dqu.additionaladditions.feature.glint;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.TextureTransform;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import one.dqu.additionaladditions.AdditionalAdditions;

import java.util.*;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class GlintRenderTypes {
    private static final Map<DyeColor, RenderType> GLINT = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, RenderType> ARMOR_ENTITY_GLINT = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, RenderType> ENTITY_GLINT = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, RenderType> GLINT_TRANSLUCENT = new EnumMap<>(DyeColor.class);

    // matches vanilla AbstractTexture's sampler which is used by glints. DynamicTexture used in GlintResourceGenerator uses a different sampler by default.
    private static final Supplier<GpuSampler> GLINT_SAMPLER = () ->
            RenderSystem.getSamplerCache().getSampler(AddressMode.REPEAT, AddressMode.REPEAT, FilterMode.NEAREST, FilterMode.LINEAR, false);

    public static RenderType getGlint(DyeColor color) {
        return GLINT.get(color);
    }

    public static RenderType getArmorEntityGlint(DyeColor color) {
        return ARMOR_ENTITY_GLINT.get(color);
    }

    public static RenderType getEntityGlint(DyeColor color) {
        return ENTITY_GLINT.get(color);
    }

    public static RenderType getGlintTranslucent(DyeColor color) {
        return GLINT_TRANSLUCENT.get(color);
    }

    public static Collection<RenderType> getRenderTypes() {
        Set<RenderType> types = new HashSet<>();
        types.addAll(GLINT.values());
        types.addAll(ARMOR_ENTITY_GLINT.values());
        types.addAll(ENTITY_GLINT.values());
        types.addAll(GLINT_TRANSLUCENT.values());
        return types;
    }
    static {
        for (DyeColor color : DyeColor.values()) {
            Identifier itemTexture = Identifier.tryBuild(
                    AdditionalAdditions.NAMESPACE,
                    "textures/util/enchanted_item_glint_" + color.getName().toLowerCase(Locale.ROOT) + ".png"
            );
            Identifier entityTexture = Identifier.tryBuild(
                    AdditionalAdditions.NAMESPACE,
                    "textures/util/enchanted_entity_glint_" + color.getName().toLowerCase(Locale.ROOT) + ".png"
            );

            GLINT.put(color, createGlint(itemTexture));
            ARMOR_ENTITY_GLINT.put(color, createArmorEntityGlint(entityTexture));
            ENTITY_GLINT.put(color, createEntityGlint(itemTexture));
            GLINT_TRANSLUCENT.put(color, createGlintTranslucent(itemTexture));
        }
    }

    // using vanilla render pipeline and not a custom one for compatibility with shader packs / iris
    // instead the texture used by the vanilla sampler is swapped out for our tinted textures

    private static RenderType createGlint(Identifier texture) {
        RenderSetup setup = RenderSetup.builder(RenderPipelines.GLINT)
                .withTexture("Sampler0", texture, GLINT_SAMPLER)
                .setTextureTransform(TextureTransform.GLINT_TEXTURING)
                .createRenderSetup();
        return RenderType.create("glint", setup);
    }

    private static RenderType createArmorEntityGlint(Identifier texture) {
        RenderSetup setup = RenderSetup.builder(RenderPipelines.GLINT)
                .withTexture("Sampler0", texture, GLINT_SAMPLER)
                .setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
                .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                .createRenderSetup();
        return RenderType.create("armor_entity_glint", setup);
    }

    private static RenderType createEntityGlint(Identifier texture) {
        RenderSetup setup = RenderSetup.builder(RenderPipelines.GLINT)
                .withTexture("Sampler0", texture, GLINT_SAMPLER)
                .setTextureTransform(TextureTransform.ENTITY_GLINT_TEXTURING)
                .createRenderSetup();
        return RenderType.create("entity_glint", setup);
    }

    private static RenderType createGlintTranslucent(Identifier texture) {
        RenderSetup setup = RenderSetup.builder(RenderPipelines.GLINT)
                .withTexture("Sampler0", texture, GLINT_SAMPLER)
                .setTextureTransform(TextureTransform.GLINT_TEXTURING)
                .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                .createRenderSetup();
        return RenderType.create("glint_translucent", setup);
    }
}
