package one.dqu.additionaladditions.feature.rose_gold;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.core.material.AnimalArmorType;
import one.dqu.additionaladditions.core.material.ToolType;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMaterials;
import one.dqu.additionaladditions.registry.AATags;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("Convert2MethodRef")
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
        ItemLike rosegold = () -> AAItems.ROSE_GOLD_INGOT.get();
        return AAReg.blockItem(AABlocks.ROSE_GOLD_BLOCK)
                .config(Config.ROSE_GOLD)
                .creative(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS, CreativePosition.AFTER)
                .tags(AATags.C_STORAGE_BLOCKS_ROSE_GOLD)
                .recipe(Recipes.shaped("RRR", "RRR", "RRR", Map.of('R', rosegold), RecipeCategory.BUILDING_BLOCKS).unlockedBy(rosegold))
                .make("rose_gold_block");
    }

    public static Supplier<Item> roseGoldIngot() {
        ItemLike block = () -> AAItems.ROSE_GOLD_BLOCK.get();
        ItemLike gold = Items.GOLD_INGOT;
        ItemLike copper = Items.COPPER_INGOT;
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .creative(Items.GOLD_INGOT, CreativeModeTabs.INGREDIENTS, CreativePosition.AFTER)
                .model(Models::flat)
                .tags(AATags.C_INGOTS_ROSE_GOLD, AATags.REPAIRS_ROSE_GOLD_ARMOR)
                .recipe(Recipes.shapeless(
                        RecipeCategory.MISC,
                        gold, gold, gold, gold,
                        copper, copper, copper, copper
                ).unlockedBy(gold, copper).group("rose_gold_ingot"))
                .recipe(Recipes.shapeless(
                        RecipeCategory.MISC, 9, block
                ).unlockedBy(block).named("%s_from_block").group("rose_gold_ingot"))
                .make("rose_gold_ingot");
    }

    private static Supplier<Item> armor(String id, ArmorType armorType, Item anchor) {
        ItemLike rosegold = () -> AAItems.ROSE_GOLD_INGOT.get();
        TagKey<Item> slotTag = switch (armorType) {
            case HELMET -> ItemTags.HEAD_ARMOR;
            case CHESTPLATE -> ItemTags.CHEST_ARMOR;
            case LEGGINGS -> ItemTags.LEG_ARMOR;
            case BOOTS -> ItemTags.FOOT_ARMOR;
            default -> throw new IllegalArgumentException("Invalid armor slot: " + armorType);
        };
        ItemLike inputItem = switch (armorType) {
            case HELMET -> Items.IRON_HELMET;
            case CHESTPLATE -> Items.IRON_CHESTPLATE;
            case LEGGINGS -> Items.IRON_LEGGINGS;
            case BOOTS -> Items.IRON_BOOTS;
            default -> throw new IllegalArgumentException("Invalid armor slot: " + armorType);
        };
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, armorType)
                .creative(anchor, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(item -> Models.armorTrim(item, AAMaterials.ROSE_GOLD.equipmentAsset(), armorType))
                .tags(slotTag, ItemTags.TRIMMABLE_ARMOR)
                .recipe(Recipes.transmute(RecipeCategory.COMBAT, inputItem, rosegold).unlockedBy(rosegold))
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
        ItemLike rosegold = () -> AAItems.ROSE_GOLD_INGOT.get();
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, AnimalArmorType.HORSE)
                .props(p -> p.stacksTo(1))
                .creative(Items.GOLDEN_HORSE_ARMOR, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(Models::flat)
                .recipe(Recipes.transmute(RecipeCategory.COMBAT, Items.IRON_HORSE_ARMOR, rosegold).unlockedBy(rosegold))
                .make("rose_gold_horse_armor");
    }

    public static Supplier<Item> nautilusArmor() {
        ItemLike rosegold = () -> AAItems.ROSE_GOLD_INGOT.get();
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, AnimalArmorType.NAUTILUS)
                .props(p -> p.stacksTo(1))
                .creative(Items.GOLDEN_NAUTILUS_ARMOR, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(Models::flat)
                .recipe(Recipes.transmute(RecipeCategory.COMBAT, Items.IRON_NAUTILUS_ARMOR, rosegold).unlockedBy(rosegold))
                .make("rose_gold_nautilus_armor");
    }

    public static Supplier<Item> sword() {
        ItemLike rosegold = () -> AAItems.ROSE_GOLD_INGOT.get();
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.SWORD)
                .creative(Items.GOLDEN_SWORD, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(Models::handheld)
                .tags(ItemTags.SWORDS)
                .recipe(Recipes.transmute(RecipeCategory.COMBAT, Items.IRON_SWORD, rosegold).unlockedBy(rosegold))
                .make("rose_gold_sword");
    }

    public static Supplier<Item> spear() {
        ItemLike rosegold = () -> AAItems.ROSE_GOLD_INGOT.get();
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.SPEAR)
                .creative(Items.GOLDEN_SPEAR, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(Models::spear)
                .tags(ItemTags.SPEARS)
                .recipe(Recipes.transmute(RecipeCategory.COMBAT, Items.IRON_SPEAR, rosegold).unlockedBy(rosegold))
                .make("rose_gold_spear");
    }

    public static Supplier<Item> shovel() {
        ItemLike rosegold = () -> AAItems.ROSE_GOLD_INGOT.get();
        return AAReg.<Item>item(AAShovelItem::new)
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.SHOVEL)
                .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .model(Models::handheld)
                .tags(ItemTags.SHOVELS)
                .recipe(Recipes.transmute(RecipeCategory.TOOLS, Items.IRON_SHOVEL, rosegold).unlockedBy(rosegold))
                .make("rose_gold_shovel");
    }

    public static Supplier<Item> pickaxe() {
        ItemLike rosegold = () -> AAItems.ROSE_GOLD_INGOT.get();
        return AAReg.item()
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.PICKAXE)
                .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .model(Models::handheld)
                .tags(ItemTags.PICKAXES)
                .recipe(Recipes.transmute(RecipeCategory.TOOLS, Items.IRON_PICKAXE, rosegold).unlockedBy(rosegold))
                .make("rose_gold_pickaxe");
    }

    public static Supplier<Item> axe() {
        ItemLike rosegold = () -> AAItems.ROSE_GOLD_INGOT.get();
        return AAReg.<Item>item(AAAxeItem::new)
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.AXE)
                .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .creative(Items.GOLDEN_AXE, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(Models::handheld)
                .tags(ItemTags.AXES)
                .recipe(Recipes.transmute(RecipeCategory.COMBAT, Items.IRON_AXE, rosegold).unlockedBy(rosegold))
                .make("rose_gold_axe");
    }

    public static Supplier<Item> hoe() {
        ItemLike rosegold = () -> AAItems.ROSE_GOLD_INGOT.get();
        return AAReg.<Item>item(AAHoeItem::new)
                .config(Config.ROSE_GOLD)
                .material(AAMaterials.ROSE_GOLD, ToolType.HOE)
                .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .model(Models::handheld)
                .tags(ItemTags.HOES)
                .recipe(Recipes.transmute(RecipeCategory.TOOLS, Items.IRON_HOE, rosegold).unlockedBy(rosegold))
                .make("rose_gold_hoe");
    }
}
