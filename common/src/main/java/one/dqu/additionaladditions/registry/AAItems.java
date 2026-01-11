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
import one.dqu.additionaladditions.config.type.SwordItemConfig;
import one.dqu.additionaladditions.config.type.ToolItemConfig;
import one.dqu.additionaladditions.glint.GlintColor;
import one.dqu.additionaladditions.item.*;
import one.dqu.additionaladditions.item.configurable.*;
import one.dqu.additionaladditions.misc.AlbumContents;
import one.dqu.additionaladditions.util.CreativeAdder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import one.dqu.additionaladditions.util.Registrar;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class AAItems {

    // BLOCKS

    public static final Supplier<Item> ROPE = new Builder("rope")
            .config(() -> Config.ROPE.get().enabled())
            .creativeAfter(Items.LADDER, CreativeModeTabs.FUNCTIONAL_BLOCKS)
            .creativeAfter(Items.SPYGLASS, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(p -> new BlockItem(AABlocks.ROPE_BLOCK.get(), p));

    public static final Supplier<Item> TINTED_REDSTONE_LAMP = new Builder("tinted_redstone_lamp")
            .config(() -> Config.TINTED_REDSTONE_LAMP.get().enabled())
            .creativeAfter(Items.REDSTONE_LAMP, CreativeModeTabs.REDSTONE_BLOCKS)
            .creativeAfter(Items.REDSTONE_LAMP, CreativeModeTabs.FUNCTIONAL_BLOCKS)
            .build(p -> new BlockItem(AABlocks.TINTED_REDSTONE_LAMP.get(), p));

    public static final Supplier<CopperPatinaItem> COPPER_PATINA = new Builder("copper_patina")
            .config(() -> Config.COPPER_PATINA.get().enabled())
            .creativeAfter(Items.REDSTONE, CreativeModeTabs.REDSTONE_BLOCKS)
            .build(p -> new CopperPatinaItem(AABlocks.COPPER_PATINA.get(), p));

    public static final Supplier<Item> PATINA_BLOCK = new Builder("patina_block")
            .config(() -> Config.COPPER_PATINA.get().enabled())
            .creativeBefore(Items.COPPER_BLOCK, CreativeModeTabs.BUILDING_BLOCKS)
            .build(p -> new BlockItem(AABlocks.PATINA_BLOCK.get(), p));

    public static final Supplier<Item> ROSE_GOLD_BLOCK = new Builder("rose_gold_block")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .creativeAfter(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS)
            .build(p -> new BlockItem(AABlocks.ROSE_GOLD_BLOCK.get(), p));

    // SNIFFER PLANTS

    public static final Supplier<Item> COTTONSHIVER = new Builder("cottonshiver")
            .creativeAfter(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new BlockItem(AABlocks.COTTONSHIVER.get(), p));
    public static final Supplier<Item> COTTONSHIVER_POD = new Builder("cottonshiver_pod")
            .creativeAfter(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new ItemNameBlockItem(AABlocks.COTTONSHIVER_CROP.get(), p));

    public static final Supplier<Item> MUDFLOWER = new Builder("mudflower")
            .creativeAfter(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new BlockItem(AABlocks.MUDFLOWER.get(), p));
    public static final Supplier<Item> MUDFLOWER_SEEDS = new Builder("mudflower_seeds")
            .creativeAfter(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new ItemNameBlockItem(AABlocks.MUDFLOWER_CROP.get(), p));

    public static final Supplier<Item> CRIMSON_BLOSSOM = new Builder("crimson_blossom")
            .creativeAfter(Items.TORCHFLOWER, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new BlockItem(AABlocks.CRIMSON_BLOSSOM.get(), p));
    public static final Supplier<Item> CRIMSON_BLOSSOM_SEEDS = new Builder("crimson_blossom_seeds")
            .creativeAfter(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new ItemNameBlockItem(AABlocks.CRIMSON_BLOSSOM_CROP.get(), p));

    public static final Supplier<Item> AMBER_BLOSSOM = new Builder("amber_blossom")
            .creativeAfter(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new BlockItem(AABlocks.AMBER_BLOSSOM.get(), p));
    public static final Supplier<Item> AMBER_BLOSSOM_SEEDS = new Builder("amber_blossom_seeds")
            .creativeAfter(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new ItemNameBlockItem(AABlocks.AMBER_BLOSSOM_CROP.get(), p));

    public static final Supplier<Item> BULBUS = new Builder("bulbus")
            .creativeAfter(Items.TORCHFLOWER, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new BlockItem(AABlocks.BULBUS.get(), p));
    public static final Supplier<Item> BULBUS_POD = new Builder("bulbus_pod")
            .creativeAfter(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new ItemNameBlockItem(AABlocks.BULBUS_CROP.get(), p));

    public static final Supplier<Item> SAWTOOTH_FERN = new Builder("sawtooth_fern")
            .creativeAfter(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new BlockItem(AABlocks.SAWTOOTH_FERN.get(), p));
    public static final Supplier<Item> SAWTOOTH_FERN_FIDDLEHEAD = new Builder("sawtooth_fern_fiddlehead")
            .creativeAfter(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new ItemNameBlockItem(AABlocks.SAWTOOTH_FERN_CROP.get(), p));

    public static final Supplier<Item> FROSTLEAF = new Builder("frostleaf")
            .creativeAfter(Items.TORCHFLOWER, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new BlockItem(AABlocks.FROSTLEAF.get(), p));
    public static final Supplier<Item> FROSTLEAF_POD = new Builder("frostleaf_pod")
            .creativeAfter(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new ItemNameBlockItem(AABlocks.FROSTLEAF_CROP.get(), p));

    public static final Supplier<Item> WISTERIA = new Builder("wisteria")
            .creativeAfter(Items.TORCHFLOWER, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new BlockItem(AABlocks.WISTERIA.get(), p));
    public static final Supplier<Item> WISTERIA_VINES = new Builder("wisteria_vines")
            .creativeAfter(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new ItemNameBlockItem(AABlocks.WISTERIA_CROP.get(), p));

    public static final Supplier<Item> SPIKEBLOSSOM = new Builder("spikeblossom")
            .creativeAfter(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new BlockItem(AABlocks.SPIKEBLOSSOM.get(), p));
    public static final Supplier<Item> SPIKEBLOSSOM_SEEDS = new Builder("spikeblossom_seeds")
            .creativeAfter(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new ItemNameBlockItem(AABlocks.SPIKEBLOSSOM_CROP.get(), p));

    public static final Supplier<Item> SNAPDRAGON = new Builder("snapdragon")
            .creativeAfter(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new BlockItem(AABlocks.SNAPDRAGON.get(), p));
    public static final Supplier<Item> SNAPDRAGON_POD = new Builder("snapdragon_pod")
            .creativeAfter(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new ItemNameBlockItem(AABlocks.SNAPDRAGON_CROP.get(), p));

    public static final Supplier<Item> LOTUS_LILY = new Builder("lotus_lily")
            .creativeAfter(Items.TORCHFLOWER, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new PlaceOnWaterBlockItem(AABlocks.LOTUS_LILY.get(), p));
    public static final Supplier<Item> LOTUS_LILY_POD = new Builder("lotus_lily_pod")
            .creativeAfter(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS)
            .build(p -> new ItemNamePlaceOnWaterBlockItem(AABlocks.LOTUS_LILY_CROP.get(), p));

    // FOOD

    public static final Supplier<FoodItem> FRIED_EGG = new Builder("fried_egg")
            .config(() -> Config.FRIED_EGG.get().enabled())
            .properties(p -> p.food(Config.FRIED_EGG.get().food()))
            .creativeAfter(Items.COOKED_RABBIT, CreativeModeTabs.FOOD_AND_DRINKS)
            .build(p -> new FoodItem(p, Config.FRIED_EGG));

    public static final Supplier<FoodItem> BERRY_PIE = new Builder("berry_pie")
            .config(() -> Config.BERRY_PIE.get().enabled())
            .properties(p -> p.food(Config.BERRY_PIE.get().food()))
            .creativeAfter(Items.PUMPKIN_PIE, CreativeModeTabs.FOOD_AND_DRINKS)
            .build(p -> new FoodItem(p, Config.BERRY_PIE));

    public static final Supplier<FoodItem> HONEYED_APPLE = new Builder("honeyed_apple")
            .config(() -> Config.HONEYED_APPLE.get().enabled())
            .properties(p -> p.food(Config.HONEYED_APPLE.get().food()))
            .creativeAfter(Items.APPLE, CreativeModeTabs.FOOD_AND_DRINKS)
            .build(p -> new FoodItem(p, Config.HONEYED_APPLE));

    public static final Supplier<FoodItem> CHICKEN_NUGGET = new Builder("chicken_nugget")
            .config(() -> Config.CHICKEN_NUGGET.get().enabled())
            .properties(p -> p.food(Config.CHICKEN_NUGGET.get().food()))
            .creativeAfter(Items.ROTTEN_FLESH, CreativeModeTabs.FOOD_AND_DRINKS)
            .build(p -> new FoodItem(p, Config.CHICKEN_NUGGET));

    // OTHER

    public static final Supplier<WateringCanItem> WATERING_CAN = new Builder("watering_can")
            .config(() -> Config.WATERING_CAN.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
                    .component(AAMisc.WATER_LEVEL_COMPONENT.get(), 0)
            )
            .creativeAfter(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(WateringCanItem::new);

    public static final Supplier<WrenchItem> WRENCH_ITEM = new Builder("wrench")
            .config(() -> Config.WRENCH.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
                    .durability(Config.WRENCH.get().durability())
            )
            .creativeAfter(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .creativeAfter(Items.TARGET, CreativeModeTabs.REDSTONE_BLOCKS)
            .build(WrenchItem::new);

    public static final Supplier<Item> TRIDENT_SHARD = new Builder("trident_shard")
            .config(() -> Config.TRIDENT_SHARD.get().enabled())
            .creativeAfter(Items.PRISMARINE_CRYSTALS, CreativeModeTabs.INGREDIENTS)
            .build();

    public static final Supplier<Item> GLOW_STICK_ITEM = new Builder("glow_stick")
            .config(() -> Config.GLOW_STICK.get().enabled())
            .creativeAfter(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(GlowStickItem::new);

    public static final Supplier<Item> BAROMETER = new Builder("barometer")
            .config(() -> Config.BAROMETER.get().enabled())
            .creativeAfter(Items.CLOCK, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(Item::new);

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
            .build();

    public static final Supplier<Item> MUSIC_DISC_1007 = new Builder("music_disc_1007")
            .config(() -> Config.MUSIC_DISC_1007.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "1007")))
            )
            .creativeAfter(Items.MUSIC_DISC_WARD, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build();

    public static final Supplier<Item> MUSIC_DISC_1507 = new Builder("music_disc_1507")
            .config(() -> Config.MUSIC_DISC_1507.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "1507")))
            )
            .creativeAfter(Items.MUSIC_DISC_WARD, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build();

    // ROSE GOLD

    public static final RoseGoldToolMaterial ROSE_GOLD_TOOL_MATERIAL = new RoseGoldToolMaterial();

    public static final Supplier<Item> ROSE_GOLD_HELMET = new Builder("rose_gold_helmet")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(Config.ROSE_GOLD_HELMET.get().durability())
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT)
            .build(p -> new RoseGoldArmorItem(ArmorItem.Type.HELMET, p));

    public static final Supplier<Item> ROSE_GOLD_CHESTPLATE = new Builder("rose_gold_chestplate")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(Config.ROSE_GOLD_CHESTPLATE.get().durability())
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT)
            .build(p -> new RoseGoldArmorItem(ArmorItem.Type.CHESTPLATE, p));

    public static final Supplier<Item> ROSE_GOLD_LEGGINGS = new Builder("rose_gold_leggings")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(Config.ROSE_GOLD_LEGGINGS.get().durability())
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT)
            .build(p -> new RoseGoldArmorItem(ArmorItem.Type.LEGGINGS, p));

    public static final Supplier<Item> ROSE_GOLD_BOOTS = new Builder("rose_gold_boots")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(Config.ROSE_GOLD_BOOTS.get().durability())
            )
            .creativeAfter(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT)
            .build(p -> new RoseGoldArmorItem(ArmorItem.Type.BOOTS, p));

    public static final Supplier<Item> ROSE_GOLD_HORSE_ARMOR = new Builder("rose_gold_horse_armor")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .stacksTo(1)
            )
            .creativeAfter(Items.GOLDEN_HORSE_ARMOR, CreativeModeTabs.COMBAT)
            .build(p -> new RoseGoldAnimalArmorItem(AnimalArmorItem.BodyType.EQUESTRIAN, false, p));

    public static final Supplier<Item> ROSE_GOLD_SWORD = new Builder("rose_gold_sword")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(Config.ROSE_GOLD_SWORD.get().durability())
                    .attributes(SwordItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, Config.ROSE_GOLD_SWORD.get().attackDamage(), Config.ROSE_GOLD_SWORD.get().attackSpeed()))
            )
            .creativeAfter(Items.GOLDEN_SWORD, CreativeModeTabs.COMBAT)
            .build(p -> new ConfigurableSwordItem(ROSE_GOLD_TOOL_MATERIAL, p, builder -> {
                SwordItemConfig config = Config.ROSE_GOLD_SWORD.get();
                builder.set(DataComponents.MAX_DAMAGE, config.durability());
                builder.set(DataComponents.ATTRIBUTE_MODIFIERS, SwordItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, config.attackDamage(), config.attackSpeed()));
            }));

    public static final Supplier<Item> ROSE_GOLD_SHOVEL = new Builder("rose_gold_shovel")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(Config.ROSE_GOLD_SHOVEL.get().durability())
                    .attributes(ShovelItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, Config.ROSE_GOLD_SHOVEL.get().attackDamage(), Config.ROSE_GOLD_SHOVEL.get().attackSpeed()))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(p -> new ConfigurableShovelItem(ROSE_GOLD_TOOL_MATERIAL, p, builder -> {
                ToolItemConfig config = Config.ROSE_GOLD_SHOVEL.get();
                builder.set(DataComponents.MAX_DAMAGE, config.durability());
                builder.set(DataComponents.ATTRIBUTE_MODIFIERS, ShovelItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, config.attackDamage(), config.attackSpeed()));
            }));

    public static final Supplier<Item> ROSE_GOLD_PICKAXE = new Builder("rose_gold_pickaxe")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(Config.ROSE_GOLD_PICKAXE.get().durability())
                    .attributes(PickaxeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, Config.ROSE_GOLD_PICKAXE.get().attackDamage(), Config.ROSE_GOLD_PICKAXE.get().attackSpeed()))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(p -> new ConfigurablePickaxeItem(ROSE_GOLD_TOOL_MATERIAL, p, builder -> {
                ToolItemConfig config = Config.ROSE_GOLD_PICKAXE.get();
                builder.set(DataComponents.MAX_DAMAGE, config.durability());
                builder.set(DataComponents.ATTRIBUTE_MODIFIERS, PickaxeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, config.attackDamage(), config.attackSpeed()));
            }));

    public static final Supplier<Item> ROSE_GOLD_AXE = new Builder("rose_gold_axe")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(Config.ROSE_GOLD_AXE.get().durability())
                    .attributes(AxeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, Config.ROSE_GOLD_AXE.get().attackDamage(), Config.ROSE_GOLD_AXE.get().attackSpeed()))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .creativeAfter(Items.GOLDEN_AXE, CreativeModeTabs.COMBAT)
            .build(p -> new ConfigurableAxeItem(ROSE_GOLD_TOOL_MATERIAL, p, builder -> {
                ToolItemConfig config = Config.ROSE_GOLD_AXE.get();
                builder.set(DataComponents.MAX_DAMAGE, config.durability());
                builder.set(DataComponents.ATTRIBUTE_MODIFIERS, AxeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, config.attackDamage(), config.attackSpeed()));
            }));

    public static final Supplier<Item> ROSE_GOLD_HOE = new Builder("rose_gold_hoe")
            .config(() -> Config.ROSE_GOLD.get().enabled())
            .properties(p -> p
                    .durability(Config.ROSE_GOLD_HOE.get().durability())
                    .attributes(HoeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, Config.ROSE_GOLD_HOE.get().attackDamage(), Config.ROSE_GOLD_HOE.get().attackSpeed()))
            )
            .creativeAfter(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .build(p -> new ConfigurableHoeItem(ROSE_GOLD_TOOL_MATERIAL, p, builder -> {
                ToolItemConfig config = Config.ROSE_GOLD_HOE.get();
                builder.set(DataComponents.MAX_DAMAGE, config.durability());
                builder.set(DataComponents.ATTRIBUTE_MODIFIERS, HoeItem.createAttributes(ROSE_GOLD_TOOL_MATERIAL, config.attackDamage(), config.attackSpeed()));
            }));

    // SUSPICIOUS DYE

    public static final Supplier<SuspiciousDyeItem> WHITE_SUSPICIOUS_DYE = new Builder("white_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.WHITE))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.WHITE, p));

    public static final Supplier<SuspiciousDyeItem> BROWN_SUSPICIOUS_DYE = new Builder("brown_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.BROWN))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.BROWN, p));

    public static final Supplier<SuspiciousDyeItem> RED_SUSPICIOUS_DYE = new Builder("red_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.RED))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.RED, p));

    public static final Supplier<SuspiciousDyeItem> ORANGE_SUSPICIOUS_DYE = new Builder("orange_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.ORANGE))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.ORANGE, p));

    public static final Supplier<SuspiciousDyeItem> YELLOW_SUSPICIOUS_DYE = new Builder("yellow_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.YELLOW))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.YELLOW, p));

    public static final Supplier<SuspiciousDyeItem> LIME_SUSPICIOUS_DYE = new Builder("lime_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.LIME))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.LIME, p));

    public static final Supplier<SuspiciousDyeItem> GREEN_SUSPICIOUS_DYE = new Builder("green_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.GREEN))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.GREEN, p));

    public static final Supplier<SuspiciousDyeItem> CYAN_SUSPICIOUS_DYE = new Builder("cyan_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.CYAN))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.CYAN, p));

    public static final Supplier<SuspiciousDyeItem> LIGHT_BLUE_SUSPICIOUS_DYE = new Builder("light_blue_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.LIGHT_BLUE))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.LIGHT_BLUE, p));

    public static final Supplier<SuspiciousDyeItem> BLUE_SUSPICIOUS_DYE = new Builder("blue_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.BLUE))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.BLUE, p));

    public static final Supplier<SuspiciousDyeItem> PURPLE_SUSPICIOUS_DYE = new Builder("purple_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.PURPLE))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.PURPLE, p));

    public static final Supplier<SuspiciousDyeItem> MAGENTA_SUSPICIOUS_DYE = new Builder("magenta_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.MAGENTA))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.MAGENTA, p));

    public static final Supplier<SuspiciousDyeItem> PINK_SUSPICIOUS_DYE = new Builder("pink_suspicious_dye")
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(DyeColor.PINK))
            )
            .creativeBefore(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS)
            .build(p -> new SuspiciousDyeItem(DyeColor.PINK, p));

    // OTHER

    public static final Supplier<RopeArrowItem> ROPE_ARROW = new Builder("rope_arrow")
            .creativeAfter(Items.ARROW, CreativeModeTabs.COMBAT)
            .build(RopeArrowItem::new);

    // ALBUMS

    public static final Supplier<AlbumItem> ALBUM = new Builder("album")
            .creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES)
            .properties(p -> p
                    .stacksTo(1)
                    .component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)
            )
            .build(AlbumItem::new);
    public static final Supplier<AlbumItem> WHITE_ALBUM = new Builder("white_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> LIGHT_GRAY_ALBUM = new Builder("light_gray_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> GRAY_ALBUM = new Builder("gray_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> BLACK_ALBUM = new Builder("black_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> BROWN_ALBUM = new Builder("brown_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> RED_ALBUM = new Builder("red_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> ORANGE_ALBUM = new Builder("orange_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> YELLOW_ALBUM = new Builder("yellow_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> LIME_ALBUM = new Builder("lime_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> GREEN_ALBUM = new Builder("green_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> CYAN_ALBUM = new Builder("cyan_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> LIGHT_BLUE_ALBUM = new Builder("light_blue_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> BLUE_ALBUM = new Builder("blue_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> PURPLE_ALBUM = new Builder("purple_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> MAGENTA_ALBUM = new Builder("magenta_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);
    public static final Supplier<AlbumItem> PINK_ALBUM = new Builder("pink_album").creativeBefore(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES).properties(p -> p.stacksTo(1).component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)).build(AlbumItem::new);

    private static class Builder {
        private final String id;
        private final Map<ResourceKey<CreativeModeTab>, List<ItemLike>> creativeAfter = new HashMap<>();
        private final Map<ResourceKey<CreativeModeTab>, List<ItemLike>> creativeBefore = new HashMap<>();
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

        public Supplier<Item> build() {
            return build(Item::new);
        }

        public <T extends Item> Supplier<T> build(Function<Item.Properties, T> itemFactory) {
            ResourceLocation location = ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, id);

            Consumer<Item.Properties> deferredProperties = propertiesConfig;
            Supplier<T> item = AARegistries.ITEMS.register(location, () -> {
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

            return item;
        }
    }

    public static void registerAll() {
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
