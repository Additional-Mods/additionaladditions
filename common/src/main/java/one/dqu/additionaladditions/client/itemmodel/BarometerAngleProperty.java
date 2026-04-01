package one.dqu.additionaladditions.client.itemmodel;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record BarometerAngleProperty() implements RangeSelectItemModelProperty {
    public static final MapCodec<BarometerAngleProperty> MAP_CODEC = MapCodec.unit(new BarometerAngleProperty());

    private static final float DEFAULT_ANGLE = 0.3125F;

    @Override
    public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        if (entity == null || level == null) return DEFAULT_ANGLE;

        float sea = level.getSeaLevel();
        float height = entity.getBlockY();
        float top = level.getMaxY();
        float bottom = level.getMinY();

        if (height > top) return 0.0F;
        if (height < bottom) return 1.0F;

        if (height >= sea) {
            return (float) ((height / (2 * (sea - top))) + 0.25 - ((sea + top) / (4.0 * (sea - top))));
        } else {
            return (float) ((height / (2 * (bottom - sea))) + 0.75 - ((bottom + sea) / (4.0 * (bottom - sea))));
        }
    }

    @Override
    public MapCodec<BarometerAngleProperty> type() {
        return MAP_CODEC;
    }
}
