package one.dqu.additionaladditions.feature.rose_gold;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.core.material.AnimalArmorType;
import one.dqu.additionaladditions.core.material.ToolType;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAMaterials;
import one.dqu.additionaladditions.registry.AATags;

import java.util.function.Supplier;

public class RoseGoldContent {
    public static Supplier<Block> roseGoldBlock() {
        return AAReg.block(Block::new)
                .props(p -> p
                        .mapColor(MapColor.COLOR_PINK)
                        .requiresCorrectToolForDrops()
                        .strength(3.0f, 6.0f)
                        .sound(SoundType.METAL))
                .tags(BlockTags.BEACON_BASE_BLOCKS, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL,
                        BlockTags.INCORRECT_FOR_GOLD_TOOL, BlockTags.INCORRECT_FOR_STONE_TOOL, BlockTags.INCORRECT_FOR_WOODEN_TOOL)
                .model(Models::cube)
                .make("rose_gold_block");
    }

    public static Supplier<BlockItem> roseGoldBlockItem() {
        return AAReg.blockItem(AABlocks.ROSE_GOLD_BLOCK)
                .config(Config.ROSE_GOLD)
                .creative(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS, CreativePosition.AFTER)
                .tags(AATags.C_STORAGE_BLOCKS_ROSE_GOLD)
                .make("rose_gold_block");
    }

    public static Supplier<Item> roseGoldIngot() {
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .creative(Items.GOLD_INGOT, CreativeModeTabs.INGREDIENTS, CreativePosition.AFTER)
                .model(Models::flat)
                .tags(AATags.C_INGOTS_ROSE_GOLD, AATags.REPAIRS_ROSE_GOLD_ARMOR)
                .make("rose_gold_ingot");
    }

    private static Supplier<Item> armor(String id, ArmorType armorType, Item anchor) {
        TagKey<Item> slotTag = switch (armorType) {
            case HELMET -> ItemTags.HEAD_ARMOR;
            case CHESTPLATE -> ItemTags.CHEST_ARMOR;
            case LEGGINGS -> ItemTags.LEG_ARMOR;
            case BOOTS -> ItemTags.FOOT_ARMOR;
            default -> throw new IllegalArgumentException("Invalid armor slot: " + armorType);
        };
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, armorType)
                .creative(anchor, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(item -> Models.armorTrim(item, AAMaterials.ROSE_GOLD.equipmentAsset(), armorType))
                .tags(slotTag, ItemTags.TRIMMABLE_ARMOR)
                .make(id);
    }

    public static Supplier<Item> helmet() {
        return armor("rose_gold_helmet", ArmorType.HELMET, Items.GOLDEN_BOOTS);
    }

    public static Supplier<Item> chestplate() {
        return armor("rose_gold_chestplate", ArmorType.CHESTPLATE, Items.GOLDEN_BOOTS);
    }

    public static Supplier<Item> leggings() {
        return armor("rose_gold_leggings", ArmorType.LEGGINGS, Items.GOLDEN_BOOTS);
    }

    public static Supplier<Item> boots() {
        return armor("rose_gold_boots", ArmorType.BOOTS, Items.GOLDEN_BOOTS);
    }

    public static Supplier<Item> horseArmor() {
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, AnimalArmorType.HORSE)
                .props(p -> p.stacksTo(1))
                .creative(Items.GOLDEN_HORSE_ARMOR, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(Models::flat)
                .make("rose_gold_horse_armor");
    }

    public static Supplier<Item> nautilusArmor() {
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, AnimalArmorType.NAUTILUS)
                .props(p -> p.stacksTo(1))
                .creative(Items.GOLDEN_NAUTILUS_ARMOR, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(Models::flat)
                .make("rose_gold_nautilus_armor");
    }

    public static Supplier<Item> sword() {
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.SWORD)
                .creative(Items.GOLDEN_SWORD, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(Models::handheld)
                .tags(ItemTags.SWORDS)
                .make("rose_gold_sword");
    }

    public static Supplier<Item> spear() {
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.SPEAR)
                .creative(Items.GOLDEN_SPEAR, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(Models::spear)
                .tags(ItemTags.SPEARS)
                .make("rose_gold_spear");
    }

    public static Supplier<Item> shovel() {
        return AAReg.<Item>item(AAShovelItem::new)
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.SHOVEL)
                .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .model(Models::handheld)
                .tags(ItemTags.SHOVELS)
                .make("rose_gold_shovel");
    }

    public static Supplier<Item> pickaxe() {
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.PICKAXE)
                .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .model(Models::handheld)
                .tags(ItemTags.PICKAXES)
                .make("rose_gold_pickaxe");
    }

    public static Supplier<Item> axe() {
        return AAReg.<Item>item(AAAxeItem::new)
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.AXE)
                .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .creative(Items.GOLDEN_AXE, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(Models::handheld)
                .tags(ItemTags.AXES)
                .make("rose_gold_axe");
    }

    public static Supplier<Item> hoe() {
        return AAReg.<Item>item(AAHoeItem::new)
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.HOE)
                .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .model(Models::handheld)
                .tags(ItemTags.HOES)
                .make("rose_gold_hoe");
    }
}
