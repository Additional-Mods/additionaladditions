package one.dqu.additionaladditions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.resources.ResourceLocation;
import one.dqu.additionaladditions.client.itemmodel.BarometerAngleProperty;
import one.dqu.additionaladditions.client.itemmodel.HasDiscProperty;

@Environment(EnvType.CLIENT)
public final class AdditionalAdditionsClient {
    public static void init() {
        // Register item model properties

        ConditionalItemModelProperties.ID_MAPPER.put(
            ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "has_disc"),
            HasDiscProperty.MAP_CODEC
        );

        RangeSelectItemModelProperties.ID_MAPPER.put(
            ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "barometer_angle"),
            BarometerAngleProperty.MAP_CODEC
        );
    }
}
