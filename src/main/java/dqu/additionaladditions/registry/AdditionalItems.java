package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.item.*;
import dqu.additionaladditions.misc.CreativeAdder;
import dqu.additionaladditions.misc.LootHandler;
import dqu.additionaladditions.misc.RoseGoldTransmuteRecipe;
import dqu.additionaladditions.misc.SuspiciousDyeRecipe;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class AdditionalItems {
    // saturation = hunger * saturationModifier
    public static final Item FRIED_EGG = new Builder("fried_egg")
            .config(() -> Config.getBool(ConfigValues.FOOD, "FriedEgg"))
            .properties(p -> p
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8666f).build())
            )
            .creativeAfter(Items.COOKED_RABBIT, CreativeAdder.FOOD_AND_DRINKS)
            .build();

    public static final Item BERRY_PIE = new Builder("berry_pie")
            .config(() -> Config.getBool(ConfigValues.FOOD, "BerryPie"))
            .properties(p -> p
                    .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.6f).build())
            )
            .creativeAfter(Items.PUMPKIN_PIE, CreativeAdder.FOOD_AND_DRINKS)
            .build();

    public static final Item HONEYED_APPLE = new Builder("honeyed_apple")
            .config(() -> Config.getBool(ConfigValues.FOOD, "HoneyedApple"))
            .properties(p -> p
                    .food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.6f).build())
            )
            .creativeAfter(Items.APPLE, CreativeAdder.FOOD_AND_DRINKS)
            .build();

    public static final Item CHICKEN_NUGGET = new Builder("chicken_nugget")
            .config(() -> Config.getBool(ConfigValues.CHICKEN_NUGGET))
            .properties(p -> p
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.9f).build())
            )
            .creativeAfter(Items.ROTTEN_FLESH, CreativeAdder.FOOD_AND_DRINKS)
            .lootTable(
                    List.of(EntityType.ZOMBIE.getDefaultLootTable(), EntityType.CREEPER.getDefaultLootTable()),
                    (registries, item) -> LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.025f, 0.01f))
                        .add(LootItem.lootTableItem(item))
            )
            .build();

    public static final WateringCanItem WATERING_CAN = new Builder("watering_can")
            .config(() -> Config.getBool(ConfigValues.WATERING_CAN))
            .properties(p -> p
                    .stacksTo(1)
                    .durability(101)
            )
            .creativeAfter(Items.BONE_MEAL, CreativeAdder.TOOLS_AND_UTILITIES)
            .build(WateringCanItem::new);

    public static final WrenchItem WRENCH_ITEM = new Builder("wrench")
            .config(() -> Config.getBool(ConfigValues.WRENCH))
            .properties(p -> p
                    .stacksTo(1)
                    .durability(256)
            )
            .creativeAfter(Items.BONE_MEAL, CreativeAdder.TOOLS_AND_UTILITIES)
            .creativeAfter(Items.TARGET, CreativeAdder.REDSTONE_BLOCKS)
            .build(WrenchItem::new);

    public static final CrossbowItem CROSSBOW_WITH_SPYGLASS = new Builder("crossbow_with_spyglass")
            .config(() -> Config.getBool(ConfigValues.CROSSBOWS))
            .properties(p -> p
                    .stacksTo(1)
                    .durability(350)
            )
            .creativeAfter(Items.CROSSBOW, CreativeAdder.COMBAT)
            .build(CrossbowItem::new);

