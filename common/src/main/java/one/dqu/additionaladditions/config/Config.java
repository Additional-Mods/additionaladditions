package one.dqu.additionaladditions.config;

import one.dqu.additionaladditions.config.type.ChanceConfig;
import one.dqu.additionaladditions.config.type.DurabilityConfig;
import one.dqu.additionaladditions.config.type.FeatureFlagConfig;
import one.dqu.additionaladditions.config.type.FoodConfig;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class Config {
    protected static final Map<ResourceLocation, ConfigProperty<?>> PROPERTIES = new HashMap<>();

    // Food

    public static final ConfigProperty<FeatureFlagConfig> FRIED_EGG =
            new ConfigProperty<>("fried_egg/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    public static final ConfigProperty<FoodConfig> FRIED_EGG_FOOD =
            new ConfigProperty<>("fried_egg/food", FoodConfig.CODEC, new FoodConfig(6, 5.2f)) {};

    public static final ConfigProperty<FeatureFlagConfig> BERRY_PIE =
            new ConfigProperty<>("berry_pie/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    public static final ConfigProperty<FoodConfig> BERRY_PIE_FOOD =
            new ConfigProperty<>("berry_pie/food", FoodConfig.CODEC, new FoodConfig(8, 4.8f)) {};

    public static final ConfigProperty<FeatureFlagConfig> HONEYED_APPLE =
            new ConfigProperty<>("honeyed_apple/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    public static final ConfigProperty<FoodConfig> HONEYED_APPLE_FOOD =
            new ConfigProperty<>("honeyed_apple/food", FoodConfig.CODEC, new FoodConfig(8, 12.8f)) {};

    // Chicken Nugget

    public static final ConfigProperty<FeatureFlagConfig> CHICKEN_NUGGET =
            new ConfigProperty<>("chicken_nugget/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    public static final ConfigProperty<FoodConfig> CHICKEN_NUGGET_FOOD =
            new ConfigProperty<>("chicken_nugget/food", FoodConfig.CODEC, new FoodConfig(6, 5.4f)) {};

    // Watering Can

    public static final ConfigProperty<FeatureFlagConfig> WATERING_CAN =
            new ConfigProperty<>("watering_can/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    public static final ConfigProperty<DurabilityConfig> WATERING_CAN_DURABILITY =
            new ConfigProperty<>("watering_can/durability", DurabilityConfig.CODEC, new DurabilityConfig(101)) {};

    // Wrench

    public static final ConfigProperty<FeatureFlagConfig> WRENCH =
            new ConfigProperty<>("wrench/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    public static final ConfigProperty<DurabilityConfig> WRENCH_DURABILITY =
            new ConfigProperty<>("wrench/durability", DurabilityConfig.CODEC, new DurabilityConfig(256)) {};

    // Crossbow with Spyglass

    public static final ConfigProperty<FeatureFlagConfig> CROSSBOW_WITH_SPYGLASS =
            new ConfigProperty<>("crossbow_with_spyglass/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    public static final ConfigProperty<DurabilityConfig> CROSSBOW_WITH_SPYGLASS_DURABILITY =
            new ConfigProperty<>("crossbow_with_spyglass/durability", DurabilityConfig.CODEC, new DurabilityConfig(350)) {};

    // Trident Shard

    public static final ConfigProperty<FeatureFlagConfig> TRIDENT_SHARD =
            new ConfigProperty<>("trident_shard/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    // Glow Stick

    public static final ConfigProperty<FeatureFlagConfig> GLOW_STICK =
            new ConfigProperty<>("glow_stick/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    // Depth Meter

    public static final ConfigProperty<FeatureFlagConfig> DEPTH_METER =
            new ConfigProperty<>("depth_meter/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    public static final ConfigProperty<FeatureFlagConfig> DEPTH_METER_DISPLAY_ALWAYS =
            new ConfigProperty<>("depth_meter/display_elevation_always", FeatureFlagConfig.CODEC, new FeatureFlagConfig(false)) {};

    // Mysterious Bundle

    public static final ConfigProperty<FeatureFlagConfig> MYSTERIOUS_BUNDLE =
            new ConfigProperty<>("mysterious_bundle/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    // Pocket Jukebox

    public static final ConfigProperty<FeatureFlagConfig> POCKET_JUKEBOX =
            new ConfigProperty<>("pocket_jukebox/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    // Rose Gold

    public static final ConfigProperty<FeatureFlagConfig> ROSE_GOLD =
            new ConfigProperty<>("rose_gold/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    // Music Discs

    public static final ConfigProperty<FeatureFlagConfig> MUSIC_DISC_0308 =
            new ConfigProperty<>("music_discs/0308", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    public static final ConfigProperty<FeatureFlagConfig> MUSIC_DISC_1007 =
            new ConfigProperty<>("music_discs/1007", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    public static final ConfigProperty<FeatureFlagConfig> MUSIC_DISC_1507 =
            new ConfigProperty<>("music_discs/1507", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    // Amethyst Lamp

    public static final ConfigProperty<FeatureFlagConfig> AMETHYST_LAMP =
            new ConfigProperty<>("amethyst_lamp/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    public static final ConfigProperty<ChanceConfig> AMETHYST_LAMP_DESPAWN_CHANCE =
            new ConfigProperty<>("amethyst_lamp/despawn_chance", ChanceConfig.CODEC, new ChanceConfig(1.0f)) {};

    // Copper Patina

    public static final ConfigProperty<FeatureFlagConfig> COPPER_PATINA =
            new ConfigProperty<>("copper_patina/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    // Rope

    public static final ConfigProperty<FeatureFlagConfig> ROPE =
            new ConfigProperty<>("rope/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    // Compostable Rotten Flesh

    public static final ConfigProperty<FeatureFlagConfig> COMPOSTABLE_ROTTEN_FLESH =
            new ConfigProperty<>("compostable_rotten_flesh/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    // Shipwreck Spyglass Loot

    public static final ConfigProperty<FeatureFlagConfig> SHIPWRECK_SPYGLASS_LOOT =
            new ConfigProperty<>("shipwreck_spyglass_loot/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};

    // Powered Rails Copper Recipe

    public static final ConfigProperty<FeatureFlagConfig> POWERED_RAILS_COPPER_RECIPE =
            new ConfigProperty<>("powered_rails_copper_recipe/flag", FeatureFlagConfig.CODEC, new FeatureFlagConfig(true)) {};
}
