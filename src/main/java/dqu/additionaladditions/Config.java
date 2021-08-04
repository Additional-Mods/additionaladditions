package dqu.additionaladditions;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class Config {
    public static final int VERSION = 3;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String PATH = FabricLoader.getInstance().getConfigDir().resolve("additional-additions-config.json").toString();
    private static final File DBFILE = new File(PATH);
    private static JsonObject db = new JsonObject();

    public static void load() {
        if (!DBFILE.exists()) {
            db.addProperty("version", VERSION);
            db.addProperty("FoodItems", true);
            db.addProperty("WateringCan", true);
            db.addProperty("RoseGold", true);
            db.addProperty("Ropes", true);
            db.addProperty("EnchantmentPrecision", true);
            db.addProperty("EnchantmentSpeed", true);
            db.addProperty("Wrench", true);
            db.addProperty("CopperPatina", true);
            db.addProperty("AmethystLamp", true);
            db.addProperty("Crossbows", true);
            db.addProperty("TridentShard", true);
            db.addProperty("GlowStick", true);
            db.addProperty("GildedNetherite", true);
            db.addProperty("DepthMeter", true);
            db.addProperty("Potions", true);
            db.addProperty("MysteryBag", true);
            save();
        }

        try {
            BufferedReader bufferedReader = Files.newReader(DBFILE, StandardCharsets.UTF_8);
            db = GSON.fromJson(bufferedReader, JsonObject.class);
        } catch (Exception e) {
            AdditionalAdditions.LOGGER.error(e.getMessage());
            AdditionalAdditions.LOGGER.error("[Additional Additions] Unable to load configuration file!");
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
            AdditionalAdditions.LOGGER.error("[Additional Additions] Unable to save configuration file!");
        }
    }

    public static boolean get(String key) {
        if (!DBFILE.exists()) {
            AdditionalAdditions.LOGGER.error("[Additional Additions] Unable to get key as file doesn't exist?!");
            return false;
        }
        return db.get(key).getAsBoolean();
    }

    private static void convert(int version) {
        switch (version) {
            case 1 -> {
                db.addProperty("TridentShard", true);
                db.addProperty("GlowStick", true);
                db.addProperty("version", 2);
            }
            case 2 -> {
                db.addProperty("MysteryBag", true);
                db.addProperty("Potions", true);
                db.addProperty("GildedNetherite", true);
                db.addProperty("DepthMeter", true);
                db.addProperty("EnchantmentSpeed", true);
                db.addProperty("version", 3);
            }
        }
        AdditionalAdditions.LOGGER.info("[Additional Additions] Converted outdated config.");
        save();
        if (version+1 < VERSION) convert(version+1);
    }
}
