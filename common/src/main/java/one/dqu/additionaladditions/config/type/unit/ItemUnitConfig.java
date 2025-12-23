package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public record ItemUnitConfig(
        Supplier<Item> item
) {
    private static final Codec<ItemUnitConfig> ICODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.xmap(
                            ItemUnitConfig::toItem,
                            ItemUnitConfig::fromItem
                    ).fieldOf("item").forGetter(ItemUnitConfig::item)
            ).apply(instance, ItemUnitConfig::new)
    );

    public static final Codec<Supplier<Item>> CODEC = ICODEC.xmap(
            ItemUnitConfig::item,
            ItemUnitConfig::new
    );

    private static Supplier<Item> toItem(String id) {
        return () -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
    }

    private static String fromItem(Supplier<Item> item) {
        return BuiltInRegistries.ITEM.getKey(item.get()).toString();
    }
}
