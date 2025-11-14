package dqu.additionaladditions.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;

@Environment(EnvType.CLIENT)
public class GlintRenderType {
    public static final RenderType GLINT_RENDER_LAYER = RenderType.create(
            "glint", DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, 1536,
            RenderType.CompositeState.builder().setShaderState(
                    new RenderStateShard.ShaderStateShard(() -> GlintShader.GLINT))
                    .setTextureState(new RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ITEM, true, false))
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE).setCullState(RenderStateShard.NO_CULL)
                    .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST).setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                    .setTexturingState(RenderStateShard.GLINT_TEXTURING).createCompositeState(false)
    );
}
