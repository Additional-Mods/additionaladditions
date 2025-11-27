package one.dqu.additionaladditions.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RopeArrowRenderer extends ArrowRenderer<RopeArrow> {
    public RopeArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(RopeArrow entity) {
        return TippableArrowRenderer.NORMAL_ARROW_LOCATION;
    }
}
