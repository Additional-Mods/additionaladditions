package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public record ItemTagUnitConfig(
        String itemTag
) {
    private static final Codec<ItemTagUnitConfig> ICODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("item_tag").forGetter(ItemTagUnitConfig::itemTag)
            ).apply(instance, ItemTagUnitConfig::new)
    );

    public static final Codec<TagKey<Item>> CODEC = ICODEC.xmap(
            ItemTagUnitConfig::toItem,
            ItemTagUnitConfig::fromItem
    );

    private TagKey<Item> toItem() {
        String item = this.itemTag;
        if (item.startsWith("#")) item = item.substring(1);
        return TagKey.create(Registries.ITEM, ResourceLocation.tryParse(item));
    }

    private static ItemTagUnitConfig fromItem(TagKey<Item> item) {
        return new ItemTagUnitConfig("#" + item.location());
    }
}
