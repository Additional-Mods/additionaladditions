package dqu.additionaladditions;

import dqu.additionaladditions.block.CopperPatina;
import dqu.additionaladditions.block.GlowStickBlock;
import dqu.additionaladditions.block.RopeBlock;
import dqu.additionaladditions.enchantment.PrecisionEnchantment;
import dqu.additionaladditions.entity.GlowStickEntity;
import dqu.additionaladditions.item.*;
import dqu.additionaladditions.material.RoseGoldArmorMaterial;
import dqu.additionaladditions.material.RoseGoldToolMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.Material;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.LootConditionTypes;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class Registrar {
    public static final String namespace = "additionaladditions";
    private static final Identifier ELDER_GUARDIAN_LOOT_TABLE_ID = EntityType.ELDER_GUARDIAN.getLootTableId();
    private static final Identifier MINESHAFT_CHEST_LOOT_TABLE_ID = LootTables.ABANDONED_MINESHAFT_CHEST;
    private static final Identifier DUNGEON_CHEST_LOOT_TABLE_ID = LootTables.SIMPLE_DUNGEON_CHEST;
    private static final Identifier STRONGHOLD_CHEST_LOOT_TABLE_ID = LootTables.STRONGHOLD_CORRIDOR_CHEST;

    public static final Item FRIED_EGG = new Item(new FabricItemSettings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(5.2f).build())
    );
    public static final Item BERRY_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(4.8f).build())
    );
    public static final Item HONEYED_APPLE = new Item(new FabricItemSettings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(12.8f).build())
    );

    public static final WateringCan WATERING_CAN = new WateringCan(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(101));
    public static final Wrench WRENCH = new Wrench(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(256));
    public static final CopperPatina COPPER_PATINA = new CopperPatina(FabricBlockSettings.of(Material.CARPET).noCollision().sounds(BlockSoundGroup.TUFF));
    public static final CrossbowItem CROSSBOW_WITH_SPYGLASS = new CrossbowItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(350));
    public static final RopeBlock ROPE_BLOCK = new RopeBlock(FabricBlockSettings.of(Material.BAMBOO).noCollision().sounds(BlockSoundGroup.WOOL));
    public static final RedstoneLampBlock AMETHYST_LAMP = new RedstoneLampBlock(FabricBlockSettings.of(Material.REDSTONE_LAMP).sounds(BlockSoundGroup.GLASS));
    public static final Item TRIDENT_SHARD = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));

    public static final ArmorMaterial ROSE_GOLD_ARMOR_MATERIAL = new RoseGoldArmorMaterial();
    public static final Item ROSE_GOLD_HELMET = new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_CHESTPLATE = new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_LEGGINGS = new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_BOOTS = new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_SWORD = new SwordItem(RoseGoldToolMaterial.MATERIAL, 4, -2.4F, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_PICKAXE = new RoseGoldPickaxe(RoseGoldToolMaterial.MATERIAL, 1, -2.8F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item ROSE_GOLD_AXE = new RoseGoldAxe(RoseGoldToolMaterial.MATERIAL, 6, -3.1F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item ROSE_GOLD_HOE = new RoseGoldHoe(RoseGoldToolMaterial.MATERIAL, -2, -1F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item ROSE_GOLD_SHOVEL = new ShovelItem(RoseGoldToolMaterial.MATERIAL, 1.5F, -3F, new Item.Settings().group(ItemGroup.TOOLS));

    public static final EntityType<GlowStickEntity> GLOW_STICK_ENTITY_ENTITY_TYPE = FabricEntityTypeBuilder.<GlowStickEntity>create(SpawnGroup.MISC, GlowStickEntity::new)
            .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
            .trackRangeBlocks(4).trackedUpdateRate(10).build();
    public static final GlowStickItem GLOW_STICK_ITEM = new GlowStickItem(new FabricItemSettings().group(ItemGroup.MISC));
    public static final GlowStickBlock GLOW_STICK_BLOCK = new GlowStickBlock(FabricBlockSettings.of(Material.CARPET).noCollision().luminance(12).breakInstantly());

    public static final Enchantment ENCHANTMENT_PRECISION = new PrecisionEnchantment();

    public static void registerItems() {
        if(Config.get("WateringCan")) Registry.register(Registry.ITEM, new Identifier(namespace, "watering_can"), WATERING_CAN);
        if(Config.get("Wrench")) Registry.register(Registry.ITEM, new Identifier(namespace, "wrench"), WRENCH);
        if(Config.get("Crossbows")) Registry.register(Registry.ITEM, new Identifier(namespace, "crossbow_with_spyglass"), CROSSBOW_WITH_SPYGLASS);
        if(Config.get("TridentShard")) Registry.register(Registry.ITEM, new Identifier(namespace, "trident_shard"), TRIDENT_SHARD);
        if(Config.get("GlowStick")) Registry.register(Registry.ITEM, new Identifier(namespace, "glow_stick"), GLOW_STICK_ITEM);
        if(Config.get("FoodItems")) {
            Registry.register(Registry.ITEM, new Identifier(namespace, "berry_pie"), BERRY_PIE);
            Registry.register(Registry.ITEM, new Identifier(namespace, "fried_egg"), FRIED_EGG);
            Registry.register(Registry.ITEM, new Identifier(namespace, "honeyed_apple"), HONEYED_APPLE);
        }
        if(Config.get("RoseGold")) {
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
    }

    public static void registerBlocks() {
        if(Config.get("GlowStick")) Registry.register(Registry.BLOCK, new Identifier(namespace, "glow_stick"), GLOW_STICK_BLOCK);
        if (Config.get("CopperPatina")) {
            Registry.register(Registry.BLOCK, new Identifier(namespace, "copper_patina"), COPPER_PATINA);
            Registry.register(Registry.ITEM, new Identifier(namespace, "copper_patina"),
                    new BlockItem(COPPER_PATINA, new FabricItemSettings().group(ItemGroup.REDSTONE)));
        }
        if (Config.get("Ropes")) {
            Registry.register(Registry.BLOCK, new Identifier(namespace, "rope"), ROPE_BLOCK);
            Registry.register(Registry.ITEM, new Identifier(namespace, "rope"),
                    new BlockItem(ROPE_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
        }
        if (Config.get("AmethystLamp")) {
            Registry.register(Registry.BLOCK, new Identifier(namespace, "amethyst_lamp"), AMETHYST_LAMP);
            Registry.register(Registry.ITEM, new Identifier(namespace, "amethyst_lamp"),
                    new BlockItem(AMETHYST_LAMP, new FabricItemSettings().group(ItemGroup.REDSTONE)));
        }
    }

    public static void registerOther() {
        if(Config.get("EnchantmentPrecision")) Registry.register(Registry.ENCHANTMENT, new Identifier(namespace, "precision"), ENCHANTMENT_PRECISION);
        if(Config.get("GlowStick")) Registry.register(Registry.ENTITY_TYPE, new Identifier(namespace, "glow_stick"), GLOW_STICK_ENTITY_ENTITY_TYPE);
        if(Config.get("Wrench")) {
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
        LootTableLoadingCallback.EVENT.register(((resourceManager, lootManager, id, table, setter) -> {
            if (ELDER_GUARDIAN_LOOT_TABLE_ID.equals(id) && Config.get("TridentShard")) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1f))
                        .with(ItemEntry.builder(TRIDENT_SHARD))
                        .conditionally(RandomChanceLootCondition.builder(0.33f));
                table.pool(poolBuilder);
            }
            if (DUNGEON_CHEST_LOOT_TABLE_ID.equals(id) || MINESHAFT_CHEST_LOOT_TABLE_ID.equals(id) || STRONGHOLD_CHEST_LOOT_TABLE_ID.equals(id)) {
                if (Config.get("GlowStick")) {
                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(UniformLootNumberProvider.create(0, 16))
                            .with(ItemEntry.builder(GLOW_STICK_ITEM));
                    table.pool(poolBuilder);
                }
            }
        }));
    }

}
