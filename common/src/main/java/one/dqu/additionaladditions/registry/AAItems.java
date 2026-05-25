package one.dqu.additionaladditions.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.core.builder.AAItem;
import one.dqu.additionaladditions.feature.album.AlbumContents;
import one.dqu.additionaladditions.feature.glint.GlintColor;
import one.dqu.additionaladditions.item.*;
import one.dqu.additionaladditions.material.AAMaterials;
import one.dqu.additionaladditions.material.AnimalArmorType;
import one.dqu.additionaladditions.material.ToolType;
import one.dqu.additionaladditions.util.Registrar;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class AAItems {
    private static final Map<Identifier, Set<Supplier<? extends Item>>> CONFIG_TO_ITEM = new HashMap<>();

    public static Collection<Supplier<? extends Item>> fromConfigProperty(ConfigProperty<?> configProperty) {
        return CONFIG_TO_ITEM.getOrDefault(configProperty.path(), Set.of());
    }

    // BLOCKS

    public static final Supplier<Item> ROPE = new AAItem<>()
            .config(Config.ROPE)
            .factory(p -> new BlockItem(AABlocks.ROPE_BLOCK.get(), p))
            .creative(Items.LADDER, CreativeModeTabs.FUNCTIONAL_BLOCKS, false)
            .creative(Items.SPYGLASS, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .make("rope");

    public static final Supplier<Item> TINTED_REDSTONE_LAMP = new AAItem<>()
            .config(Config.TINTED_REDSTONE_LAMP)
            .factory(p -> new BlockItem(AABlocks.TINTED_REDSTONE_LAMP.get(), p))
            .creative(Items.REDSTONE_LAMP, CreativeModeTabs.REDSTONE_BLOCKS, false)
            .creative(Items.REDSTONE_LAMP, CreativeModeTabs.FUNCTIONAL_BLOCKS, false)
            .make("tinted_redstone_lamp");

    public static final Supplier<CopperPatinaItem> COPPER_PATINA = new AAItem<CopperPatinaItem>()
            .config(Config.COPPER_PATINA)
            .factory(p -> new CopperPatinaItem(AABlocks.COPPER_PATINA.get(), p))
            .creative(Items.REDSTONE, CreativeModeTabs.REDSTONE_BLOCKS, false)
            .make("copper_patina");

    public static final Supplier<Item> PATINA_BLOCK = new AAItem<>()
            .config(Config.COPPER_PATINA)
            .factory(p -> new BlockItem(AABlocks.PATINA_BLOCK.get(), p))
            .creative(Items.COPPER_BLOCK, CreativeModeTabs.BUILDING_BLOCKS, true)
            .make("patina_block");

    public static final Supplier<Item> ROSE_GOLD_BLOCK = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .factory(p -> new BlockItem(AABlocks.ROSE_GOLD_BLOCK.get(), p))
            .creative(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, CreativeModeTabs.BUILDING_BLOCKS, false)
            .make("rose_gold_block");

    // SNIFFER PLANTS

    public static final Supplier<Item> COTTONSHIVER = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.COTTONSHIVER.get(), p))
            .creative(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("cottonshiver");
    public static final Supplier<Item> COTTONSHIVER_POD = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.COTTONSHIVER_CROP.get(), p))
            .properties(p -> p.useItemDescriptionPrefix())
            .creative(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("cottonshiver_pod");

    public static final Supplier<Item> MUDFLOWER = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.MUDFLOWER.get(), p))
            .creative(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("mudflower");
    public static final Supplier<Item> MUDFLOWER_SEEDS = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.MUDFLOWER_CROP.get(), p))
            .properties(p -> p.useItemDescriptionPrefix())
            .creative(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("mudflower_seeds");

    public static final Supplier<Item> CRIMSON_BLOSSOM = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.CRIMSON_BLOSSOM.get(), p))
            .creative(Items.TORCHFLOWER, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("crimson_blossom");
    public static final Supplier<Item> CRIMSON_BLOSSOM_SEEDS = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.CRIMSON_BLOSSOM_CROP.get(), p))
            .properties(p -> p.useItemDescriptionPrefix())
            .creative(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("crimson_blossom_seeds");

    public static final Supplier<Item> AMBER_BLOSSOM = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.AMBER_BLOSSOM.get(), p))
            .creative(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("amber_blossom");
    public static final Supplier<Item> AMBER_BLOSSOM_SEEDS = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.AMBER_BLOSSOM_CROP.get(), p))
            .properties(p -> p.useItemDescriptionPrefix())
            .creative(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("amber_blossom_seeds");

    public static final Supplier<Item> BULBUS = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.BULBUS.get(), p))
            .creative(Items.TORCHFLOWER, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("bulbus");
    public static final Supplier<Item> BULBUS_POD = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.BULBUS_CROP.get(), p))
            .properties(p -> p.useItemDescriptionPrefix())
            .creative(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("bulbus_pod");

    public static final Supplier<Item> SAWTOOTH_FERN = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.SAWTOOTH_FERN.get(), p))
            .creative(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("sawtooth_fern");
    public static final Supplier<Item> SAWTOOTH_FERN_FIDDLEHEAD = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.SAWTOOTH_FERN_CROP.get(), p))
            .properties(p -> p.useItemDescriptionPrefix())
            .creative(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("sawtooth_fern_fiddlehead");

    public static final Supplier<Item> FROSTLEAF = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.FROSTLEAF.get(), p))
            .creative(Items.TORCHFLOWER, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("frostleaf");
    public static final Supplier<Item> FROSTLEAF_POD = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.FROSTLEAF_CROP.get(), p))
            .properties(p -> p.useItemDescriptionPrefix())
            .creative(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("frostleaf_pod");

    public static final Supplier<Item> WISTERIA = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.WISTERIA.get(), p))
            .creative(Items.TORCHFLOWER, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("wisteria");
    public static final Supplier<Item> WISTERIA_VINES = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.WISTERIA_CROP.get(), p))
            .properties(p -> p.useItemDescriptionPrefix())
            .creative(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("wisteria_vines");

    public static final Supplier<Item> SPIKEBLOSSOM = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.SPIKEBLOSSOM.get(), p))
            .creative(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("spikeblossom");
    public static final Supplier<Item> SPIKEBLOSSOM_SEEDS = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.SPIKEBLOSSOM_CROP.get(), p))
            .properties(p -> p.useItemDescriptionPrefix())
            .creative(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("spikeblossom_seeds");

    public static final Supplier<Item> SNAPDRAGON = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.SNAPDRAGON.get(), p))
            .creative(Items.PITCHER_PLANT, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("snapdragon");
    public static final Supplier<Item> SNAPDRAGON_POD = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.SNAPDRAGON_CROP.get(), p))
            .properties(p -> p.useItemDescriptionPrefix())
            .creative(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("snapdragon_pod");

    public static final Supplier<Item> LOTUS_LILY = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new PlaceOnWaterBlockItem(AABlocks.LOTUS_LILY.get(), p))
            .creative(Items.TORCHFLOWER, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("lotus_lily");
    public static final Supplier<Item> LOTUS_LILY_POD = new AAItem<>()
            .config(Config.SNIFFER_PLANTS)
            .factory(p -> new BlockItem(AABlocks.LOTUS_LILY_CROP.get(), p))
            .properties(p -> p.useItemDescriptionPrefix())
            .creative(Items.PITCHER_POD, CreativeModeTabs.NATURAL_BLOCKS, false)
            .make("lotus_lily_pod");

    // FOOD

    public static final Supplier<Item> FRIED_EGG = new AAItem<>()
            .config(Config.FRIED_EGG)
            .properties(p -> p
                    .delayedComponent(DataComponents.FOOD, provider -> Config.FRIED_EGG.get().food())
                    .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_FOOD)
            )
            .creative(Items.COOKED_RABBIT, CreativeModeTabs.FOOD_AND_DRINKS, false)
            .make("fried_egg");

    public static final Supplier<Item> BERRY_PIE = new AAItem<>()
            .config(Config.BERRY_PIE)
            .properties(p -> p
                    .delayedComponent(DataComponents.FOOD, provider -> Config.BERRY_PIE.get().food())
                    .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_FOOD)
            )
            .creative(Items.PUMPKIN_PIE, CreativeModeTabs.FOOD_AND_DRINKS, false)
            .make("berry_pie");

    public static final Supplier<Item> HONEYED_APPLE = new AAItem<>()
            .config(Config.HONEYED_APPLE)
            .properties(p -> p
                    .delayedComponent(DataComponents.FOOD, provider -> Config.HONEYED_APPLE.get().food())
                    .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_FOOD)
            )
            .creative(Items.APPLE, CreativeModeTabs.FOOD_AND_DRINKS, false)
            .make("honeyed_apple");

    public static final Supplier<Item> CHICKEN_NUGGET = new AAItem<>()
            .config(Config.CHICKEN_NUGGET)
            .properties(p -> p
                    .delayedComponent(DataComponents.FOOD, provider -> Config.CHICKEN_NUGGET.get().food())
                    .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_FOOD)
            )
            .creative(Items.ROTTEN_FLESH, CreativeModeTabs.FOOD_AND_DRINKS, false)
            .make("chicken_nugget");

    // OTHER

    public static final Supplier<WateringCanItem> WATERING_CAN = new AAItem<WateringCanItem>()
            .config(Config.WATERING_CAN)
            .factory(WateringCanItem::new)
            .properties(p -> p
                    .stacksTo(1)
                    .component(AAMisc.WATER_LEVEL_COMPONENT.get(), 0)
            )
            .creative(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .make("watering_can");

    public static final Supplier<WrenchItem> WRENCH_ITEM = new AAItem<WrenchItem>()
            .config(Config.WRENCH)
            .factory(WrenchItem::new)
            .properties(p -> p
                    .stacksTo(1)
                    .delayedComponent(DataComponents.MAX_DAMAGE, provider -> Config.WRENCH.get().durability())
                    .component(DataComponents.DAMAGE, 0)
            )
            .creative(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .creative(Items.TARGET, CreativeModeTabs.REDSTONE_BLOCKS, false)
            .make("wrench");

    public static final Supplier<Item> TRIDENT_SHARD = new AAItem<>()
            .config(Config.TRIDENT_SHARD)
            .creative(Items.PRISMARINE_CRYSTALS, CreativeModeTabs.INGREDIENTS, false)
            .make("trident_shard");

    public static final Supplier<Item> GLOW_STICK_ITEM = new AAItem<>()
            .config(Config.GLOW_STICK)
            .factory(p -> new GlowStickItem(AABlocks.GLOW_STICK_BLOCK.get(), p))
            .creative(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .make("glow_stick");

    public static final Supplier<Item> BAROMETER = new AAItem<>()
            .config(Config.BAROMETER)
            .creative(Items.CLOCK, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .make("barometer");

    public static final Supplier<PocketJukeboxItem> POCKET_JUKEBOX_ITEM = new AAItem<PocketJukeboxItem>()
            .config(Config.POCKET_JUKEBOX)
            .factory(PocketJukeboxItem::new)
            .properties(p -> p
                    .stacksTo(1)
                    .component(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT.withHidden(DataComponents.CONTAINER, true))
            )
            .creative(Items.SPYGLASS, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .make("pocket_jukebox");

    public static final Supplier<Item> ROSE_GOLD_INGOT = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .creative(Items.GOLD_INGOT, CreativeModeTabs.INGREDIENTS, false)
            .make("rose_gold_ingot");

    public static final Supplier<Item> MUSIC_DISC_0308 = new AAItem<>()
            .config(Config.MUSIC_DISC_0308)
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "0308")))
            )
            .creative(Items.MUSIC_DISC_WARD, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .make("music_disc_0308");

    public static final Supplier<Item> MUSIC_DISC_1007 = new AAItem<>()
            .config(Config.MUSIC_DISC_1007)
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "1007")))
            )
            .creative(Items.MUSIC_DISC_WARD, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .make("music_disc_1007");

    public static final Supplier<Item> MUSIC_DISC_1507 = new AAItem<>()
            .config(Config.MUSIC_DISC_1507)
            .properties(p -> p
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "1507")))
            )
            .creative(Items.MUSIC_DISC_WARD, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .make("music_disc_1507");

    // ROSE GOLD

    public static final Supplier<Item> ROSE_GOLD_HELMET = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .material(AAMaterials.ROSE_GOLD, ArmorType.HELMET)
            .creative(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT, false)
            .make("rose_gold_helmet");

    public static final Supplier<Item> ROSE_GOLD_CHESTPLATE = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .material(AAMaterials.ROSE_GOLD, ArmorType.CHESTPLATE)
            .creative(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT, false)
            .make("rose_gold_chestplate");

    public static final Supplier<Item> ROSE_GOLD_LEGGINGS = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .material(AAMaterials.ROSE_GOLD, ArmorType.LEGGINGS)
            .creative(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT, false)
            .make("rose_gold_leggings");

    public static final Supplier<Item> ROSE_GOLD_BOOTS = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .material(AAMaterials.ROSE_GOLD, ArmorType.BOOTS)
            .creative(Items.GOLDEN_BOOTS, CreativeModeTabs.COMBAT, false)
            .make("rose_gold_boots");

    public static final Supplier<Item> ROSE_GOLD_HORSE_ARMOR = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .material(AAMaterials.ROSE_GOLD, AnimalArmorType.HORSE)
            .properties(p -> p.stacksTo(1))
            .creative(Items.GOLDEN_HORSE_ARMOR, CreativeModeTabs.COMBAT, false)
            .make("rose_gold_horse_armor");

    public static final Supplier<Item> ROSE_GOLD_NAUTILUS_ARMOR = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .material(AAMaterials.ROSE_GOLD, AnimalArmorType.NAUTILUS)
            .properties(p -> p.stacksTo(1))
            .creative(Items.GOLDEN_NAUTILUS_ARMOR, CreativeModeTabs.COMBAT, false)
            .make("rose_gold_nautilus_armor");

    public static final Supplier<Item> ROSE_GOLD_SWORD = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .material(AAMaterials.ROSE_GOLD, ToolType.SWORD)
            .creative(Items.GOLDEN_SWORD, CreativeModeTabs.COMBAT, false)
            .make("rose_gold_sword");

    public static final Supplier<Item> ROSE_GOLD_SPEAR = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .material(AAMaterials.ROSE_GOLD, ToolType.SPEAR)
            .creative(Items.GOLDEN_SPEAR, CreativeModeTabs.COMBAT, false)
            .make("rose_gold_spear");

    public static final Supplier<Item> ROSE_GOLD_SHOVEL = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .factory(AAShovelItem::new)
            .material(AAMaterials.ROSE_GOLD, ToolType.SHOVEL)
            .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .make("rose_gold_shovel");

    public static final Supplier<Item> ROSE_GOLD_PICKAXE = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .material(AAMaterials.ROSE_GOLD, ToolType.PICKAXE)
            .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .make("rose_gold_pickaxe");

    public static final Supplier<Item> ROSE_GOLD_AXE = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .factory(AAAxeItem::new)
            .material(AAMaterials.ROSE_GOLD, ToolType.AXE)
            .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .creative(Items.GOLDEN_AXE, CreativeModeTabs.COMBAT, false)
            .make("rose_gold_axe");

    public static final Supplier<Item> ROSE_GOLD_HOE = new AAItem<>()
            .config(Config.ROSE_GOLD)
            .factory(AAHoeItem::new)
            .material(AAMaterials.ROSE_GOLD, ToolType.HOE)
            .creative(Items.GOLDEN_HOE, CreativeModeTabs.TOOLS_AND_UTILITIES, false)
            .make("rose_gold_hoe");

    // SUSPICIOUS DYE

    private static AAItem<SuspiciousDyeItem> suspiciousDye(DyeColor color) {
        return new AAItem<SuspiciousDyeItem>()
                .config(Config.SUSPICIOUS_DYE)
                .factory(p -> new SuspiciousDyeItem(color, p))
                .properties(p -> p
                        .stacksTo(1)
                        .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                        .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(color))
                )
                .creative(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS, true);
    }

    public static final Supplier<SuspiciousDyeItem> WHITE_SUSPICIOUS_DYE = suspiciousDye(DyeColor.WHITE).make("white_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> BROWN_SUSPICIOUS_DYE = suspiciousDye(DyeColor.BROWN).make("brown_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> RED_SUSPICIOUS_DYE = suspiciousDye(DyeColor.RED).make("red_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> ORANGE_SUSPICIOUS_DYE = suspiciousDye(DyeColor.ORANGE).make("orange_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> YELLOW_SUSPICIOUS_DYE = suspiciousDye(DyeColor.YELLOW).make("yellow_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> LIME_SUSPICIOUS_DYE = suspiciousDye(DyeColor.LIME).make("lime_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> GREEN_SUSPICIOUS_DYE = suspiciousDye(DyeColor.GREEN).make("green_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> CYAN_SUSPICIOUS_DYE = suspiciousDye(DyeColor.CYAN).make("cyan_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> LIGHT_BLUE_SUSPICIOUS_DYE = suspiciousDye(DyeColor.LIGHT_BLUE).make("light_blue_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> BLUE_SUSPICIOUS_DYE = suspiciousDye(DyeColor.BLUE).make("blue_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> PURPLE_SUSPICIOUS_DYE = suspiciousDye(DyeColor.PURPLE).make("purple_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> MAGENTA_SUSPICIOUS_DYE = suspiciousDye(DyeColor.MAGENTA).make("magenta_suspicious_dye");
    public static final Supplier<SuspiciousDyeItem> PINK_SUSPICIOUS_DYE = suspiciousDye(DyeColor.PINK).make("pink_suspicious_dye");

    // OTHER

    public static final Supplier<RopeArrowItem> ROPE_ARROW = new AAItem<RopeArrowItem>()
            .config(Config.ROPE)
            .factory(RopeArrowItem::new)
            .creative(Items.ARROW, CreativeModeTabs.COMBAT, false)
            .make("rope_arrow");

    // ALBUMS

    public static final AAItem<AlbumItem> ALBUM_TEMPLATE = new AAItem<AlbumItem>()
            .config(Config.ALBUM)
            .factory(AlbumItem::new)
            .creative(Items.MUSIC_DISC_13, CreativeModeTabs.TOOLS_AND_UTILITIES, true)
            .properties(p -> p
                    .stacksTo(1)
                    .component(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY)
            );
    public static final Supplier<AlbumItem> ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("album");
    public static final Supplier<AlbumItem> WHITE_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("white_album");
    public static final Supplier<AlbumItem> LIGHT_GRAY_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("light_gray_album");
    public static final Supplier<AlbumItem> GRAY_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("gray_album");
    public static final Supplier<AlbumItem> BLACK_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("black_album");
    public static final Supplier<AlbumItem> BROWN_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("brown_album");
    public static final Supplier<AlbumItem> RED_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("red_album");
    public static final Supplier<AlbumItem> ORANGE_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("orange_album");
    public static final Supplier<AlbumItem> YELLOW_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("yellow_album");
    public static final Supplier<AlbumItem> LIME_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("lime_album");
    public static final Supplier<AlbumItem> GREEN_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("green_album");
    public static final Supplier<AlbumItem> CYAN_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("cyan_album");
    public static final Supplier<AlbumItem> LIGHT_BLUE_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("light_blue_album");
    public static final Supplier<AlbumItem> BLUE_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("blue_album");
    public static final Supplier<AlbumItem> PURPLE_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("purple_album");
    public static final Supplier<AlbumItem> MAGENTA_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("magenta_album");
    public static final Supplier<AlbumItem> PINK_ALBUM = new AAItem<>(ALBUM_TEMPLATE).make("pink_album");


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
