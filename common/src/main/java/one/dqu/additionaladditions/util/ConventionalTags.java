package one.dqu.additionaladditions.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ConventionalTags {
    public static final TagKey<Item> ENCHANTABLE = item("enchantables");

    private static TagKey<Item> item(String tag) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", tag));
    }
}
