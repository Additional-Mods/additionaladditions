package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.item.*;
import dqu.additionaladditions.material.*;
import dqu.additionaladditions.misc.CreativeAdder;
import dqu.additionaladditions.misc.LootHandler;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;

public class AdditionalMaterials {
    public static final ArmorMaterial ROSE_GOLD_ARMOR_MATERIAL = new RoseGoldArmorMaterial();
    public static final Item ROSE_GOLD_HELMET = new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties());
    public static final Item ROSE_GOLD_CHESTPLATE = new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final Item ROSE_GOLD_LEGGINGS = new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties());
    public static final Item ROSE_GOLD_BOOTS = new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties());
    public static final Item ROSE_GOLD_SWORD = new AdditionalSwordItem(RoseGoldToolMaterial.MATERIAL, 4, -2.4F, new Item.Properties());
    public static final Item ROSE_GOLD_PICKAXE = new AdditionalPickaxeItem(RoseGoldToolMaterial.MATERIAL, 1, -2.8F, new Item.Properties());
    public static final Item ROSE_GOLD_AXE = new AdditionalAxeItem(RoseGoldToolMaterial.MATERIAL, 6, -3.1F, new Item.Properties());
    public static final Item ROSE_GOLD_HOE = new AdditionalHoeItem(RoseGoldToolMaterial.MATERIAL, -2, -1F, new Item.Properties());
    public static final Item ROSE_GOLD_SHOVEL = new AdditionalShovelItem(RoseGoldToolMaterial.MATERIAL, 1.5F, -3F, new Item.Properties());
    public static final Item ROSE_GOLD_UPGRADE = AdditionalSmithingTemplate.create("rose_gold_upgrade", AdditionalSmithingTemplate.iconsEquipment(), List.of(AdditionalSmithingTemplate.SmithingIcon.ALLOY.location()));

    public static final ArmorMaterial GILDED_NETHERITE_ARMOR_MATERIAL = new GildedNetheriteArmorMaterial();
    public static final Item GILDED_NETHERITE_HELMET = new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties().fireResistant());
    public static final Item GILDED_NETHERITE_CHESTPLATE = new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant());
    public static final Item GILDED_NETHERITE_LEGGINGS = new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant());
    public static final Item GILDED_NETHERITE_BOOTS = new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant());
    public static final Item GILDED_NETHERITE_SWORD = new AdditionalSwordItem(GildedNetheriteToolMaterial.MATERIAL, 5, -2.4F, new Item.Properties().fireResistant());
    public static final Item GILDED_NETHERITE_PICKAXE = new AdditionalPickaxeItem(GildedNetheriteToolMaterial.MATERIAL, 3, -2.6F, new Item.Properties().fireResistant());
    public static final Item GILDED_NETHERITE_AXE = new AdditionalAxeItem(GildedNetheriteToolMaterial.MATERIAL, 7, -3F, new Item.Properties().fireResistant());
    public static final Item GILDED_NETHERITE_HOE = new AdditionalHoeItem(GildedNetheriteToolMaterial.MATERIAL, -2, 0, new Item.Properties().fireResistant());
    public static final Item GILDED_NETHERITE_SHOVEL = new AdditionalShovelItem(GildedNetheriteToolMaterial.MATERIAL, 3.5F, -3F, new Item.Properties().fireResistant());
    public static final Item GILDED_NETHERITE_UPGRADE = AdditionalSmithingTemplate.create("gilded_netherite_upgrade", AdditionalSmithingTemplate.iconsEquipment(), List.of(AdditionalSmithingTemplate.SmithingIcon.RING.location()));

    public static void registerAll() {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_helmet"), ROSE_GOLD_HELMET);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_chestplate"), ROSE_GOLD_CHESTPLATE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_leggings"), ROSE_GOLD_LEGGINGS);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_boots"), ROSE_GOLD_BOOTS);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_sword"), ROSE_GOLD_SWORD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_pickaxe"), ROSE_GOLD_PICKAXE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_axe"), ROSE_GOLD_AXE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_shovel"), ROSE_GOLD_SHOVEL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_hoe"), ROSE_GOLD_HOE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_upgrade"), ROSE_GOLD_UPGRADE);

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_helmet"), GILDED_NETHERITE_HELMET);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_chestplate"), GILDED_NETHERITE_CHESTPLATE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_leggings"), GILDED_NETHERITE_LEGGINGS);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_boots"), GILDED_NETHERITE_BOOTS);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_sword"), GILDED_NETHERITE_SWORD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_pickaxe"), GILDED_NETHERITE_PICKAXE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_axe"), GILDED_NETHERITE_AXE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_shovel"), GILDED_NETHERITE_SHOVEL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_hoe"), GILDED_NETHERITE_HOE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_upgrade"), GILDED_NETHERITE_UPGRADE);

        LootHandler.register(List.of(BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.PILLAGER_OUTPOST, BuiltInLootTables.UNDERWATER_RUIN_SMALL, BuiltInLootTables.UNDERWATER_RUIN_BIG, BuiltInLootTables.SHIPWRECK_TREASURE), () -> Config.getBool(ConfigValues.ROSE_GOLD), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.32f))
                .add(LootItem.lootTableItem(ROSE_GOLD_UPGRADE))
        );
        LootHandler.register(BuiltInLootTables.RUINED_PORTAL, () -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.45f))
                .add(LootItem.lootTableItem(GILDED_NETHERITE_UPGRADE))
        );

        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_HOE, ROSE_GOLD_SHOVEL, ROSE_GOLD_PICKAXE, ROSE_GOLD_AXE, ROSE_GOLD_HOE);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_HOE, GILDED_NETHERITE_SHOVEL, GILDED_NETHERITE_PICKAXE, GILDED_NETHERITE_AXE, GILDED_NETHERITE_HOE);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_SWORD, ROSE_GOLD_SWORD);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_AXE, ROSE_GOLD_AXE);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_BOOTS, ROSE_GOLD_HELMET, ROSE_GOLD_CHESTPLATE, ROSE_GOLD_LEGGINGS, ROSE_GOLD_BOOTS);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_SWORD, GILDED_NETHERITE_SWORD);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_AXE, GILDED_NETHERITE_AXE);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_BOOTS, GILDED_NETHERITE_HELMET, GILDED_NETHERITE_CHESTPLATE, GILDED_NETHERITE_LEGGINGS, GILDED_NETHERITE_BOOTS);
        CreativeAdder.INGREDIENTS.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, ROSE_GOLD_UPGRADE);
        CreativeAdder.INGREDIENTS.add(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, GILDED_NETHERITE_UPGRADE);
    }
}
