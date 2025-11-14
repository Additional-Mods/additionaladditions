package dqu.additionaladditions.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

public class GlintVertexConsumer implements VertexConsumer {
    private final VertexConsumer parent;
    private final int color;

    public GlintVertexConsumer(VertexConsumer parent, int color) {
        this.parent = parent;
        this.color = color;
        this.setColor(color);
    }

    @Override
    public VertexConsumer addVertex(float f, float g, float h) {
        parent.addVertex(f, g, h);
        return this;
    }

    @Override
    public VertexConsumer setColor(int i, int j, int k, int l) {
        int r = (color >> 16 & 255);
        int g = (color >> 8 & 255);
        int b = (color & 255);
        parent.setColor(r, g, b, 255);
        return this;
    }

    @Override
    public VertexConsumer setUv(float f, float g) {
        parent.setUv(f, g);
        return this;
    }

    @Override
    public VertexConsumer setUv1(int i, int j) {
        parent.setUv1(i, j);
        return this;
    }

    @Override
    public VertexConsumer setUv2(int i, int j) {
        parent.setUv2(i, j);
        return this;
    }

    @Override
    public VertexConsumer setNormal(float f, float g, float h) {
        parent.setNormal(f, g, h);
        return this;
    }
}