`    public static final Item TRIDENT_SHARD = new Builder("trident_shard")
            .config(() -> Config.getBool(ConfigValues.TRIDENT_SHARD))
            .creativeAfter(Items.PRISMARINE_CRYSTALS, CreativeAdder.INGREDIENTS)
            .lootTable(
                    EntityType.ELDER_GUARDIAN.getDefaultLootTable(),
                    (registries, item) -> LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1f))
                        .add(LootItem.lootTableItem(item))
                        .when(LootItemRandomChanceCondition.randomChance(0.33f))
            )
            .build();`

    public static final Item GLOW_STICK_ITEM = new Builder("glow_stick")
            .config(() -> Config.getBool(ConfigValues.GLOW_STICK))
            .creativeAfter(Items.BONE_MEAL, CreativeAdder.TOOLS_AND_UTILITIES)
            .lootTable(
                    List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.STRONGHOLD_CORRIDOR),
                    (registries, item) -> LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0, 4))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
                        .add(LootItem.lootTableItem(item))
            )
            .build();

    public static final DepthMeterItem DEPTH_METER_ITEM = new Builder("depth_meter")
            .config(() -> Config.getBool(ConfigValues.DEPTH_METER, "enabled"))
            .creativeAfter(Items.CLOCK, CreativeAdder.TOOLS_AND_UTILITIES)
            .lootTable(
                    List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.STRONGHOLD_CORRIDOR),
                    (registries, item) -> LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                        .add(LootItem.lootTableItem(item))
            )
            .build(DepthMeterItem::new);

    public static final MysteriousBundleItem MYSTERIOUS_BUNDLE_ITEM = new Builder("mysterious_bundle")
            .config(() -> Config.getBool(ConfigValues.MYSTERIOUS_BUNDLE))
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
            )
            .creativeAfter(Items.ELYTRA, CreativeAdder.TOOLS_AND_UTILITIES)
            .build(MysteriousBundleItem::new);

    public static final PocketJukeboxItem POCKET_JUKEBOX_ITEM = new Builder("pocket_jukebox")
            .config(() -> Config.getBool(ConfigValues.POCKET_JUKEBOX))
            .properties(p -> p
                    .stacksTo(1)
            )
            .creativeAfter(Items.SPYGLASS, CreativeAdder.TOOLS_AND_UTILITIES)
            .build(PocketJukeboxItem::new);

    public static final Item ROSE_GOLD_INGOT = new Builder("rose_gold_ingot")
            .config(() -> Config.getBool(ConfigValues.ROSE_GOLD))
            .creativeAfter(Items.GOLD_INGOT, CreativeAdder.INGREDIENTS)
            .build();

    public static final Item MUSIC_DISC_0308 = new Builder("music_disc_0308")
            .config(() -> Config.getBool(ConfigValues.MUSIC_DISCS))
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "0308")))
            )
            .creativeAfter(Items.MUSIC_DISC_WARD, CreativeAdder.TOOLS_AND_UTILITIES)
            .lootTable(
                    List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.WOODLAND_MANSION),
                    (registries, item) -> LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.25f))
                            .add(LootItem.lootTableItem(item))
            )
            .build();

    public static final Item MUSIC_DISC_1007 = new Builder("music_disc_1007")
            .config(() -> Config.getBool(ConfigValues.MUSIC_DISCS))
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "1007")))
            )
            .creativeAfter(Items.MUSIC_DISC_WARD, CreativeAdder.TOOLS_AND_UTILITIES)
            .lootTable(
                    List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.WOODLAND_MANSION),
                    (registries, item) -> LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.25f))
                            .add(LootItem.lootTableItem(item))
            )
            .build();

    public static final Item MUSIC_DISC_1507 = new Builder("music_disc_1507")
            .config(() -> Config.getBool(ConfigValues.MUSIC_DISCS))
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "1507")))
            )
            .creativeAfter(Items.MUSIC_DISC_WARD, CreativeAdder.TOOLS_AND_UTILITIES)
            .lootTable(
                    List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.WOODLAND_MANSION),
                    (registries, item) -> LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.25f))
                            .add(LootItem.lootTableItem(item))
            )
            .build();

    public static final RoseGoldToolMaterial ROSE_GOLD_TOOL_MATERIAL = new RoseGoldToolMaterial();

    public static final Item ROSE_GOLD_HELMET = new Builder("rose_gold_helmet")
            .config(() -> Config.getBool(ConfigValues.ROSE_GOLD))
            .properties(p -> p
                    .durability(312)
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeAdder.COMBAT)
            .build(p -> new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.HELMET, p));

    public static final Item ROSE_GOLD_CHESTPLATE = new Builder("rose_gold_chestplate")
            .config(() -> Config.getBool(ConfigValues.ROSE_GOLD))
            .properties(p -> p
                    .durability(360)
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeAdder.COMBAT)
            .build(p -> new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.CHESTPLATE, p));

    public static final Item ROSE_GOLD_LEGGINGS = new Builder("rose_gold_leggings")
            .config(() -> Config.getBool(ConfigValues.ROSE_GOLD))
            .properties(p -> p
                    .durability(384)
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeAdder.COMBAT)
            .build(p -> new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.LEGGINGS, p));

    public static final Item ROSE_GOLD_BOOTS = new Builder("rose_gold_boots")
            .config(() -> Config.getBool(ConfigValues.ROSE_GOLD))
            .properties(p -> p
                    .durability(264)
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeAdder.COMBAT)
            .build(p -> new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.BOOTS, p));

    public static final Item ROSE_GOLD_SWORD = new Builder("rose_gold_sword")
            .config(() -> Config.getBool(ConfigValues.ROSE_GOLD))
            .properties(p -> p
                    .attributes(SwordItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 4, -2.4F))
            )
            .creativeAfter(Items.GOLDEN_SWORD, CreativeAdder.COMBAT)
            .build(p -> new SwordItem(ROSE_GOLD_TOOL_MATERIAL, p));

    public static final Item ROSE_GOLD_AXE = new Builder("rose_gold_axe")
            .config(() -> Config.getBool(ConfigValues.ROSE_GOLD))
            .properties(p -> p
                    .attributes(AxeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 6, -3.1F))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeAdder.TOOLS_AND_UTILITIES)
            .build(p -> new AxeItem(ROSE_GOLD_TOOL_MATERIAL, p));

    public static final Item ROSE_GOLD_PICKAXE = new Builder("rose_gold_pickaxe")
            .config(() -> Config.getBool(ConfigValues.ROSE_GOLD))
            .properties(p -> p
                    .attributes(PickaxeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 1, -2.8F))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeAdder.TOOLS_AND_UTILITIES)
            .build(p -> new PickaxeItem(ROSE_GOLD_TOOL_MATERIAL, p));

    public static final Item ROSE_GOLD_SHOVEL = new Builder("rose_gold_shovel")
            .config(() -> Config.getBool(ConfigValues.ROSE_GOLD))
            .properties(p -> p
                    .attributes(ShovelItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 1.5F, -3F))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeAdder.TOOLS_AND_UTILITIES)
            .build(p -> new ShovelItem(ROSE_GOLD_TOOL_MATERIAL, p));

    public static final Item ROSE_GOLD_HOE = new Builder("rose_gold_hoe")
            .config(() -> Config.getBool(ConfigValues.ROSE_GOLD))
            .properties(p -> p
                    .attributes(HoeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, -2, -1F))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeAdder.TOOLS_AND_UTILITIES)
            .build(p -> new HoeItem(ROSE_GOLD_TOOL_MATERIAL, p));

    public static final SuspiciousDyeItem WHITE_SUSPICIOUS_DYE = new Builder("white_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.WHITE, p));

    public static final SuspiciousDyeItem BROWN_SUSPICIOUS_DYE = new Builder("brown_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.BROWN, p));

    public static final SuspiciousDyeItem RED_SUSPICIOUS_DYE = new Builder("red_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.RED, p));

    public static final SuspiciousDyeItem ORANGE_SUSPICIOUS_DYE = new Builder("orange_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.ORANGE, p));

    public static final SuspiciousDyeItem YELLOW_SUSPICIOUS_DYE = new Builder("yellow_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.YELLOW, p));

    public static final SuspiciousDyeItem LIME_SUSPICIOUS_DYE = new Builder("lime_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.LIME, p));

    public static final SuspiciousDyeItem GREEN_SUSPICIOUS_DYE = new Builder("green_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.GREEN, p));

    public static final SuspiciousDyeItem CYAN_SUSPICIOUS_DYE = new Builder("cyan_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.CYAN, p));

    public static final SuspiciousDyeItem LIGHT_BLUE_SUSPICIOUS_DYE = new Builder("light_blue_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.LIGHT_BLUE, p));

    public static final SuspiciousDyeItem BLUE_SUSPICIOUS_DYE = new Builder("blue_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.BLUE, p));

    public static final SuspiciousDyeItem PURPLE_SUSPICIOUS_DYE = new Builder("purple_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.PURPLE, p));

    public static final SuspiciousDyeItem MAGENTA_SUSPICIOUS_DYE = new Builder("magenta_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.MAGENTA, p));

    public static final SuspiciousDyeItem PINK_SUSPICIOUS_DYE = new Builder("pink_suspicious_dye")
            .properties(p -> p.stacksTo(1))
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeAdder.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.PINK, p));

    public static final DataComponentType<DyeColor> GLINT_COLOR_COMPONENT = DataComponentType.<DyeColor>builder().persistent(DyeColor.CODEC).networkSynchronized(DyeColor.STREAM_CODEC).build();

    public static final TagKey<Item> SUSPICIOUS_DYES_TAG = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "suspicious_dyes"));

    private static class Builder {
        private final Item.Properties properties = new Item.Properties();
        private Supplier<Boolean> config = () -> true;
        private final Map<CreativeAdder, List<ItemLike>> creativeAfter = new HashMap<>();
        private final Map<CreativeAdder, List<ItemLike>> creativeBefore = new HashMap<>();
        private final Map<ResourceKey<LootTable>, BiFunction<HolderLookup.Provider, Item, LootPool.Builder>> lootTables = new HashMap<>();
        private String id;

        public Builder(String id) {
            this.id = id;
        }

        public Builder properties(Consumer<Item.Properties> consumer) {
            consumer.accept(this.properties);
            return this;
        }

        public Builder config(Supplier<Boolean> config) {
            this.config = config;
            return this;
        }

        public Builder creativeAfter(ItemLike item, CreativeAdder category) {
            this.creativeAfter.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
            return this;
        }

        public Builder creativeBefore(ItemLike item, CreativeAdder category) {
            this.creativeBefore.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
            return this;
        }

        public Builder lootTable(ResourceKey<LootTable> table, BiFunction<HolderLookup.Provider, Item, LootPool.Builder> lootPoolBuilder) {
            this.lootTables.put(table, lootPoolBuilder);
            return this;
        }

        public Builder lootTable(List<ResourceKey<LootTable>> tables, BiFunction<HolderLookup.Provider, Item, LootPool.Builder> lootPoolBuilder) {
            for (ResourceKey<LootTable> table : tables) {
                this.lootTables.put(table, lootPoolBuilder);
            }
            return this;
        }

        public Item build() {
            return build(Item::new);
        }

        public <T extends Item> T build(Function<Item.Properties, T> consumer) {
            ResourceLocation location = ResourceLocation.tryBuild(AdditionalAdditions.namespace, id);
            T item = consumer.apply(this.properties);

            Registry.register(BuiltInRegistries.ITEM, location, item);

            for (Map.Entry<CreativeAdder, List<ItemLike>> entry : this.creativeAfter.entrySet()) {
                for (ItemLike itemLike : entry.getValue()) {
                    entry.getKey().add(this.config, itemLike, item);
                }
            }
            for (Map.Entry<CreativeAdder, List<ItemLike>> entry : this.creativeBefore.entrySet()) {
                for (ItemLike itemLike : entry.getValue()) {
                    entry.getKey().addBefore(this.config, itemLike, item);
                }
            }

            for (Map.Entry<ResourceKey<LootTable>, BiFunction<HolderLookup.Provider, Item, LootPool.Builder>> entry : this.lootTables.entrySet()) {
                LootHandler.register(entry.getKey(), this.config, provider -> entry.getValue().apply(provider, item));
            }

            return item;
        }
    }

    public static void registerAll() {
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "glint_color"), GLINT_COLOR_COMPONENT);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "rose_gold_transmute"), RoseGoldTransmuteRecipe.ROSE_GOLD_TRANSMUTE_RECIPE_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "suspicious_dye"), SuspiciousDyeRecipe.SUSPICIOUS_DYE_RECIPE_SERIALIZER);

        LootHandler.register(BuiltInLootTables.SHIPWRECK_SUPPLY, () -> Config.getBool(ConfigValues.SHIPWRECK_SPYGLASS_LOOT), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.5f))
                .add(LootItem.lootTableItem(Items.SPYGLASS))
        );

        if (Config.getBool(ConfigValues.WRENCH)) {
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

        if (Config.getBool(ConfigValues.COMPOSTABLE_ROTTEN_FLESH)) {
            CompostingChanceRegistry.INSTANCE.add(Items.ROTTEN_FLESH, 0.33F);
        }
    }
}
