package dqu.additionaladditions;

import dqu.additionaladditions.block.CopperPatina;
import dqu.additionaladditions.block.RopeBlock;
import dqu.additionaladditions.enchantment.PrecisionEnchantment;
import dqu.additionaladditions.item.*;
import dqu.additionaladditions.material.RoseGoldArmorMaterial;
import dqu.additionaladditions.material.RoseGoldToolMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.Material;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class Registrar {
    public static final String namespace = "additionaladditions";

    public static final Item BERRY_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(5f).build())
    );
    public static final WateringCan WATERING_CAN = new WateringCan(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).maxDamage(101));
    public static final Wrench WRENCH = new Wrench(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(256));
    public static final CopperPatina COPPER_PATINA = new CopperPatina(FabricBlockSettings.of(Material.CARPET));
    public static final CrossbowItem CROSSBOW_WITH_SPYGLASS = new CrossbowItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(350));
    public static final RopeBlock ROPE_BLOCK = new RopeBlock(FabricBlockSettings.of(Material.BAMBOO).noCollision().sounds(BlockSoundGroup.WOOL));
    public static final RedstoneLampBlock AMETHYST_LAMP = new RedstoneLampBlock(FabricBlockSettings.of(Material.REDSTONE_LAMP).sounds(BlockSoundGroup.GLASS));

    public static final ArmorMaterial ROSE_GOLD_ARMOR_MATERIAL = new RoseGoldArmorMaterial();
    public static final Item ROSE_GOLD_HELMET = new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_CHESTPLATE = new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_LEGGINGS = new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_BOOTS = new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_SWORD = new SwordItem(RoseGoldToolMaterial.MATERIAL, 3, -2.4F, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_PICKAXE = new RoseGoldPickaxe(RoseGoldToolMaterial.MATERIAL, 1, -2.4F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item ROSE_GOLD_AXE = new RoseGoldAxe(RoseGoldToolMaterial.MATERIAL, 6, -3.1F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item ROSE_GOLD_HOE = new RoseGoldHoe(RoseGoldToolMaterial.MATERIAL, 0, -1.8F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item ROSE_GOLD_SHOVEL = new ShovelItem(RoseGoldToolMaterial.MATERIAL, 1, -2.4F, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Enchantment ENCHANTMENT_PRECISION = new PrecisionEnchantment();

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(namespace, "berry_pie"), BERRY_PIE);
        Registry.register(Registry.ITEM, new Identifier(namespace, "watering_can"), WATERING_CAN);
        Registry.register(Registry.ITEM, new Identifier(namespace, "wrench"), WRENCH);
        Registry.register(Registry.ITEM, new Identifier(namespace, "crossbow_with_spyglass"), CROSSBOW_WITH_SPYGLASS);
        Registry.register(Registry.ITEM, new Identifier(namespace, "rose_gold_helmet"), ROSE_GOLD_HELMET);
        Registry.register(Registry.ITEM, new Identifier(namespace, "rose_gold_chestplate"), ROSE_GOLD_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier(namespace, "rose_gold_leggings"), ROSE_GOLD_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier(namespace, "rose_gold_boots"), ROSE_GOLD_BOOTS);
        Registry.register(Registry.ITEM, new Identifier(namespace, "rose_gold_sword"), ROSE_GOLD_SWORD);
        Registry.register(Registry.ITEM, new Identifier(namespace, "rose_gold_pickaxe"), ROSE_GOLD_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier(namespace, "rose_gold_axe"), ROSE_GOLD_AXE);
        Registry.register(Registry.ITEM, new Identifier(namespace, "rose_gold_shovel"), ROSE_GOLD_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier(namespace, "rose_gold_hoe"), ROSE_GOLD_HOE);
    }

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(namespace, "copper_patina"), COPPER_PATINA);
        Registry.register(Registry.ITEM, new Identifier(namespace, "copper_patina"),
                new BlockItem(COPPER_PATINA, new FabricItemSettings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.BLOCK, new Identifier(namespace, "rope"), ROPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(namespace, "rope"),
                new BlockItem(ROPE_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
        Registry.register(Registry.BLOCK, new Identifier(namespace, "amethyst_lamp"), AMETHYST_LAMP);
        Registry.register(Registry.ITEM, new Identifier(namespace, "amethyst_lamp"),
                new BlockItem(AMETHYST_LAMP, new FabricItemSettings().group(ItemGroup.REDSTONE)));
    }

    public static void registerOther() {
        Registry.register(Registry.ENCHANTMENT, new Identifier(namespace, "precision"), ENCHANTMENT_PRECISION);
        DispenserBlock.registerBehavior(WRENCH, new ItemDispenserBehavior() {
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                Wrench wrench = (Wrench) stack.getItem();

                BlockState dstate = pointer.getBlockState();
                BlockPos pos = pointer.getBlockPos().offset(dstate.get(Properties.FACING));
                BlockState state = pointer.getWorld().getBlockState(pos);

                wrench.dispenserUse(pointer.getWorld(), pos, state, stack);
                return stack;
            }
        });
    }

}
