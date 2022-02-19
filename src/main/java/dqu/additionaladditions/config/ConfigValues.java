package dqu.additionaladditions.config;

import dqu.additionaladditions.config.value.BooleanConfigValue;
import dqu.additionaladditions.config.value.ConfigValueType;
import dqu.additionaladditions.config.value.IntegerConfigValue;
import dqu.additionaladditions.config.value.ListConfigValue;

public enum ConfigValues {
    FOOD(1, new ConfigProperty("FoodItems", new ListConfigValue()
            .put(new ConfigProperty("FriedEgg", ConfigValueType.TRUE))
            .put(new ConfigProperty("BerryPie", ConfigValueType.TRUE))
            .put(new ConfigProperty("HoneyedApple", ConfigValueType.TRUE))
    )),
    WATERING_CAN(1, new ConfigProperty("WateringCan", ConfigValueType.TRUE)),
    ROSE_GOLD( 1, new ConfigProperty("RoseGold", ConfigValueType.TRUE)),
    ROPES(1, new ConfigProperty("Ropes", ConfigValueType.TRUE)),
    ENCHANTMENT_PRECISION(1, new ConfigProperty("EnchantmentPrecision", ConfigValueType.TRUE)),
    ENCHANTMENT_SPEED(3, new ConfigProperty("EnchantmentSpeed", ConfigValueType.TRUE)),
    WRENCH(1, new ConfigProperty("Wrench", ConfigValueType.TRUE)),
    COPPER_PATINA(1, new ConfigProperty("CopperPatina", ConfigValueType.TRUE)),
    AMETHYST_LAMP(1, new ConfigProperty("AmethystLamp", ConfigValueType.TRUE)),
    CROSSBOWS(1, new ConfigProperty("Crossbows", ConfigValueType.TRUE)),
    TRIDENT_SHARD(2, new ConfigProperty("TridentShard", ConfigValueType.TRUE)),
    GLOW_STICK(2, new ConfigProperty("GlowStick", ConfigValueType.TRUE)),
    GILDED_NETHERITE(3, new ConfigProperty("GildedNetherite", ConfigValueType.TRUE)),
    DEPTH_METER(3, new ConfigProperty("DepthMeter", ConfigValueType.TRUE)),
    POTIONS(3, new ConfigProperty("Potions", ConfigValueType.TRUE)),
    MYSTERIOUS_BUNDLE(3, new ConfigProperty("MysteriousBundle", ConfigValueType.TRUE)),
    COMPOSTABLE_ROTTEN_FLESH(3, new ConfigProperty("CompostableRottenFlesh", ConfigValueType.TRUE)),
    MUSIC_DISCS(4, new ConfigProperty("MusicDiscs", ConfigValueType.TRUE)),
    NOTE_BLOCK_AMETHYST_SOUNDS(5, new ConfigProperty("NoteBlockAmethystSounds", ConfigValueType.TRUE)),
    SHIPWRECK_SPYGLASS_LOOT(5, new ConfigProperty("ShipwreckSpyglassLoot", ConfigValueType.TRUE)),
    POCKET_JUKEBOX(5, new ConfigProperty("PocketJukebox", ConfigValueType.TRUE)),
    CHICKEN_NUGGET(5, new ConfigProperty("ChickenNugget", ConfigValueType.TRUE)),
    POWERED_RAILS_COPPER_RECIPE(5, new ConfigProperty("PoweredRailsCopperRecipe", ConfigValueType.TRUE)),
    GOLD_RING(5, new ConfigProperty("GoldRing", ConfigValueType.TRUE)),
    GLOW_BERRY_GLOW(6, new ConfigProperty("GlowBerryEatGlow", new ListConfigValue()
            .put(new ConfigProperty("enabled", ConfigValueType.TRUE))
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
