package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.item.*;
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
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class AdditionalItems {
    private static final Identifier ELDER_GUARDIAN_LOOT_TABLE_ID = EntityType.ELDER_GUARDIAN.getLootTableId();
    private static final Identifier ZOMBIE_LOOT_TABLE_ID = EntityType.ZOMBIE.getLootTableId();
    private static final Identifier CREEPER_LOOT_TABLE_ID = EntityType.CREEPER.getLootTableId();
    private static final Identifier PIGLIN_BARTERING_LOOT_TABLE_ID = LootTables.PIGLIN_BARTERING_GAMEPLAY;
    private static final Identifier MINESHAFT_CHEST_LOOT_TABLE_ID = LootTables.ABANDONED_MINESHAFT_CHEST;
    private static final Identifier DUNGEON_CHEST_LOOT_TABLE_ID = LootTables.SIMPLE_DUNGEON_CHEST;
    private static final Identifier STRONGHOLD_CHEST_LOOT_TABLE_ID = LootTables.STRONGHOLD_CORRIDOR_CHEST;
    private static final Identifier MANSION_CHEST_LOOT_TABLE_ID = LootTables.WOODLAND_MANSION_CHEST;
    private static final Identifier SHIPWRECK_SUPPLY_CHEST_LOOT_TABLE_ID = LootTables.SHIPWRECK_SUPPLY_CHEST;

    // saturation = hunger * saturationModifier
    public static final Item FRIED_EGG = new Item(new FabricItemSettings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8666f).build())
    );
    public static final Item BERRY_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.6f).build())
    );
    public static final Item HONEYED_APPLE = new Item(new FabricItemSettings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(1.6f).build())
    );
    public static final Item CHICKEN_NUGGET = new Item(new FabricItemSettings().group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.9f).meat().build())
    );

    public static final WateringCanItem WATERING_CAN = new WateringCanItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(101));
    public static final WrenchItem WRENCH_ITEM = new WrenchItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(256));
    public static final CrossbowItem CROSSBOW_WITH_SPYGLASS = new CrossbowItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(350));
    public static final Item TRIDENT_SHARD = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final GlowStickItem GLOW_STICK_ITEM = new GlowStickItem(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item DEPTH_METER_ITEM = new Item(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));
    public static final MysteriousBundleItem MYSTERIOUS_BUNDLE_ITEM = new MysteriousBundleItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final Item GOLD_RING = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS).maxCount(1));
    public static final PocketJukeboxItem POCKET_JUKEBOX_ITEM = new PocketJukeboxItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));

    private static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "watering_can"), WATERING_CAN);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "wrench"), WRENCH_ITEM);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "crossbow_with_spyglass"), CROSSBOW_WITH_SPYGLASS);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "trident_shard"), TRIDENT_SHARD);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "glow_stick"), GLOW_STICK_ITEM);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "depth_meter"), DEPTH_METER_ITEM);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "mysterious_bundle"), MYSTERIOUS_BUNDLE_ITEM);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "pocket_jukebox"), POCKET_JUKEBOX_ITEM);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "gold_ring"), GOLD_RING);
    }

    private static void registerFoods() {
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "berry_pie"), BERRY_PIE);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "fried_egg"), FRIED_EGG);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "honeyed_apple"), HONEYED_APPLE);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "chicken_nugget"), CHICKEN_NUGGET);
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
                            .rolls(UniformLootNumberProvider.create(0, 4))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4)))
                            .with(ItemEntry.builder(GLOW_STICK_ITEM));
                    table.pool(poolBuilder);
                }
                if (Config.get("Ropes")) {
                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(UniformLootNumberProvider.create(1, 4))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 8)))
                            .with(ItemEntry.builder(AdditionalBlocks.ROPE_BLOCK.asItem()));
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
            if (DUNGEON_CHEST_LOOT_TABLE_ID.equals(id) || MANSION_CHEST_LOOT_TABLE_ID.equals(id)) {
                if (Config.get("MusicDiscs")) {
                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.25f))
                            .with(ItemEntry.builder(AdditionalMusicDiscs.MUSIC_DISC_0308))
                            .with(ItemEntry.builder(AdditionalMusicDiscs.MUSIC_DISC_1007))
                            .with(ItemEntry.builder(AdditionalMusicDiscs.MUSIC_DISC_1507));
                    table.pool(poolBuilder);
                }
            }
            if (SHIPWRECK_SUPPLY_CHEST_LOOT_TABLE_ID.equals(id) && Config.get("ShipwreckSpyglassLoot")) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.5f))
                        .with(ItemEntry.builder(Items.SPYGLASS));
                table.pool(poolBuilder);
            }
            if (ZOMBIE_LOOT_TABLE_ID.equals(id) || CREEPER_LOOT_TABLE_ID.equals(id)) {
                if (Config.get("ChickenNugget")) {
                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.025f))
                            .with(ItemEntry.builder(CHICKEN_NUGGET));
                    table.pool(poolBuilder);
                }
            }
            if (PIGLIN_BARTERING_LOOT_TABLE_ID.equals(id) && Config.get("GoldRing")) {
                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.015f))
                            .with(ItemEntry.builder(GOLD_RING));
                    table.pool(poolBuilder);
            }
        }));
    }

    private static void registerOther() {
        if(Config.get("Wrench")) {
            DispenserBlock.registerBehavior(WRENCH_ITEM, new ItemDispenserBehavior() {
                public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                    WrenchItem wrench = (WrenchItem) stack.getItem();

                    BlockState dstate = pointer.getBlockState();
                    BlockPos pos = pointer.getPos().offset(dstate.get(Properties.FACING));
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
        registerFoods();
        registerLootTables();
        registerOther();
    }
}
