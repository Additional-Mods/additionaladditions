package dqu.additionaladditions.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dqu.additionaladditions.AdditionalAdditions;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Config {
    public static final int VERSION = 5;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String PATH = FabricLoader.getInstance().getConfigDir().resolve("additional-additions-config.json").toString();
    private static final File DBFILE = new File(PATH);
    public static final HashMap<String, Integer> properties = new HashMap<>();
    public static boolean initialized = false;
    private static JsonObject db = new JsonObject();

    private static String format(String message) {
        return String.format("[%s] %s", AdditionalAdditions.namespace, message);
    }

    public static void init() {
        // First value is the key name, second value is the config version where key was added.
        initialized = true;
        properties.put("FoodItems", 1);
        properties.put("WateringCan", 1);
        properties.put("RoseGold", 1);
        properties.put("Ropes", 1);
        properties.put("EnchantmentPrecision", 1);
        properties.put("EnchantmentSpeed", 3);
        properties.put("Wrench", 1);
        properties.put("CopperPatina", 1);
        properties.put("AmethystLamp", 1);
        properties.put("Crossbows", 1);
        properties.put("TridentShard", 2);
        properties.put("GlowStick", 2);
        properties.put("GildedNetherite", 3);
        properties.put("DepthMeter", 3);
        properties.put("Potions", 3);
        properties.put("MysteriousBundle", 3);
        properties.put("CompostableRottenFlesh", 3);
        properties.put("MusicDiscs", 4);
        properties.put("NoteBlockAmethystSounds", 5);
        properties.put("ShipwreckSpyglassLoot", 5);
        properties.put("PocketJukebox", 5);
        properties.put("ChickenNugget", 5);
    }

    public static void load() {
        if (!DBFILE.exists()) {
            db.addProperty("version", VERSION);
            for (String property : properties.keySet()) db.addProperty(property, true);
            save();
        }

        try {
            BufferedReader bufferedReader = Files.newReader(DBFILE, StandardCharsets.UTF_8);
            db = GSON.fromJson(bufferedReader, JsonObject.class);
        } catch (Exception e) {
            AdditionalAdditions.LOGGER.error(e.getMessage());
            AdditionalAdditions.LOGGER.error(format("Unable to load configuration file!"));
        }

        if (db.get("version").getAsInt() != VERSION) convert(db.get("version").getAsInt());
    }

    private static void save() {
        try {
            BufferedWriter bufferedWriter = Files.newWriter(DBFILE, StandardCharsets.UTF_8);
            String json = GSON.toJson(db);
            bufferedWriter.write(json);
            bufferedWriter.close();
        } catch (Exception e) {
            AdditionalAdditions.LOGGER.error(e.getMessage());
            AdditionalAdditions.LOGGER.error(format("Unable to save configuration file!"));
        }
    }

    public static boolean get(String key) {
        if (!initialized) {
            init();
            load();
        }
        return db.get(key).getAsBoolean();
    }

    public static void set(String key, boolean value) {
        if (!initialized) {
            init();
            load();
        }
        db.addProperty(key, value);
        save();
    }

    private static void convert(int version) {
        for (String property : properties.keySet()) {
            if (properties.get(property) > version || db.get(property) == null) {
                db.addProperty(property, true);
            }
        }
        db.addProperty("version", VERSION);

        AdditionalAdditions.LOGGER.info(format("Converted outdated config."));
        save();
    }
}
