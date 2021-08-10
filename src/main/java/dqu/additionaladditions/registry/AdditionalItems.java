package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.Config;
import dqu.additionaladditions.item.GlowStickItem;
import dqu.additionaladditions.item.MysteriousBundleItem;
import dqu.additionaladditions.item.WateringCanItem;
import dqu.additionaladditions.item.WrenchItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class AdditionalItems {
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

    public static final WateringCanItem WATERING_CAN = new WateringCanItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(101));
    public static final WrenchItem WRENCH_ITEM = new WrenchItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(256));
    public static final CrossbowItem CROSSBOW_WITH_SPYGLASS = new CrossbowItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(350));
    public static final Item TRIDENT_SHARD = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final GlowStickItem GLOW_STICK_ITEM = new GlowStickItem(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item DEPTH_METER_ITEM = new Item(new FabricItemSettings().group(ItemGroup.TOOLS));
    public static final MysteriousBundleItem MYSTERIOUS_BUNDLE_ITEM = new MysteriousBundleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));

    private static void registerItems() {
        if(Config.get("WateringCan")) Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "watering_can"), WATERING_CAN);
        if(Config.get("Wrench")) Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "wrench"), WRENCH_ITEM);
        if(Config.get("Crossbows")) Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "crossbow_with_spyglass"), CROSSBOW_WITH_SPYGLASS);
        if(Config.get("TridentShard")) Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "trident_shard"), TRIDENT_SHARD);
        if(Config.get("GlowStick")) Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "glow_stick"), GLOW_STICK_ITEM);
        if(Config.get("DepthMeter")) Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "depth_meter"), DEPTH_METER_ITEM);
        if(Config.get("MysteriousBundle")) Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "mysterious_bundle"), MYSTERIOUS_BUNDLE_ITEM);
    }

    private static void registerFoods() {
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "berry_pie"), BERRY_PIE);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "fried_egg"), FRIED_EGG);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "honeyed_apple"), HONEYED_APPLE);
    }

    private static void registerLootTables() {
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
                if (Config.get("DepthMeter")) {
                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.1f))
                            .with(ItemEntry.builder(DEPTH_METER_ITEM));
                    table.pool(poolBuilder);
                }
            }
        }));
    }

    private static void registerOther() {
        if(Config.get("Wrench")) {
            DispenserBlock.registerBehavior(WRENCH_ITEM, new ItemDispenserBehavior() {
                public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                    WrenchItem wrench = (WrenchItem) stack.getItem();

                    BlockState dstate = pointer.getBlockState();
                    BlockPos pos = pointer.getBlockPos().offset(dstate.get(Properties.FACING));
                    BlockState state = pointer.getWorld().getBlockState(pos);

                    wrench.dispenserUse(pointer.getWorld(), pos, state, stack);
                    return stack;
                }
            });
        }
        if (Config.get("CompostableRottenFlesh"))
            CompostingChanceRegistry.INSTANCE.add(Items.ROTTEN_FLESH, 0.33F);
    }

    public static void registerAll() {
        registerItems();
        if (Config.get("FoodItems")) registerFoods();
        registerLootTables();
        registerOther();
    }
}
