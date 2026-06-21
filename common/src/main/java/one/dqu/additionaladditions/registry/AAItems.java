package one.dqu.additionaladditions.registry;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.feature.album.AlbumContent;
import one.dqu.additionaladditions.feature.album.AlbumItem;
import one.dqu.additionaladditions.feature.barometer.BarometerContent;
import one.dqu.additionaladditions.feature.copper_patina.CopperPatinaContent;
import one.dqu.additionaladditions.feature.copper_patina.CopperPatinaItem;
import one.dqu.additionaladditions.feature.food.FoodContent;
import one.dqu.additionaladditions.feature.glow_stick.GlowStickContent;
import one.dqu.additionaladditions.feature.music_disc.MusicDiscContent;
import one.dqu.additionaladditions.feature.pocket_jukebox.PocketJukeboxContent;
import one.dqu.additionaladditions.feature.pocket_jukebox.PocketJukeboxItem;
import one.dqu.additionaladditions.feature.redstone_lamp.RedstoneLampContent;
import one.dqu.additionaladditions.feature.rope.RopeArrowItem;
import one.dqu.additionaladditions.feature.rope.RopeContent;
import one.dqu.additionaladditions.feature.rose_gold.RoseGoldContent;
import one.dqu.additionaladditions.feature.sniffer_plants.SnifferPlantsContent;
import one.dqu.additionaladditions.feature.suspicious_dye.SuspiciousDyeContent;
import one.dqu.additionaladditions.feature.suspicious_dye.SuspiciousDyeItem;
import one.dqu.additionaladditions.feature.trident.TridentShardContent;
import one.dqu.additionaladditions.feature.watering_can.WateringCanContent;
import one.dqu.additionaladditions.feature.watering_can.WateringCanItem;
import one.dqu.additionaladditions.feature.wrench.WrenchContent;
import one.dqu.additionaladditions.feature.wrench.WrenchItem;

