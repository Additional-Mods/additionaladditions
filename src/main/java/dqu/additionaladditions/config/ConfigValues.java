package dqu.additionaladditions.config;

import dqu.additionaladditions.config.value.BooleanConfigValue;
import dqu.additionaladditions.config.value.ConfigValueType;
import dqu.additionaladditions.config.value.IntegerConfigValue;
import dqu.additionaladditions.config.value.ListConfigValue;

public enum ConfigValues {
    FOOD(1, new ConfigProperty("FoodItems", new BooleanConfigValue(true))),
    WATERING_CAN(1, new ConfigProperty("WateringCan", new BooleanConfigValue(true))),
    ROSE_GOLD( 1, new ConfigProperty("RoseGold", new BooleanConfigValue(true))),
    ROPES(1, new ConfigProperty("Ropes", new BooleanConfigValue(true))),
    ENCHANTMENT_PRECISION(1, new ConfigProperty("EnchantmentPrecision", new BooleanConfigValue(true))),
    ENCHANTMENT_SPEED(3, new ConfigProperty("EnchantmentSpeed", new BooleanConfigValue(true))),
    WRENCH(1, new ConfigProperty("Wrench", new BooleanConfigValue(true))),
    COPPER_PATINA(1, new ConfigProperty("CopperPatina", new BooleanConfigValue(true))),
    AMETHYST_LAMP(1, new ConfigProperty("AmethystLamp", new BooleanConfigValue(true))),
    CROSSBOWS(1, new ConfigProperty("Crossbows", new BooleanConfigValue(true))),
    TRIDENT_SHARD(2, new ConfigProperty("TridentShard", new BooleanConfigValue(true))),
    GLOW_STICK(2, new ConfigProperty("GlowStick", new BooleanConfigValue(true))),
    GILDED_NETHERITE(3, new ConfigProperty("GildedNetherite", new BooleanConfigValue(true))),
    DEPTH_METER(3, new ConfigProperty("DepthMeter", new BooleanConfigValue(true))),
    POTIONS(3, new ConfigProperty("Potions", new BooleanConfigValue(true))),
    MYSTERIOUS_BUNDLE(3, new ConfigProperty("MysteriousBundle", new BooleanConfigValue(true))),
    COMPOSTABLE_ROTTEN_FLESH(3, new ConfigProperty("CompostableRottenFlesh", new BooleanConfigValue(true))),
    MUSIC_DISCS(4, new ConfigProperty("MusicDiscs", new BooleanConfigValue(true))),
    NOTE_BLOCK_AMETHYST_SOUNDS(5, new ConfigProperty("NoteBlockAmethystSounds", new BooleanConfigValue(true))),
    SHIPWRECK_SPYGLASS_LOOT(5, new ConfigProperty("ShipwreckSpyglassLoot", new BooleanConfigValue(true))),
    POCKET_JUKEBOX(5, new ConfigProperty("PocketJukebox", new BooleanConfigValue(true))),
    CHICKEN_NUGGET(5, new ConfigProperty("ChickenNugget", new BooleanConfigValue(true))),
    POWERED_RAILS_COPPER_RECIPE(5, new ConfigProperty("PoweredRailsCopperRecipe", new BooleanConfigValue(true))),
    GOLD_RING(5, new ConfigProperty("GoldRing", new BooleanConfigValue(true))),
    GLOW_BERRY_GLOW(6, new ConfigProperty("GlowBerryEatGlow", new ListConfigValue()
            .put(new ConfigProperty("enabled", new BooleanConfigValue(true)))
            .put(new ConfigProperty("duration", new IntegerConfigValue(5)))
    ));

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
