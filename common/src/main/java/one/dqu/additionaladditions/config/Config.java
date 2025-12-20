package one.dqu.additionaladditions.config;

import net.minecraft.world.food.FoodProperties;
import one.dqu.additionaladditions.config.datafixer.ConfigFixerUpper;
import one.dqu.additionaladditions.config.type.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Config {
    public static void init() { /* forces static init */ }

    public static final ConfigProperty<VersionConfig> VERSION = new ConfigProperty<>(
            "version", VersionConfig.CODEC,
            new VersionConfig(ConfigFixerUpper.CURRENT_VERSION)
    );

    public static final ConfigProperty<FoodConfig> FRIED_EGG = new ConfigProperty<>(
            "fried_egg", FoodConfig.CODEC,
            new FoodConfig(true, new FoodProperties.Builder()
                    .nutrition(6).saturationModifier(0.867f).build()
            )
    );

    public static final ConfigProperty<FoodConfig> BERRY_PIE = new ConfigProperty<>(
            "berry_pie", FoodConfig.CODEC,
            new FoodConfig(true, new FoodProperties.Builder()
                    .nutrition(8).saturationModifier(0.6f).build()
            )
    );

    public static final ConfigProperty<FoodConfig> HONEYED_APPLE = new ConfigProperty<>(
            "honeyed_apple", FoodConfig.CODEC,
            new FoodConfig(true, new FoodProperties.Builder()
                    .nutrition(8).saturationModifier(1.6f).build()
            )
    );

    public static final ConfigProperty<FoodConfig> CHICKEN_NUGGET = new ConfigProperty<>(
            "chicken_nugget", FoodConfig.CODEC,
            new FoodConfig(true, new FoodProperties.Builder()
                    .nutrition(6).saturationModifier(0.9f).build()
            )
    );

    public static final ConfigProperty<WateringCanConfig> WATERING_CAN = new ConfigProperty<>(
            "watering_can", WateringCanConfig.CODEC,
            new WateringCanConfig(true, 10)
    );

    public static final ConfigProperty<DamageableItemConfig> WRENCH = new ConfigProperty<>(
            "wrench", DamageableItemConfig.CODEC,
            new DamageableItemConfig(true, 256)
    );

    public static final ConfigProperty<DamageableItemConfig> CROSSBOW_WITH_SPYGLASS = new ConfigProperty<>(
            "crossbow_with_spyglass", DamageableItemConfig.CODEC,
            new DamageableItemConfig(true, 350)
    );

    public static final ConfigProperty<FeatureConfig> TRIDENT_SHARD = new ConfigProperty<>(
            "trident_shard", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<FeatureConfig> GLOW_STICK = new ConfigProperty<>(
            "glow_stick", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<BarometerConfig> BAROMETER = new ConfigProperty<>(
            "barometer", BarometerConfig.CODEC,
            new BarometerConfig(true, false)
    );

    public static final ConfigProperty<FeatureConfig> POCKET_JUKEBOX = new ConfigProperty<>(
            "pocket_jukebox", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<FeatureConfig> ALBUM = new ConfigProperty<>(
            "album", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<FeatureConfig> SUSPICIOUS_DYES = new ConfigProperty<>(
            "suspicious_dyes", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<FeatureConfig> ROSE_GOLD = new ConfigProperty<>(
            "rose_gold/enabled", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<FeatureConfig> MUSIC_DISC_0308 = new ConfigProperty<>(
            "music_discs/0308", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<FeatureConfig> MUSIC_DISC_1007 = new ConfigProperty<>(
            "music_discs/1007", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<FeatureConfig> MUSIC_DISC_1507 = new ConfigProperty<>(
            "music_discs/1507", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<TintedRedstoneLampConfig> TINTED_REDSTONE_LAMP = new ConfigProperty<>(
            "tinted_redstone_lamp", TintedRedstoneLampConfig.CODEC,
            new TintedRedstoneLampConfig(true, 1.0f, 16)
    );

    public static final ConfigProperty<FeatureConfig> COPPER_PATINA = new ConfigProperty<>(
            "copper_patina", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<FeatureConfig> ROPE = new ConfigProperty<>(
            "rope", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<FeatureConfig> SHIPWRECK_SPYGLASS_LOOT = new ConfigProperty<>(
            "shipwreck_spyglass_loot", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static final ConfigProperty<FeatureConfig> POWERED_RAILS_COPPER_RECIPE = new ConfigProperty<>(
            "powered_rails_copper_recipe", FeatureConfig.CODEC,
            new FeatureConfig(true)
    );

    public static List<ConfigProperty<?>> getAllConfigProperties() {
        return Arrays.stream(Config.class.getDeclaredFields())
                .filter(field -> field.getType().equals(ConfigProperty.class))
                .map(field -> {
                    try {
                        return (ConfigProperty<?>) field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
