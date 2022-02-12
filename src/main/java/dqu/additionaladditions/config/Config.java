package dqu.additionaladditions.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.value.ConfigValues;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class Config {
    public static final int VERSION = 5;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String PATH = FabricLoader.getInstance().getConfigDir().resolve("additional-additions-config.json").toString();
    private static final File DBFILE = new File(PATH);
    public static boolean initialized = false;
    private static JsonObject db = new JsonObject();

    private static String format(String message) {
        return String.format("[%s] %s", AdditionalAdditions.namespace, message);
    }

    public static void load() {
        if (!DBFILE.exists()) {
            db.addProperty("version", VERSION);
            for (ConfigValues value : ConfigValues.values()) {
                switch (value.getType()) {
                    case BOOLEAN -> db.addProperty(value.getValue(), (Boolean) value.getConfigValue().getValue());
                    case STRING -> db.addProperty(value.getValue(), (String) value.getConfigValue().getValue());
                }
            }

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
        repair();

        initialized = true;
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

    public static boolean getBool(ConfigValues value) {
        return Boolean.TRUE.equals(get(value));
    }

    public static <T> T get(ConfigValues value) {
        if (!initialized) {
            load();
        }

        switch (value.getType()) {
            case STRING -> {
                return (T) getString(value);
            }
            case BOOLEAN -> {
                return (T) getBoolean(value);
            }
            default -> {
                return null;
            }
        }
    }

    private static Boolean getBoolean(ConfigValues value) {
        return db.get(value.getValue()).getAsBoolean();
    }

    private static String getString(ConfigValues value) {
        return db.get(value.getValue()).getAsString();
    }

    public static void set(ConfigValues value, Object property) {
        if (!initialized) {
            load();
        }

        switch (value.getType()) {
            case STRING -> {
                if (property instanceof String)
                    db.addProperty(value.getValue(), (String) property);
            }
            case BOOLEAN -> {
                if (property instanceof Boolean)
                    db.addProperty(value.getValue(), (Boolean) property);
            }
        }

        save();
    }

    private static void convert(int version) {
        for (ConfigValues value : ConfigValues.values()) {
            if (value.getVersion() > version || db.get(value.getValue()) == null) {
                db.addProperty(value.getValue(), true);
            }
        }
        db.addProperty("version", VERSION);

        AdditionalAdditions.LOGGER.info(format("Converted outdated config."));
        save();
    }

    private static void repair() {
        int repaired = 0;
        for (ConfigValues value : ConfigValues.values()) {
            if (db.get(value.getValue()) == null) {
                db.addProperty(value.getValue(), true);
                repaired++;
            }
        }
        if (repaired > 0) {
            AdditionalAdditions.LOGGER.info(format("Repaired " + repaired + " config properties"));
        }
        save();
    }
}
