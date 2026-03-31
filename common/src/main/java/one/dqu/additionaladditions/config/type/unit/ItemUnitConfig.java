package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public record ItemUnitConfig(
        String item
) {
    private static final Codec<ItemUnitConfig> ICODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("item").forGetter(ItemUnitConfig::item)
            ).apply(instance, ItemUnitConfig::new)
    );

    public static final Codec<TagKey<Item>> CODEC = ICODEC.xmap(
            ItemUnitConfig::toItem,
            ItemUnitConfig::fromItem
    );

    private TagKey<Item> toItem() {
        return TagKey.create(Registries.ITEM, ResourceLocation.tryParse(item));
    }

    private static ItemUnitConfig fromItem(TagKey<Item> item) {
        return new ItemUnitConfig(item.location().toString());
    }
}
