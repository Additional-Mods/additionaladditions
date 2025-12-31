package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public record ItemUnitConfig(
        String item
) {
    private static final Codec<ItemUnitConfig> ICODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("item").forGetter(ItemUnitConfig::item)
            ).apply(instance, ItemUnitConfig::new)
    );

    public static final Codec<Supplier<Item>> CODEC = ICODEC.xmap(
            ItemUnitConfig::toItem,
            ItemUnitConfig::fromItem
    );

    private Supplier<Item> toItem() {
        return () -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(this.item));
    }

    private static ItemUnitConfig fromItem(Supplier<Item> item) {
        return new ItemUnitConfig(BuiltInRegistries.ITEM.getKey(item.get()).toString());
    }
}
