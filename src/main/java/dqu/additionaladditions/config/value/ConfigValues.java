package dqu.additionaladditions.config.value;

public enum ConfigValues {
    FOOD("FoodItems", 1, new BooleanConfigValue(true)),
    WATERING_CAN("WateringCan", 1, new BooleanConfigValue(true)),
    ROSE_GOLD("RoseGold", 1, new BooleanConfigValue(true)),
    ROPES("Ropes", 1, new BooleanConfigValue(true)),
    ENCHANTMENT_PRECISION("EnchantmentPrecision", 1, new BooleanConfigValue(true)),
    ENCHANTMENT_SPEED("EnchantmentSpeed", 3, new BooleanConfigValue(true)),
    WRENCH("Wrench", 1, new BooleanConfigValue(true)),
    COPPER_PATINA("CopperPatina", 1, new BooleanConfigValue(true)),
    AMETHYST_LAMP("AmethystLamp", 1, new BooleanConfigValue(true)),
    CROSSBOWS("Crossbows", 1, new BooleanConfigValue(true)),
    TRIDENT_SHARD("TridentShard", 2, new BooleanConfigValue(true)),
    GLOW_STICK("GlowStick", 2, new BooleanConfigValue(true)),
    GILDED_NETHERITE("GildedNetherite", 3, new BooleanConfigValue(true)),
    DEPTH_METER("DepthMeter", 3, new BooleanConfigValue(true)),
    POTIONS("Potions", 3, new BooleanConfigValue(true)),
    MYSTERIOUS_BUNDLE("MysteriousBundle", 3, new BooleanConfigValue(true)),
    COMPOSTABLE_ROTTEN_FLESH("CompostableRottenFlesh", 3, new BooleanConfigValue(true)),
    MUSIC_DISCS("MusicDiscs", 4, new BooleanConfigValue(true)),
    NOTE_BLOCK_AMETHYST_SOUNDS("NoteBlockAmethystSounds", 5, new BooleanConfigValue(true)),
    SHIPWRECK_SPYGLASS_LOOT("ShipwreckSpyglassLoot", 5, new BooleanConfigValue(true)),
    POCKET_JUKEBOX("PocketJukebox", 5, new BooleanConfigValue(true)),
    CHICKEN_NUGGET("ChickenNugget", 5, new BooleanConfigValue(true)),
    POWERED_RAILS_COPPER_RECIPE("PoweredRailsCopperRecipe", 5, new BooleanConfigValue(true)),
    GOLD_RING("GoldRing", 5, new BooleanConfigValue(true)),
    PLAYER_GLOW("PlayerGlow", 6, new BooleanConfigValue(true));

    private final String value;
    private final int version;
    private final ConfigValue configValue;

    ConfigValues(String value, int version, ConfigValue configValue) {
        this.value = value;
        this.version = version;
        this.configValue = configValue;
    }

    public String getValue() {
        return value;
    }

    public int getVersion() {
        return version;
    }

    public ConfigValue getConfigValue() {
        return configValue;
    }

    public ConfigValueType getType() {
        return configValue.getType();
    }
}
