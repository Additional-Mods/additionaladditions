package dqu.additionaladditions.config;

import dqu.additionaladditions.config.value.ConfigValueType;
import dqu.additionaladditions.config.value.IntegerConfigValue;
import dqu.additionaladditions.config.value.ListConfigValue;

public enum ConfigValues {
    FOOD(1, new ConfigProperty("FoodItems", new ListConfigValue()
            .put(new ConfigProperty("FriedEgg"))
            .put(new ConfigProperty("BerryPie"))
            .put(new ConfigProperty("HoneyedApple"))
    )),
    WATERING_CAN(1, new ConfigProperty("WateringCan")),
    ROSE_GOLD( 1, new ConfigProperty("RoseGold")),
    ROPES(1, new ConfigProperty("Ropes")),
    ENCHANTMENT_PRECISION(1, new ConfigProperty("EnchantmentPrecision")),
    ENCHANTMENT_SPEED(3, new ConfigProperty("EnchantmentSpeed")),
    WRENCH(1, new ConfigProperty("Wrench")),
    COPPER_PATINA(1, new ConfigProperty("CopperPatina")),
    AMETHYST_LAMP(1, new ConfigProperty("AmethystLamp")),
    CROSSBOWS(1, new ConfigProperty("Crossbows")),
    TRIDENT_SHARD(2, new ConfigProperty("TridentShard")),
    GLOW_STICK(2, new ConfigProperty("GlowStick")),
    GILDED_NETHERITE(3, new ConfigProperty("GildedNetherite")),
    DEPTH_METER(3, new ConfigProperty("DepthMeter", new ListConfigValue()
            .put(new ConfigProperty("enabled"))
            .put(new ConfigProperty("displayElevationAlways", ConfigValueType.FALSE))
    )),
    MYSTERIOUS_BUNDLE(3, new ConfigProperty("MysteriousBundle")),
    COMPOSTABLE_ROTTEN_FLESH(3, new ConfigProperty("CompostableRottenFlesh")),
    MUSIC_DISCS(4, new ConfigProperty("MusicDiscs")),
    NOTE_BLOCK_AMETHYST_SOUNDS(5, new ConfigProperty("NoteBlockAmethystSounds")),
    SHIPWRECK_SPYGLASS_LOOT(5, new ConfigProperty("ShipwreckSpyglassLoot")),
    POCKET_JUKEBOX(5, new ConfigProperty("PocketJukebox")),
    CHICKEN_NUGGET(5, new ConfigProperty("ChickenNugget")),
    POWERED_RAILS_COPPER_RECIPE(5, new ConfigProperty("PoweredRailsCopperRecipe")),
    GOLD_RING(5, new ConfigProperty("GoldRing")),
    GLOW_BERRY_GLOW(6, new ConfigProperty("GlowBerryEatGlow", new ListConfigValue()
            .put(new ConfigProperty("enabled"))
            .put(new ConfigProperty("duration", new IntegerConfigValue(5)))
    )),
    HASTE_POTIONS(6, new ConfigProperty("HastePotions")),
    BUNDLE_RECIPE(6, new ConfigProperty("BundleRecipe"));

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
}
