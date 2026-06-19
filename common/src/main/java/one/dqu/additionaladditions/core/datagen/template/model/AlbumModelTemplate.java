package one.dqu.additionaladditions.core.datagen.template.model;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.client.HasDiscProperty;

public class AlbumModelTemplate {
    private static final Material OVERLAY = new Material(
            Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "item/album_overlay"));

    public static void createAlbum(ItemModelGenerators gen, Item item) {
        Material texture = TextureMapping.getItemTexture(item);
        Identifier base = ModelLocationUtils.getModelLocation(item);

        Identifier empty = base.withSuffix("_0");
        ModelTemplates.FLAT_ITEM.create(empty, TextureMapping.layer0(texture), gen.modelOutput);

        Identifier filled = base.withSuffix("_1");
        ModelTemplates.TWO_LAYERED_ITEM.create(filled, TextureMapping.layered(texture, OVERLAY), gen.modelOutput);

        gen.itemModelOutput.accept(item, ItemModelUtils.conditional(
                new HasDiscProperty(), ItemModelUtils.plainModel(filled), ItemModelUtils.plainModel(empty)
        ));
    }
}
