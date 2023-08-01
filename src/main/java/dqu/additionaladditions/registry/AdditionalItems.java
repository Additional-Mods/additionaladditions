package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.item.*;
import dqu.additionaladditions.misc.LootHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;

public class AdditionalItems {
    // saturation = hunger * saturationModifier
    public static final Item FRIED_EGG = new Item(new FabricItemSettings()
            .food(new FoodProperties.Builder().nutrition(6).saturationMod(0.8666f).build())
    );
    public static final Item BERRY_PIE = new Item(new FabricItemSettings()
            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.6f).build())
    );
    public static final Item HONEYED_APPLE = new Item(new FabricItemSettings()
            .food(new FoodProperties.Builder().nutrition(8).saturationMod(1.6f).build())
    );
    public static final Item CHICKEN_NUGGET = new Item(new FabricItemSettings()
            .food(new FoodProperties.Builder().nutrition(6).saturationMod(0.9f).meat().build())
    );

    public static final WateringCanItem WATERING_CAN = new WateringCanItem(new FabricItemSettings().stacksTo(1).durability(101));
    public static final WrenchItem WRENCH_ITEM = new WrenchItem(new FabricItemSettings().stacksTo(1).durability(256));
    public static final CrossbowItem CROSSBOW_WITH_SPYGLASS = new CrossbowItem(new FabricItemSettings().stacksTo(1).durability(350));
    public static final Item TRIDENT_SHARD = new Item(new FabricItemSettings());
    public static final GlowStickItem GLOW_STICK_ITEM = new GlowStickItem(new FabricItemSettings());
    public static final DepthMeterItem DEPTH_METER_ITEM = new DepthMeterItem(new FabricItemSettings());
    public static final MysteriousBundleItem MYSTERIOUS_BUNDLE_ITEM = new MysteriousBundleItem(new FabricItemSettings().stacksTo(1).rarity(Rarity.RARE));
    public static final Item GOLD_RING = new Item(new FabricItemSettings().stacksTo(1));
    public static final PocketJukeboxItem POCKET_JUKEBOX_ITEM = new PocketJukeboxItem(new FabricItemSettings().stacksTo(1));
    public static final Item ROSE_GOLD_ALLOY = new Item(new FabricItemSettings());

    private static void registerItems() {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "watering_can"), WATERING_CAN);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "wrench"), WRENCH_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "crossbow_with_spyglass"), CROSSBOW_WITH_SPYGLASS);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "trident_shard"), TRIDENT_SHARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "glow_stick"), GLOW_STICK_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "depth_meter"), DEPTH_METER_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "mysterious_bundle"), MYSTERIOUS_BUNDLE_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "pocket_jukebox"), POCKET_JUKEBOX_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "gold_ring"), GOLD_RING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rose_gold_alloy"), ROSE_GOLD_ALLOY);
    }

    private static void registerFoods() {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "berry_pie"), BERRY_PIE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "fried_egg"), FRIED_EGG);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "honeyed_apple"), HONEYED_APPLE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "chicken_nugget"), CHICKEN_NUGGET);
    }

    private static void registerLootTables() {
        LootHandler.register(EntityType.ELDER_GUARDIAN.getDefaultLootTable(), () -> Config.getBool(ConfigValues.TRIDENT_SHARD), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(TRIDENT_SHARD))
                .when(LootItemRandomChanceCondition.randomChance(0.33f))
        );
        LootHandler.register(List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.STRONGHOLD_CORRIDOR), () -> Config.getBool(ConfigValues.GLOW_STICK), LootPool.lootPool()
                .setRolls(UniformGenerator.between(0, 4))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
                .add(LootItem.lootTableItem(GLOW_STICK_ITEM))
        );
        LootHandler.register(List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.STRONGHOLD_CORRIDOR), () -> Config.getBool(ConfigValues.DEPTH_METER), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.1f))
                .add(LootItem.lootTableItem(DEPTH_METER_ITEM))
        );
        LootHandler.register(List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.WOODLAND_MANSION), () -> Config.getBool(ConfigValues.MUSIC_DISCS), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.25f))
                .add(LootItem.lootTableItem(AdditionalMusicDiscs.MUSIC_DISC_0308))
                .add(LootItem.lootTableItem(AdditionalMusicDiscs.MUSIC_DISC_1007))
                .add(LootItem.lootTableItem(AdditionalMusicDiscs.MUSIC_DISC_1507))
        );
        LootHandler.register(BuiltInLootTables.SHIPWRECK_SUPPLY, () -> Config.getBool(ConfigValues.SHIPWRECK_SPYGLASS_LOOT), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.5f))
                .add(LootItem.lootTableItem(Items.SPYGLASS))
        );
        LootHandler.register(List.of(EntityType.ZOMBIE.getDefaultLootTable(), EntityType.CREEPER.getDefaultLootTable()), () -> Config.getBool(ConfigValues.CHICKEN_NUGGET), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025f, 0.01f))
                .add(LootItem.lootTableItem(CHICKEN_NUGGET))
        );
        LootHandler.register(BuiltInLootTables.PIGLIN_BARTERING, () -> Config.getBool(ConfigValues.GILDED_NETHERITE), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.015f))
                .add(LootItem.lootTableItem(GOLD_RING))
        );
    }

    private static void registerOther() {
        if(Config.getBool(ConfigValues.WRENCH)) {
            DispenserBlock.registerBehavior(WRENCH_ITEM, new DefaultDispenseItemBehavior() {
                public ItemStack execute(BlockSource pointer, ItemStack stack) {
                    WrenchItem wrench = (WrenchItem) stack.getItem();

                    BlockState dstate = pointer.getBlockState();
                    BlockPos pos = pointer.getPos().relative(dstate.getValue(BlockStateProperties.FACING));
                    BlockState state = pointer.getLevel().getBlockState(pos);

                    wrench.dispenserUse(pointer.getLevel(), pos, state, stack);
                    return stack;
                }
            });
        }
        if (Config.getBool(ConfigValues.COMPOSTABLE_ROTTEN_FLESH))
            CompostingChanceRegistry.INSTANCE.add(Items.ROTTEN_FLESH, 0.33F);
    }

    private static void putToItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> {
            content.addAfter(Items.BONE_MEAL, WATERING_CAN, WRENCH_ITEM, GLOW_STICK_ITEM);
            content.addAfter(Items.CLOCK, DEPTH_METER_ITEM);
            content.addAfter(Items.ELYTRA, MYSTERIOUS_BUNDLE_ITEM);
            content.addAfter(Items.SPYGLASS, POCKET_JUKEBOX_ITEM);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(content -> {
            content.addBefore(Items.NETHERITE_SCRAP, GOLD_RING, ROSE_GOLD_ALLOY);
            content.addAfter(Items.PRISMARINE_CRYSTALS, TRIDENT_SHARD);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(content -> {
            content.addAfter(Blocks.TARGET, WRENCH_ITEM);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(content -> {
            content.addAfter(Items.CROSSBOW, CROSSBOW_WITH_SPYGLASS);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(content -> {
            content.addAfter(Items.COOKED_RABBIT, FRIED_EGG);
            content.addAfter(Items.PUMPKIN_PIE, BERRY_PIE);
            content.addAfter(Items.APPLE, HONEYED_APPLE);
            content.addAfter(Items.ROTTEN_FLESH, CHICKEN_NUGGET);
        });
    }

    public static void registerAll() {
        registerItems();
        registerFoods();
        registerLootTables();
        registerOther();
        putToItemGroups();
    }
}
