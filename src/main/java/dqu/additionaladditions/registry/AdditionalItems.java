package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.item.*;
import dqu.additionaladditions.misc.CreativeAdder;
import dqu.additionaladditions.misc.LootHandler;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;

public class AdditionalItems {
    // saturation = hunger * saturationModifier
    public static final Item FRIED_EGG = new Item(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8666f).build())
    );
    public static final Item BERRY_PIE = new Item(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.6f).build())
    );
    public static final Item HONEYED_APPLE = new Item(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.6f).build())
    );
    public static final Item CHICKEN_NUGGET = new Item(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.9f).build())
    );

    public static final WateringCanItem WATERING_CAN = new WateringCanItem(new Item.Properties().stacksTo(1).durability(101));
    public static final WrenchItem WRENCH_ITEM = new WrenchItem(new Item.Properties().stacksTo(1).durability(256));
    public static final CrossbowItem CROSSBOW_WITH_SPYGLASS = new CrossbowItem(new Item.Properties().stacksTo(1).durability(350));
    public static final Item TRIDENT_SHARD = new Item(new Item.Properties());
    public static final GlowStickItem GLOW_STICK_ITEM = new GlowStickItem(new Item.Properties());
    public static final DepthMeterItem DEPTH_METER_ITEM = new DepthMeterItem(new Item.Properties());
    public static final MysteriousBundleItem MYSTERIOUS_BUNDLE_ITEM = new MysteriousBundleItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    public static final Item GOLD_RING = new Item(new Item.Properties().stacksTo(1));
    public static final PocketJukeboxItem POCKET_JUKEBOX_ITEM = new PocketJukeboxItem(new Item.Properties().stacksTo(1));
    public static final Item ROSE_GOLD_ALLOY = new Item(new Item.Properties());

    public static final Item MUSIC_DISC_0308 = new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "0308"))));
    public static final Item MUSIC_DISC_1007 = new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "1007"))));
    public static final Item MUSIC_DISC_1507 = new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "1507"))));

    public static final RoseGoldToolMaterial ROSE_GOLD_TOOL_MATERIAL = new RoseGoldToolMaterial();
    public static final Item ROSE_GOLD_HELMET = new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.HELMET, new Item.Properties().durability(312));
    public static final Item ROSE_GOLD_CHESTPLATE = new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(360));
    public static final Item ROSE_GOLD_LEGGINGS = new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(384));
    public static final Item ROSE_GOLD_BOOTS = new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.BOOTS, new Item.Properties().durability(264));
    public static final Item ROSE_GOLD_SWORD = new SwordItem(ROSE_GOLD_TOOL_MATERIAL, new Item.Properties().attributes(SwordItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 4, -2.4F)));
    public static final Item ROSE_GOLD_AXE = new AxeItem(ROSE_GOLD_TOOL_MATERIAL, new Item.Properties().attributes(AxeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 6, -3.1F)));
    public static final Item ROSE_GOLD_PICKAXE = new PickaxeItem(ROSE_GOLD_TOOL_MATERIAL, new Item.Properties().attributes(PickaxeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 1, -2.8F)));
    public static final Item ROSE_GOLD_SHOVEL = new ShovelItem(ROSE_GOLD_TOOL_MATERIAL, new Item.Properties().attributes(ShovelItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 1.5F, -3F)));
    public static final Item ROSE_GOLD_HOE = new HoeItem(ROSE_GOLD_TOOL_MATERIAL, new Item.Properties().attributes(HoeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL,  -2, -1F)));

    private static void registerItems() {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "berry_pie"), BERRY_PIE);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "fried_egg"), FRIED_EGG);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "honeyed_apple"), HONEYED_APPLE);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "chicken_nugget"), CHICKEN_NUGGET);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "watering_can"), WATERING_CAN);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "wrench"), WRENCH_ITEM);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "crossbow_with_spyglass"), CROSSBOW_WITH_SPYGLASS);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "trident_shard"), TRIDENT_SHARD);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "glow_stick"), GLOW_STICK_ITEM);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "depth_meter"), DEPTH_METER_ITEM);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "mysterious_bundle"), MYSTERIOUS_BUNDLE_ITEM);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "pocket_jukebox"), POCKET_JUKEBOX_ITEM);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "gold_ring"), GOLD_RING);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_alloy"), ROSE_GOLD_ALLOY);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "music_disc_0308"), MUSIC_DISC_0308);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "music_disc_1007"), MUSIC_DISC_1007);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "music_disc_1507"), MUSIC_DISC_1507);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_helmet"), ROSE_GOLD_HELMET);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_chestplate"), ROSE_GOLD_CHESTPLATE);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_leggings"), ROSE_GOLD_LEGGINGS);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_boots"), ROSE_GOLD_BOOTS);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_sword"), ROSE_GOLD_SWORD);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_axe"), ROSE_GOLD_AXE);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_pickaxe"), ROSE_GOLD_PICKAXE);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_shovel"), ROSE_GOLD_SHOVEL);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_hoe"), ROSE_GOLD_HOE);
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
                .add(LootItem.lootTableItem(MUSIC_DISC_0308))
                .add(LootItem.lootTableItem(MUSIC_DISC_1007))
                .add(LootItem.lootTableItem(MUSIC_DISC_1507))
        );
        LootHandler.register(BuiltInLootTables.SHIPWRECK_SUPPLY, () -> Config.getBool(ConfigValues.SHIPWRECK_SPYGLASS_LOOT), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.5f))
                .add(LootItem.lootTableItem(Items.SPYGLASS))
        );
        LootHandler.register(List.of(EntityType.ZOMBIE.getDefaultLootTable(), EntityType.CREEPER.getDefaultLootTable()), () -> Config.getBool(ConfigValues.CHICKEN_NUGGET), (registries) -> LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.025f, 0.01f))
                .add(LootItem.lootTableItem(CHICKEN_NUGGET))
        );
        LootHandler.register(BuiltInLootTables.PIGLIN_BARTERING, () -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), LootPool.lootPool()
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

                    BlockState dstate = pointer.state();
                    BlockPos pos = pointer.pos().relative(dstate.getValue(BlockStateProperties.FACING));
                    BlockState state = pointer.level().getBlockState(pos);

                    wrench.dispenserUse(pointer.level(), pos, state, stack);
                    return stack;
                }
            });
        }
        if (Config.getBool(ConfigValues.COMPOSTABLE_ROTTEN_FLESH))
            CompostingChanceRegistry.INSTANCE.add(Items.ROTTEN_FLESH, 0.33F);
    }

    private static void putToItemGroups() {
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.WATERING_CAN), Items.BONE_MEAL, WATERING_CAN);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.WRENCH), Items.BONE_MEAL, WRENCH_ITEM);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.GLOW_STICK), Items.BONE_MEAL, GLOW_STICK_ITEM);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.DEPTH_METER, "enabled"), Items.CLOCK, DEPTH_METER_ITEM);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.MYSTERIOUS_BUNDLE), Items.ELYTRA, MYSTERIOUS_BUNDLE_ITEM);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.POCKET_JUKEBOX), Items.SPYGLASS, POCKET_JUKEBOX_ITEM);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.MUSIC_DISCS), Items.MUSIC_DISC_WARD, MUSIC_DISC_0308);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.MUSIC_DISCS), Items.MUSIC_DISC_WARD, MUSIC_DISC_1007);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.MUSIC_DISCS), Items.MUSIC_DISC_WARD, MUSIC_DISC_1507);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_HOE, ROSE_GOLD_PICKAXE);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_HOE, ROSE_GOLD_AXE);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_HOE, ROSE_GOLD_SHOVEL);
        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_HOE, ROSE_GOLD_HOE);
        CreativeAdder.INGREDIENTS.addBefore(() -> Config.getBool(ConfigValues.GILDED_NETHERITE, "enabled"), Items.NETHERITE_SCRAP, GOLD_RING);
        CreativeAdder.INGREDIENTS.addBefore(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.NETHERITE_SCRAP, ROSE_GOLD_ALLOY);
        CreativeAdder.INGREDIENTS.add(() -> Config.getBool(ConfigValues.TRIDENT_SHARD), Items.PRISMARINE_CRYSTALS, TRIDENT_SHARD);
        CreativeAdder.REDSTONE_BLOCKS.add(() -> Config.getBool(ConfigValues.WRENCH), Items.TARGET, WRENCH_ITEM);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.CROSSBOWS), Items.CROSSBOW, CROSSBOW_WITH_SPYGLASS);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_BOOTS, ROSE_GOLD_HELMET);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_BOOTS, ROSE_GOLD_CHESTPLATE);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_BOOTS, ROSE_GOLD_LEGGINGS);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_BOOTS, ROSE_GOLD_BOOTS);
        CreativeAdder.COMBAT.add(() -> Config.getBool(ConfigValues.ROSE_GOLD), Items.GOLDEN_SWORD, ROSE_GOLD_SWORD);
        CreativeAdder.FOOD_AND_DRINKS.add(() -> Config.getBool(ConfigValues.FOOD, "FriedEgg"), Items.COOKED_RABBIT, FRIED_EGG);
        CreativeAdder.FOOD_AND_DRINKS.add(() -> Config.getBool(ConfigValues.FOOD, "BerryPie"), Items.PUMPKIN_PIE, BERRY_PIE);
        CreativeAdder.FOOD_AND_DRINKS.add(() -> Config.getBool(ConfigValues.FOOD, "HoneyedApple"), Items.APPLE, HONEYED_APPLE);
        CreativeAdder.FOOD_AND_DRINKS.add(() -> Config.getBool(ConfigValues.CHICKEN_NUGGET), Items.ROTTEN_FLESH, CHICKEN_NUGGET);
    }

    public static void registerAll() {
        registerItems();
        registerLootTables();
        registerOther();
        putToItemGroups();
    }
}
