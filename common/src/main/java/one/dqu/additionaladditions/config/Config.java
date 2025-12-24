package one.dqu.additionaladditions.config;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.crafting.Ingredient;
import one.dqu.additionaladditions.config.datafixer.ConfigFixerUpper;
import one.dqu.additionaladditions.config.type.*;
import one.dqu.additionaladditions.registry.AAItems;

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

    public static final ConfigProperty<ArmorMaterialConfig> ROSE_GOLD_ARMOR_MATERIAL = new ConfigProperty<>(
            "rose_gold/armor_material", ArmorMaterialConfig.CODEC,
            new ArmorMaterialConfig(0f, 0f, 17, SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(AAItems.ROSE_GOLD_INGOT.get()))
    );

    public static final ConfigProperty<ArmorItemConfig> ROSE_GOLD_HELMET = new ConfigProperty<>(
            "rose_gold/helmet", ArmorItemConfig.CODEC,
            new ArmorItemConfig(2, 312)
    );

    public static final ConfigProperty<ArmorItemConfig> ROSE_GOLD_CHESTPLATE = new ConfigProperty<>(
            "rose_gold/chestplate", ArmorItemConfig.CODEC,
            new ArmorItemConfig(7, 384)
    );

    public static final ConfigProperty<ArmorItemConfig> ROSE_GOLD_LEGGINGS = new ConfigProperty<>(
            "rose_gold/leggings", ArmorItemConfig.CODEC,
            new ArmorItemConfig(6, 360)
    );

    public static final ConfigProperty<ArmorItemConfig> ROSE_GOLD_BOOTS = new ConfigProperty<>(
            "rose_gold/boots", ArmorItemConfig.CODEC,
            new ArmorItemConfig(2, 264)
    );

    public static final ConfigProperty<BodyArmorItemConfig> ROSE_GOLD_BODY_ARMOR = new ConfigProperty<>(
            "rose_gold/body_armor", BodyArmorItemConfig.CODEC,
            new BodyArmorItemConfig(9)
    );

    public static final ConfigProperty<SwordItemConfig> ROSE_GOLD_SWORD = new ConfigProperty<>(
            "rose_gold/sword", SwordItemConfig.CODEC,
            new SwordItemConfig(1.6f, 7, 900)
    );

    public static final ConfigProperty<ToolItemConfig> ROSE_GOLD_SHOVEL = new ConfigProperty<>(
            "rose_gold/shovel", ToolItemConfig.CODEC,
            new ToolItemConfig(1f, 4.5f, 900)
    );

    public static final ConfigProperty<ToolItemConfig> ROSE_GOLD_PICKAXE = new ConfigProperty<>(
            "rose_gold/pickaxe", ToolItemConfig.CODEC,
            new ToolItemConfig(1.2f, 4f, 900)
    );

    public static final ConfigProperty<ToolItemConfig> ROSE_GOLD_AXE = new ConfigProperty<>(
            "rose_gold/axe", ToolItemConfig.CODEC,
            new ToolItemConfig(0.9f, 9f, 900)
    );

    public static final ConfigProperty<ToolItemConfig> ROSE_GOLD_HOE = new ConfigProperty<>(
            "rose_gold/hoe", ToolItemConfig.CODEC,
            new ToolItemConfig(3f, 1f, 900)
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
            new TintedRedstoneLampConfig(true, 1.0f, 14)
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
}