import java.util.*;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class AAItems {
    private static final Map<Identifier, Set<Supplier<? extends Item>>> CONFIG_TO_ITEM = new HashMap<>();

    public static Collection<Supplier<? extends Item>> fromConfigProperty(ConfigProperty<?> configProperty) {
        return CONFIG_TO_ITEM.getOrDefault(configProperty.path(), Set.of());
    }

    public static void addConfigItem(ConfigProperty<?> config, Supplier<? extends Item> item) {
        CONFIG_TO_ITEM.computeIfAbsent(config.path(), k -> new HashSet<>()).add(item);
    }

    public static Identifier configPathFor(Item item) {
        for (Map.Entry<Identifier, Set<Supplier<? extends Item>>> e : CONFIG_TO_ITEM.entrySet()) {
            for (Supplier<? extends Item> supplier : e.getValue()) {
                if (supplier.get() == item) return e.getKey();
            }
        }
        return null;
    }

    // BLOCKS

    public static final Supplier<BlockItem> ROPE = RopeContent.rope();

    public static final Supplier<BlockItem> TINTED_REDSTONE_LAMP = RedstoneLampContent.tintedRedstoneLampItem();

    public static final Supplier<CopperPatinaItem> COPPER_PATINA = CopperPatinaContent.copperPatina();

    public static final Supplier<BlockItem> PATINA_BLOCK = CopperPatinaContent.patinaBlockItem();

    public static final Supplier<BlockItem> ROSE_GOLD_BLOCK = RoseGoldContent.roseGoldBlockItem();

    // SNIFFER PLANTS

    public static final Supplier<Item> COTTONSHIVER = SnifferPlantsContent.cottonshiverItem();
    public static final Supplier<Item> COTTONSHIVER_POD = SnifferPlantsContent.cottonshiverPod();

    public static final Supplier<Item> MUDFLOWER = SnifferPlantsContent.mudflowerItem();
    public static final Supplier<Item> MUDFLOWER_SEEDS = SnifferPlantsContent.mudflowerSeeds();

    public static final Supplier<Item> CRIMSON_BLOSSOM = SnifferPlantsContent.crimsonBlossomItem();
    public static final Supplier<Item> CRIMSON_BLOSSOM_SEEDS = SnifferPlantsContent.crimsonBlossomSeeds();

    public static final Supplier<Item> AMBER_BLOSSOM = SnifferPlantsContent.amberBlossomItem();
    public static final Supplier<Item> AMBER_BLOSSOM_SEEDS = SnifferPlantsContent.amberBlossomSeeds();

    public static final Supplier<Item> BULBUS = SnifferPlantsContent.bulbusItem();
    public static final Supplier<Item> BULBUS_POD = SnifferPlantsContent.bulbusPod();

    public static final Supplier<Item> SAWTOOTH_FERN = SnifferPlantsContent.sawtoothFernItem();
    public static final Supplier<Item> SAWTOOTH_FERN_FIDDLEHEAD = SnifferPlantsContent.sawtoothFernFiddlehead();

    public static final Supplier<Item> FROSTLEAF = SnifferPlantsContent.frostleafItem();
    public static final Supplier<Item> FROSTLEAF_POD = SnifferPlantsContent.frostleafPod();

    public static final Supplier<Item> WISTERIA = SnifferPlantsContent.wisteriaItem();
    public static final Supplier<Item> WISTERIA_VINES = SnifferPlantsContent.wisteriaVines();

    public static final Supplier<Item> SPIKEBLOSSOM = SnifferPlantsContent.spikeblossomItem();
    public static final Supplier<Item> SPIKEBLOSSOM_SEEDS = SnifferPlantsContent.spikeblossomSeeds();

    public static final Supplier<Item> SNAPDRAGON = SnifferPlantsContent.snapdragonItem();
    public static final Supplier<Item> SNAPDRAGON_POD = SnifferPlantsContent.snapdragonPod();

    public static final Supplier<Item> LOTUS_LILY = SnifferPlantsContent.lotusLilyItem();
    public static final Supplier<Item> LOTUS_LILY_POD = SnifferPlantsContent.lotusLilyPod();

    // FOOD

    public static final Supplier<Item> FRIED_EGG = FoodContent.friedEgg();
    public static final Supplier<Item> BERRY_PIE = FoodContent.berryPie();
    public static final Supplier<Item> HONEYED_APPLE = FoodContent.honeyedApple();
    public static final Supplier<Item> CHICKEN_NUGGET = FoodContent.chickenNugget();

    // OTHER

    public static final Supplier<WateringCanItem> WATERING_CAN = WateringCanContent.wateringCan();

    public static final Supplier<WrenchItem> WRENCH_ITEM = WrenchContent.wrench();

    public static final Supplier<Item> TRIDENT_SHARD = TridentShardContent.tridentShard();

    public static final Supplier<Item> GLOW_STICK_ITEM = GlowStickContent.glowStick();

    public static final Supplier<Item> BAROMETER = BarometerContent.barometer();

    public static final Supplier<PocketJukeboxItem> POCKET_JUKEBOX_ITEM = PocketJukeboxContent.pocketJukebox();

    public static final Supplier<Item> ROSE_GOLD_INGOT = RoseGoldContent.roseGoldIngot();

    public static final Supplier<Item> MUSIC_DISC_0308 = MusicDiscContent.disc0308();
    public static final Supplier<Item> MUSIC_DISC_1007 = MusicDiscContent.disc1007();
    public static final Supplier<Item> MUSIC_DISC_1507 = MusicDiscContent.disc1507();

    // ROSE GOLD

    public static final Supplier<Item> ROSE_GOLD_HELMET = RoseGoldContent.helmet();
    public static final Supplier<Item> ROSE_GOLD_CHESTPLATE = RoseGoldContent.chestplate();
    public static final Supplier<Item> ROSE_GOLD_LEGGINGS = RoseGoldContent.leggings();
    public static final Supplier<Item> ROSE_GOLD_BOOTS = RoseGoldContent.boots();
    public static final Supplier<Item> ROSE_GOLD_HORSE_ARMOR = RoseGoldContent.horseArmor();
    public static final Supplier<Item> ROSE_GOLD_NAUTILUS_ARMOR = RoseGoldContent.nautilusArmor();
    public static final Supplier<Item> ROSE_GOLD_SWORD = RoseGoldContent.sword();
    public static final Supplier<Item> ROSE_GOLD_SPEAR = RoseGoldContent.spear();
    public static final Supplier<Item> ROSE_GOLD_SHOVEL = RoseGoldContent.shovel();
    public static final Supplier<Item> ROSE_GOLD_PICKAXE = RoseGoldContent.pickaxe();
    public static final Supplier<Item> ROSE_GOLD_AXE = RoseGoldContent.axe();
    public static final Supplier<Item> ROSE_GOLD_HOE = RoseGoldContent.hoe();

    // SUSPICIOUS DYE

    public static final Supplier<SuspiciousDyeItem> WHITE_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.WHITE);
    public static final Supplier<SuspiciousDyeItem> BROWN_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.BROWN);
    public static final Supplier<SuspiciousDyeItem> RED_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.RED);
    public static final Supplier<SuspiciousDyeItem> ORANGE_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.ORANGE);
    public static final Supplier<SuspiciousDyeItem> YELLOW_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.YELLOW);
    public static final Supplier<SuspiciousDyeItem> LIME_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.LIME);
    public static final Supplier<SuspiciousDyeItem> GREEN_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.GREEN);
    public static final Supplier<SuspiciousDyeItem> CYAN_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.CYAN);
    public static final Supplier<SuspiciousDyeItem> LIGHT_BLUE_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.LIGHT_BLUE);
    public static final Supplier<SuspiciousDyeItem> BLUE_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.BLUE);
    public static final Supplier<SuspiciousDyeItem> PURPLE_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.PURPLE);
    public static final Supplier<SuspiciousDyeItem> MAGENTA_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.MAGENTA);
    public static final Supplier<SuspiciousDyeItem> PINK_SUSPICIOUS_DYE = SuspiciousDyeContent.suspiciousDye(DyeColor.PINK);

    // OTHER

    public static final Supplier<RopeArrowItem> ROPE_ARROW = RopeContent.ropeArrow();

    // ALBUMS

    public static final Supplier<AlbumItem> ALBUM = AlbumContent.album("album");
    public static final Supplier<AlbumItem> WHITE_ALBUM = AlbumContent.album("white_album", DyeColor.WHITE);
    public static final Supplier<AlbumItem> LIGHT_GRAY_ALBUM = AlbumContent.album("light_gray_album", DyeColor.LIGHT_GRAY);
    public static final Supplier<AlbumItem> GRAY_ALBUM = AlbumContent.album("gray_album", DyeColor.GRAY);
    public static final Supplier<AlbumItem> BLACK_ALBUM = AlbumContent.album("black_album", DyeColor.BLACK);
    public static final Supplier<AlbumItem> BROWN_ALBUM = AlbumContent.album("brown_album", DyeColor.BROWN);
    public static final Supplier<AlbumItem> RED_ALBUM = AlbumContent.album("red_album", DyeColor.RED);
    public static final Supplier<AlbumItem> ORANGE_ALBUM = AlbumContent.album("orange_album", DyeColor.ORANGE);
    public static final Supplier<AlbumItem> YELLOW_ALBUM = AlbumContent.album("yellow_album", DyeColor.YELLOW);
    public static final Supplier<AlbumItem> LIME_ALBUM = AlbumContent.album("lime_album", DyeColor.LIME);
    public static final Supplier<AlbumItem> GREEN_ALBUM = AlbumContent.album("green_album", DyeColor.GREEN);
    public static final Supplier<AlbumItem> CYAN_ALBUM = AlbumContent.album("cyan_album", DyeColor.CYAN);
    public static final Supplier<AlbumItem> LIGHT_BLUE_ALBUM = AlbumContent.album("light_blue_album", DyeColor.LIGHT_BLUE);
    public static final Supplier<AlbumItem> BLUE_ALBUM = AlbumContent.album("blue_album", DyeColor.BLUE);
    public static final Supplier<AlbumItem> PURPLE_ALBUM = AlbumContent.album("purple_album", DyeColor.PURPLE);
    public static final Supplier<AlbumItem> MAGENTA_ALBUM = AlbumContent.album("magenta_album", DyeColor.MAGENTA);
    public static final Supplier<AlbumItem> PINK_ALBUM = AlbumContent.album("pink_album", DyeColor.PINK);


    public static void registerAll() {

    }
}
