package one.dqu.additionaladditions.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.glint.GlintColor;
import one.dqu.additionaladditions.item.*;
import one.dqu.additionaladditions.util.CreativeAdder;
import one.dqu.additionaladditions.util.LootHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
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
import one.dqu.additionaladditions.util.Registrar;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class AdditionalItems {
    // saturation = hunger * saturationModifier
    public static final Supplier<Item> FRIED_EGG = new Builder("fried_egg")
            .config(() -> Config.FRIED_EGG.get().enabled())
            .properties(p -> p
                    .food(new FoodProperties.Builder()
                            .nutrition(Config.FRIED_EGG_FOOD.get().nutrition())
                            .saturationModifier(Config.FRIED_EGG_FOOD.get().saturation() / Config.FRIED_EGG_FOOD.get().nutrition())
                            .build()
                    )
            )
            .creativeAfter(Items.COOKED_RABBIT, CreativeModeTabs.FOOD_AND_DRINKS)
            .build();

    public static final Supplier<Item> BERRY_PIE = new Builder("berry_pie")
            .config(() -> Config.BERRY_PIE.get().enabled())
            .properties(p -> p
                    .food(new FoodProperties.Builder().
                            nutrition(Config.BERRY_PIE_FOOD.get().nutrition()).
                            saturationModifier(Config.BERRY_PIE_FOOD.get().saturation() / Config.BERRY_PIE_FOOD.get().nutrition())
                            .build()
                    )
            )
            .creativeAfter(Items.PUMPKIN_PIE, CreativeModeTabs.FOOD_AND_DRINKS)
            .build();

    public static final Supplier<Item> HONEYED_APPLE = new Builder("honeyed_apple")
            .config(() -> Config.HONEYED_APPLE.get().enabled())
            .properties(p -> p
                    .food(new FoodProperties.Builder()
                            .nutrition(Config.HONEYED_APPLE_FOOD.get().nutrition())
                            .saturationModifier(Config.HONEYED_APPLE_FOOD.get().saturation() / Config.HONEYED_APPLE_FOOD.get().nutrition())
                            .build()
                    )
            )
            .creativeAfter(Items.APPLE, CreativeModeTabs.FOOD_AND_DRINKS)
            .build();

    public static final Supplier<Item> CHICKEN_NUGGET = new Builder("chicken_nugget")
            .config(() -> Config.CHICKEN_NUGGET.get().enabled())
            .properties(p -> p
                    .food(new FoodProperties.Builder()
                            .nutrition(Config.CHICKEN_NUGGET_FOOD.get().nutrition())
                            .saturationModifier(Config.CHICKEN_NUGGET_FOOD.get().saturation() / Config.CHICKEN_NUGGET_FOOD.get().nutrition())
                            .build()
                    )
            )
            .creativeAfter(Items.ROTTEN_FLESH, CreativeModeTabs.FOOD_AND_DRINKS)
            .lootTable(
                    List.of(EntityType.ZOMBIE.getDefaultLootTable(), EntityType.CREEPER.getDefaultLootTable()),
                    (registries, item) -> LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.025f, 0.01f))
                        .add(LootItem.lootTableItem(item))
            )
            .build();

    public static final Supplier<WateringCanItem> WATERING_CAN = new Builder("watering_can")
            .config(() -> Config.WATERING_CAN.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
                    .durability(Config.WATERING_CAN_DURABILITY.get().durability())
            )
            .creativeAfter(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(WateringCanItem::new);

    public static final Supplier<WrenchItem> WRENCH_ITEM = new Builder("wrench")
            .config(() -> Config.WRENCH.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
                    .durability(Config.WRENCH_DURABILITY.get().durability())
            )
            .creativeAfter(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .creativeAfter(Items.TARGET, CreativeModeTabs.REDSTONE_BLOCKS)
            .build(WrenchItem::new);

    public static final Supplier<CrossbowWithSpyglassItem> CROSSBOW_WITH_SPYGLASS = new Builder("crossbow_with_spyglass")
            .config(() -> Config.CROSSBOW_WITH_SPYGLASS.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
                    .durability(Config.CROSSBOW_WITH_SPYGLASS_DURABILITY.get().durability())
            )
            .creativeAfter(Items.CROSSBOW, CreativeModeTabs.COMBAT)
            .build(CrossbowWithSpyglassItem::new);

    public static final Supplier<Item> TRIDENT_SHARD = new Builder("trident_shard")
            .config(() -> Config.TRIDENT_SHARD.get().enabled())
            .creativeAfter(Items.PRISMARINE_CRYSTALS, CreativeModeTabs.INGREDIENTS)
            .lootTable(
                    EntityType.ELDER_GUARDIAN.getDefaultLootTable(),
                    (registries, item) -> LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1f))
                        .add(LootItem.lootTableItem(item))
                        .when(LootItemRandomChanceCondition.randomChance(0.33f))
            )
            .build();

    public static final Supplier<Item> GLOW_STICK_ITEM = new Builder("glow_stick")
            .config(() -> Config.GLOW_STICK.get().enabled())
            .creativeAfter(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .lootTable(
                    List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.STRONGHOLD_CORRIDOR),
                    (registries, item) -> LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0, 4))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
                        .add(LootItem.lootTableItem(item))
            )
            .build();

    public static final Supplier<Item> DEPTH_METER_ITEM = new Builder("depth_meter")
            .config(() -> Config.DEPTH_METER.get().enabled())
            .creativeAfter(Items.CLOCK, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .lootTable(
                    List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT, BuiltInLootTables.STRONGHOLD_CORRIDOR),
                    (registries, item) -> LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                        .add(LootItem.lootTableItem(item))
            )
            .build(Item::new);

    public static final Supplier<MysteriousBundleItem> MYSTERIOUS_BUNDLE_ITEM = new Builder("mysterious_bundle")
            .config(() -> Config.MYSTERIOUS_BUNDLE.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
            )
            .creativeAfter(Items.ELYTRA, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(MysteriousBundleItem::new);

    public static final Supplier<PocketJukeboxItem> POCKET_JUKEBOX_ITEM = new Builder("pocket_jukebox")
            .config(() -> Config.POCKET_JUKEBOX.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
            )
            .creativeAfter(Items.SPYGLASS, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(PocketJukeboxItem::new);

    public static final Supplier<Item> ROSE_GOLD_INGOT = new Builder("rose_gold_ingot")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .creativeAfter(Items.GOLD_INGOT, CreativeModeTabs.INGREDIENTS)
            .build();

    public static final Supplier<Item> MUSIC_DISC_0308 = new Builder("music_disc_0308")
            .config(() -> Config.MUSIC_DISC_0308.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "0308")))
            )
            .creativeAfter(Items.MUSIC_DISC_WARD, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .lootTable(
                    List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.WOODLAND_MANSION),
                    (registries, item) -> LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.25f))
                            .add(LootItem.lootTableItem(item))
            )
            .build();

    public static final Supplier<Item> MUSIC_DISC_1007 = new Builder("music_disc_1007")
            .config(() -> Config.MUSIC_DISC_1007.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "1007")))
            )
            .creativeAfter(Items.MUSIC_DISC_WARD, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .lootTable(
                    List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.WOODLAND_MANSION),
                    (registries, item) -> LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.25f))
                            .add(LootItem.lootTableItem(item))
            )
            .build();

    public static final Supplier<Item> MUSIC_DISC_1507 = new Builder("music_disc_1507")
            .config(() -> Config.MUSIC_DISC_1507.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "1507")))
            )
            .creativeAfter(Items.MUSIC_DISC_WARD, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .lootTable(
                    List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.WOODLAND_MANSION),
                    (registries, item) -> LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(0.25f))
                            .add(LootItem.lootTableItem(item))
            )
            .build();

    public static final RoseGoldToolMaterial ROSE_GOLD_TOOL_MATERIAL = new RoseGoldToolMaterial();

    public static final Supplier<Item> ROSE_GOLD_HELMET = new Builder("rose_gold_helmet")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(312)
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT)
            .build(p -> new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.HELMET, p));

    public static final Supplier<Item> ROSE_GOLD_CHESTPLATE = new Builder("rose_gold_chestplate")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(360)
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT)
            .build(p -> new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.CHESTPLATE, p));

    public static final Supplier<Item> ROSE_GOLD_LEGGINGS = new Builder("rose_gold_leggings")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(384)
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT)
            .build(p -> new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.LEGGINGS, p));

    public static final Supplier<Item> ROSE_GOLD_BOOTS = new Builder("rose_gold_boots")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(264)
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT)
            .build(p -> new ArmorItem(AdditionalMaterials.ROSE_GOLD, ArmorItem.Type.BOOTS, p));

    public static final Supplier<Item> ROSE_GOLD_SWORD = new Builder("rose_gold_sword")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .attributes(SwordItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 4, -2.4F))
            )
            .creativeAfter(Items.GOLDEN_SWORD, CreativeModeTabs.COMBAT)
            .build(p -> new SwordItem(ROSE_GOLD_TOOL_MATERIAL, p));

    public static final Supplier<Item> ROSE_GOLD_AXE = new Builder("rose_gold_axe")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .attributes(AxeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 6, -3.1F))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(p -> new AxeItem(ROSE_GOLD_TOOL_MATERIAL, p));

    public static final Supplier<Item> ROSE_GOLD_PICKAXE = new Builder("rose_gold_pickaxe")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .attributes(PickaxeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 1, -2.8F))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(p -> new PickaxeItem(ROSE_GOLD_TOOL_MATERIAL, p));

    public static final Supplier<Item> ROSE_GOLD_SHOVEL = new Builder("rose_gold_shovel")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .attributes(ShovelItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, 1.5F, -3F))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(p -> new ShovelItem(ROSE_GOLD_TOOL_MATERIAL, p));

    public static final Supplier<Item> ROSE_GOLD_HOE = new Builder("rose_gold_hoe")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .attributes(HoeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, -2, -1F))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(p -> new HoeItem(ROSE_GOLD_TOOL_MATERIAL, p));

    public static final Supplier<SuspiciousDyeItem> WHITE_SUSPICIOUS_DYE = new Builder("white_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.WHITE))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.WHITE, p));

    public static final Supplier<SuspiciousDyeItem> BROWN_SUSPICIOUS_DYE = new Builder("brown_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.BROWN))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.BROWN, p));

    public static final Supplier<SuspiciousDyeItem> RED_SUSPICIOUS_DYE = new Builder("red_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.RED))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.RED, p));

    public static final Supplier<SuspiciousDyeItem> ORANGE_SUSPICIOUS_DYE = new Builder("orange_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.ORANGE))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.ORANGE, p));

    public static final Supplier<SuspiciousDyeItem> YELLOW_SUSPICIOUS_DYE = new Builder("yellow_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.YELLOW))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.YELLOW, p));

    public static final Supplier<SuspiciousDyeItem> LIME_SUSPICIOUS_DYE = new Builder("lime_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.LIME))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.LIME, p));

    public static final Supplier<SuspiciousDyeItem> GREEN_SUSPICIOUS_DYE = new Builder("green_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.GREEN))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.GREEN, p));

    public static final Supplier<SuspiciousDyeItem> CYAN_SUSPICIOUS_DYE = new Builder("cyan_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.CYAN))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.CYAN, p));

    public static final Supplier<SuspiciousDyeItem> LIGHT_BLUE_SUSPICIOUS_DYE = new Builder("light_blue_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.LIGHT_BLUE))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.LIGHT_BLUE, p));

    public static final Supplier<SuspiciousDyeItem> BLUE_SUSPICIOUS_DYE = new Builder("blue_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.BLUE))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.BLUE, p));

    public static final Supplier<SuspiciousDyeItem> PURPLE_SUSPICIOUS_DYE = new Builder("purple_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.PURPLE))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.PURPLE, p));

    public static final Supplier<SuspiciousDyeItem> MAGENTA_SUSPICIOUS_DYE = new Builder("magenta_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.MAGENTA))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.MAGENTA, p));

    public static final Supplier<SuspiciousDyeItem> PINK_SUSPICIOUS_DYE = new Builder("pink_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AdditionalMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.PINK))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.PINK, p));

    public static final Supplier<RopeArrowItem> ROPE_ARROW = new Builder("rope_arrow")
            .creativeAfter(Items.ARROW, CreativeModeTabs.COMBAT)
            .build(RopeArrowItem::new);

    private static class Builder {
        private final String id;
        private final Map<ResourceKey<CreativeModeTab>, List<ItemLike>> creativeAfter = new HashMap<>();
        private final Map<ResourceKey<CreativeModeTab>, List<ItemLike>> creativeBefore = new HashMap<>();
        private final Map<ResourceKey<LootTable>, BiFunction<HolderLookup.Provider, Item, LootPool.Builder>> lootTables = new HashMap<>();
        private Consumer<Item.Properties> propertiesConfig = p -> {};
        private Supplier<Boolean> config = () -> true;

        public Builder(String id) {
            this.id = id;
        }

        public Builder properties(Consumer<Item.Properties> consumer) {
            this.propertiesConfig = consumer;
            return this;
        }

        public Builder config(Supplier<Boolean> config) {
            this.config = config;
            return this;
        }

        public Builder creativeAfter(ItemLike item, ResourceKey<CreativeModeTab> category) {
            this.creativeAfter.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
            return this;
        }

        public Builder creativeBefore(ItemLike item, ResourceKey<CreativeModeTab> category) {
            this.creativeBefore.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
            return this;
        }

        public Builder lootTable(ResourceKey<LootTable> table, BiFunction<HolderLookup.Provider, Item, LootPool.Builder> lootPoolBuilder) {
            this.lootTables.put(table, lootPoolBuilder);
            return this;
        }

        public Builder lootTable(List<ResourceKey<LootTable>> tables, BiFunction<HolderLookup.Provider, Item, LootPool.Builder> lootPoolBuilder) {
            tables.forEach(table -> this.lootTables.put(table, lootPoolBuilder));
            return this;
        }

        public Supplier<Item> build() {
            return build(Item::new);
        }

        public <T extends Item> Supplier<T> build(Function<Item.Properties, T> itemFactory) {
            ResourceLocation location = ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, id);

            Consumer<Item.Properties> deferredProperties = propertiesConfig;
            Supplier<T> item = AdditionalRegistries.ITEMS.register(location, () -> {
                Item.Properties props = new Item.Properties();
                deferredProperties.accept(props);
                return itemFactory.apply(props);
            });

            creativeAfter.forEach((tab, items) ->
                items.forEach(anchor -> CreativeAdder.add(tab, config, anchor, item))
            );

            creativeBefore.forEach((tab, items) ->
                items.forEach(anchor -> CreativeAdder.addBefore(tab, config, anchor, item))
            );

            lootTables.forEach((table, builder) ->
                LootHandler.register(table, config, provider -> builder.apply(provider, item.get()))
            );

            return item;
        }
    }

    public static void registerAll() {
        LootHandler.register(BuiltInLootTables.SHIPWRECK_SUPPLY, () -> Config.SHIPWRECK_SPYGLASS_LOOT.get().enabled(), LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.5f))
                .add(LootItem.lootTableItem(Items.SPYGLASS))
        );

        if (Config.WRENCH.get().enabled()) {
            Registrar.defer(() -> {
                DispenserBlock.registerBehavior(WRENCH_ITEM.get(), new DefaultDispenseItemBehavior() {
                    public ItemStack execute(BlockSource pointer, ItemStack stack) {
                        WrenchItem wrench = (WrenchItem) stack.getItem();

                        BlockState dstate = pointer.state();
                        BlockPos pos = pointer.pos().relative(dstate.getValue(BlockStateProperties.FACING));
                        BlockState state = pointer.level().getBlockState(pos);

                        wrench.dispenserUse(pointer.level(), pos, state, stack);
                        return stack;
                    }
                });
            });
        }
    }
}
