package dqu.additionaladditions.config;

import dqu.additionaladditions.config.value.ConfigValueType;
import dqu.additionaladditions.config.value.FloatConfigValue;
import dqu.additionaladditions.config.value.IntegerConfigValue;
import dqu.additionaladditions.config.value.ListConfigValue;

public enum ConfigValues {
    // Version is the version the property was last changed in
    FOOD(6, new ConfigProperty("FoodItems", new ListConfigValue()
            .put(new ConfigProperty("FriedEgg"))
            .put(new ConfigProperty("BerryPie"))
            .put(new ConfigProperty("HoneyedApple"))
    )),
    WATERING_CAN(6, new ConfigProperty("WateringCan")),
    ROSE_GOLD( 6, new ConfigProperty("RoseGold")),
    ROPES(6, new ConfigProperty("Ropes")),
    ENCHANTMENT_PRECISION(6, new ConfigProperty("EnchantmentPrecision")),
    ENCHANTMENT_SPEED(6, new ConfigProperty("EnchantmentSpeed")),
    WRENCH(6, new ConfigProperty("Wrench")),
    COPPER_PATINA(6, new ConfigProperty("CopperPatina")),
    AMETHYST_LAMP(7, new ConfigProperty("AmethystLamp", new ListConfigValue()
            .put(new ConfigProperty("enabled"))
            .put(new ConfigProperty("despawnChance", new FloatConfigValue(0.5f)))
    )),
    CROSSBOWS(6, new ConfigProperty("Crossbows")),
    TRIDENT_SHARD(6, new ConfigProperty("TridentShard")),
    GLOW_STICK(6, new ConfigProperty("GlowStick")),
    GILDED_NETHERITE(8, new ConfigProperty("GildedNetherite", new ListConfigValue()
            .put(new ConfigProperty("enabled"))
            .put(new ConfigProperty("fireResistancePerPiece", new FloatConfigValue(2.5f)))
    )),
    DEPTH_METER(6, new ConfigProperty("DepthMeter", new ListConfigValue()
            .put(new ConfigProperty("enabled"))
            .put(new ConfigProperty("displayElevationAlways", ConfigValueType.FALSE))
    )),
    MYSTERIOUS_BUNDLE(6, new ConfigProperty("MysteriousBundle")),
    COMPOSTABLE_ROTTEN_FLESH(6, new ConfigProperty("CompostableRottenFlesh")),
    MUSIC_DISCS(6, new ConfigProperty("MusicDiscs")),
    NOTE_BLOCK_AMETHYST_SOUNDS(6, new ConfigProperty("NoteBlockAmethystSounds")),
    SHIPWRECK_SPYGLASS_LOOT(6, new ConfigProperty("ShipwreckSpyglassLoot")),
    POCKET_JUKEBOX(6, new ConfigProperty("PocketJukebox")),
    CHICKEN_NUGGET(6, new ConfigProperty("ChickenNugget")),
    POWERED_RAILS_COPPER_RECIPE(6, new ConfigProperty("PoweredRailsCopperRecipe")),
    GLOW_BERRY_GLOW(6, new ConfigProperty("GlowBerryEatGlow", new ListConfigValue()
            .put(new ConfigProperty("enabled"))
            .put(new ConfigProperty("duration", new IntegerConfigValue(5)))
    )),
    HASTE_POTIONS(6, new ConfigProperty("HastePotions"));

    private final int version;
    private final ConfigProperty configProperty;

    ConfigValues(int version, ConfigProperty configProperty) {
        this.version = version;
        this.configProperty = configProperty;
    }

    public ConfigProperty getProperty() {
        return configProperty;
    }

    public int getVersion() {
        return version;
    }

    public ConfigValueType getType() {
        return configProperty.value().getType();
    }

    public static ConfigValues getByName(String name) {
        for (ConfigValues value : values()) {
            if (value.getProperty().key().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
