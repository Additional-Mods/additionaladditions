package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.item.*;
import dqu.additionaladditions.material.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;

public class AdditionalMaterials {
    public static final ArmorMaterial ROSE_GOLD_ARMOR_MATERIAL = new RoseGoldArmorMaterial();
    public static final Item ROSE_GOLD_HELMET = new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    public static final Item ROSE_GOLD_CHESTPLATE = new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    public static final Item ROSE_GOLD_LEGGINGS = new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    public static final Item ROSE_GOLD_BOOTS = new AdditionalArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    public static final Item ROSE_GOLD_SWORD = new AdditionalSwordItem(RoseGoldToolMaterial.MATERIAL, 4, -2.4F, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    public static final Item ROSE_GOLD_PICKAXE = new AdditionalPickaxeItem(RoseGoldToolMaterial.MATERIAL, 1, -2.8F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS));
    public static final Item ROSE_GOLD_AXE = new AdditionalAxeItem(RoseGoldToolMaterial.MATERIAL, 6, -3.1F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS));
    public static final Item ROSE_GOLD_HOE = new AdditionalHoeItem(RoseGoldToolMaterial.MATERIAL, -2, -1F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS));
    public static final Item ROSE_GOLD_SHOVEL = new AdditionalShovelItem(RoseGoldToolMaterial.MATERIAL, 1.5F, -3F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS));

    public static final ArmorMaterial GILDED_NETHERITE_ARMOR_MATERIAL = new GildedNetheriteArmorMaterial();
    public static final Item GILDED_NETHERITE_HELMET = new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).fireResistant());
    public static final Item GILDED_NETHERITE_CHESTPLATE = new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).fireResistant());
    public static final Item GILDED_NETHERITE_LEGGINGS = new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).fireResistant());
    public static final Item GILDED_NETHERITE_BOOTS = new AdditionalArmorItem(GILDED_NETHERITE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).fireResistant());
    public static final Item GILDED_NETHERITE_SWORD = new AdditionalSwordItem(GildedNetheriteToolMaterial.MATERIAL, 5, -2.4F, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).fireResistant());
    public static final Item GILDED_NETHERITE_PICKAXE = new AdditionalPickaxeItem(GildedNetheriteToolMaterial.MATERIAL, 3, -2.6F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).fireResistant());
    public static final Item GILDED_NETHERITE_AXE = new AdditionalAxeItem(GildedNetheriteToolMaterial.MATERIAL, 7, -3F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).fireResistant());
    public static final Item GILDED_NETHERITE_HOE = new AdditionalHoeItem(GildedNetheriteToolMaterial.MATERIAL, -2, 0, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).fireResistant());
    public static final Item GILDED_NETHERITE_SHOVEL = new AdditionalShovelItem(GildedNetheriteToolMaterial.MATERIAL, 3.5F, -3F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).fireResistant());

    public static void registerAll() {
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_helmet"), ROSE_GOLD_HELMET);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_chestplate"), ROSE_GOLD_CHESTPLATE);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_leggings"), ROSE_GOLD_LEGGINGS);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_boots"), ROSE_GOLD_BOOTS);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_sword"), ROSE_GOLD_SWORD);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_pickaxe"), ROSE_GOLD_PICKAXE);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_axe"), ROSE_GOLD_AXE);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_shovel"), ROSE_GOLD_SHOVEL);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_hoe"), ROSE_GOLD_HOE);

        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_helmet"), GILDED_NETHERITE_HELMET);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_chestplate"), GILDED_NETHERITE_CHESTPLATE);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_leggings"), GILDED_NETHERITE_LEGGINGS);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_boots"), GILDED_NETHERITE_BOOTS);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_sword"), GILDED_NETHERITE_SWORD);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_pickaxe"), GILDED_NETHERITE_PICKAXE);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_axe"), GILDED_NETHERITE_AXE);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_shovel"), GILDED_NETHERITE_SHOVEL);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gilded_netherite_hoe"), GILDED_NETHERITE_HOE);
    }
}
