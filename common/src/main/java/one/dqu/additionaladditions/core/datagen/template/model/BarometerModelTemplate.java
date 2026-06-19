package one.dqu.additionaladditions.core.datagen.template.model;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import one.dqu.additionaladditions.client.BarometerAngleProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BarometerModelTemplate {
    public static void createBarometer(ItemModelGenerators gen, Item item) {
        Identifier base = ModelLocationUtils.getModelLocation(item);
        List<RangeSelectItemModel.Entry> overrides = new ArrayList<>(16);
        Identifier fallback = null;

        for (int i = 0; i < 16; i++) {
            String suffix = String.format(Locale.ROOT, "_%02d", i);
            Identifier frame = base.withSuffix(suffix);

            ModelTemplates.FLAT_ITEM.create(frame, TextureMapping.layer0(TextureMapping.getItemTexture(item, suffix)), gen.modelOutput);
            overrides.add(ItemModelUtils.override(ItemModelUtils.plainModel(frame), (float) i / 16));
            
            if (i == 5) {
                fallback = frame;
            }
        }

        gen.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(
                new BarometerAngleProperty(), ItemModelUtils.plainModel(fallback), overrides)
        );
    }
}
