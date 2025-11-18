package one.dqu.additionaladditions.glint;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import one.dqu.additionaladditions.AdditionalAdditions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.*;

@Environment(EnvType.CLIENT)
public class GlintRenderType {
    private static final Map<DyeColor, RenderType> GLINT = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, RenderType> ARMOR_ENTITY_GLINT = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, RenderType> ENTITY_GLINT_DIRECT = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, RenderType> ENTITY_GLINT = new EnumMap<>(DyeColor.class);
    private static final Map<DyeColor, RenderType> GLINT_TRANSLUCENT = new EnumMap<>(DyeColor.class);

    public static RenderType getGlint(DyeColor color) {
        return GLINT.get(color);
    }

    public static RenderType getArmorEntityGlint(DyeColor color) {
        return ARMOR_ENTITY_GLINT.get(color);
    }

    public static RenderType getEntityGlintDirect(DyeColor color) {
        return ENTITY_GLINT_DIRECT.get(color);
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
        types.addAll(ENTITY_GLINT_DIRECT.values());
        types.addAll(ENTITY_GLINT.values());
        types.addAll(GLINT_TRANSLUCENT.values());
        return types;
    }

    static {
        for (DyeColor color : DyeColor.values()) {
            ResourceLocation itemTexture = ResourceLocation.tryBuild(
                    AdditionalAdditions.NAMESPACE,
                    "textures/util/enchanted_item_glint_" + color.getName().toLowerCase(Locale.ROOT) + ".png"
            );
            ResourceLocation entityTexture = ResourceLocation.tryBuild(
                    AdditionalAdditions.NAMESPACE,
                    "textures/util/enchanted_entity_glint_" + color.getName().toLowerCase(Locale.ROOT) + ".png"
            );

            GLINT.put(color, createGlintRenderType(itemTexture));
            ARMOR_ENTITY_GLINT.put(color, createArmorEntityGlintRenderType(entityTexture));
            ENTITY_GLINT_DIRECT.put(color, createEntityGlintDirect(entityTexture));
            ENTITY_GLINT.put(color, createEntityGlint(entityTexture));
            GLINT_TRANSLUCENT.put(color, createGlintTranslucent(itemTexture));
        }
    }

    // using vanilla shader and not a custom core shader for compatibility with shader packs / iris
    // instead the texture used by the vanilla shader is swapped out for our tinted textures

    private static RenderType createGlintRenderType(ResourceLocation texture) {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_GLINT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                .createCompositeState(false);

        return RenderType.create(
                "glint",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                1536,
                state
        );
    }

    private static RenderType createArmorEntityGlintRenderType(ResourceLocation texture) {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ARMOR_ENTITY_GLINT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                .createCompositeState(false);

        return RenderType.create(
                "armor_entity_glint",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                1536,
                state
        );
    }

    private static RenderType createEntityGlintDirect(ResourceLocation texture) {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                .createCompositeState(false);

        return RenderType.create(
                "entity_glint_direct",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                1536,
                state
        );
    }

    private static RenderType createEntityGlint(ResourceLocation texture) {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_GLINT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                .createCompositeState(false);

        return RenderType.create(
                "entity_glint",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                1536,
                state
        );
    }

    private static RenderType createGlintTranslucent(ResourceLocation texture) {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_GLINT_TRANSLUCENT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                .createCompositeState(false);

        return RenderType.create(
                "glint_translucent",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                1536,
                state
        );
    }
}