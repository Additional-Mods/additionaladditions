package one.dqu.additionaladditions.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import one.dqu.additionaladditions.AdditionalAdditions;


public class AATags {
    // AddAdd Tags

    public static final TagKey<Item> SUSPICIOUS_DYES = aitem("suspicious_dyes");

    public static final TagKey<Block> WRENCH_BLACKLIST = ablock("wrench_blacklisted");

    public static final TagKey<Item> ALBUMS = aitem("albums");

    public static final TagKey<Item> REPAIRS_ROSE_GOLD_ARMOR = aitem("repairs_rose_gold_armor");

    // Conventional Tags

    public static final TagKey<Item> C_ENCHANTABLE = citem("enchantables");

    public static final TagKey<Item> C_ROPES_ITEM = citem("ropes");
    public static final TagKey<Block> C_ROPES_BLOCK = cblock("ropes");

    public static final TagKey<Item> C_FLOWERS_ITEM = citem("flowers");
    public static final TagKey<Block> C_FLOWERS_BLOCK = cblock("flowers");
    public static final TagKey<Item> C_FLOWERS_SMALL_ITEM = citem("flowers/small");
    public static final TagKey<Block> C_FLOWERS_SMALL_BLOCK = cblock("flowers/small");
    public static final TagKey<Item> C_FLOWERS_TALL_ITEM = citem("flowers/tall");
    public static final TagKey<Block> C_FLOWERS_TALL_BLOCK = cblock("flowers/tall");

    public static final TagKey<Item> C_SEEDS = citem("seeds");
    public static final TagKey<Item> C_FOODS = citem("foods");
    public static final TagKey<Item> C_FOODS_PIE = citem("foods/pie");
    public static final TagKey<Item> C_EGGS = citem("eggs");
    public static final TagKey<Item> C_MUSIC_DISCS = citem("music_discs");
    public static final TagKey<Item> C_TOOLS_WRENCH = citem("tools/wrench");
    public static final TagKey<Item> C_INGOTS_ROSE_GOLD = citem("ingots/rose_gold");
    public static final TagKey<Item> C_STORAGE_BLOCKS_ROSE_GOLD = citem("storage_blocks/rose_gold");

    // i don't know if this is right. (used in AlbumContent)
    public static TagKey<Item> dyed(DyeColor color) {
        return citem("dyed/" + color.getName());
    }

    // helper methods

    private static TagKey<Item> aitem(String tag) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, tag));
    }

    private static TagKey<Block> ablock(String tag) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, tag));
    }

    private static TagKey<Item> citem(String tag) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", tag));
    }

    private static TagKey<Block> cblock(String tag) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", tag));
    }

    public static void registerAll() {

    }
}
