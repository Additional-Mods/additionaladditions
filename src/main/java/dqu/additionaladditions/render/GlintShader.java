package dqu.additionaladditions.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import dqu.additionaladditions.AdditionalAdditions;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;

public class GlintShader {
    public static ShaderInstance GLINT;

    public static void register(CoreShaderRegistrationCallback.RegistrationContext context) throws IOException {
        context.register(
                ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.namespace, "rendertype_glint"),
                DefaultVertexFormat.POSITION_TEX,
                shader -> GLINT = shader
        );
    }
}
