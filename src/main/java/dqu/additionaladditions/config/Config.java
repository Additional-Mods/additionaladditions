package dqu.additionaladditions.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.value.ConfigValueType;
import dqu.additionaladditions.config.value.ListConfigValue;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class Config {
    public static final int VERSION = 6;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String PATH = FabricLoader.getInstance().getConfigDir().resolve("additional-additions-config.json").toString();
    private static final File DBFILE = new File(PATH);
    public static boolean initialized = false;
    private static JsonObject db = new JsonObject();

    private static String format(String message) {
        return String.format("[%s] %s", AdditionalAdditions.namespace, message);
    }

    private static void addPropertyTo(JsonObject object, ConfigProperty property) {
        switch (property.value().getType()) {
            case BOOLEAN -> object.addProperty(property.key(), (Boolean) property.value().getValue());
            case STRING -> object.addProperty(property.key(), (String) property.value().getValue());
            case INTEGER -> object.addProperty(property.key(), (Integer) property.value().getValue());
            case LIST -> {
                JsonObject newObject = new JsonObject();
                for (ConfigProperty i : (List<ConfigProperty>) property.value().getValue()) {
                    addPropertyTo(newObject, i);
                }
                object.add(property.key(), newObject);
            }
        }
    }

    public static void load() {
        if (!DBFILE.exists()) {
            db.addProperty("version", VERSION);
            for (ConfigValues value : ConfigValues.values()) {
                addPropertyTo(db, value.getProperty());
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

    public static boolean getBool(ConfigValues value, String key) {
        return Boolean.TRUE.equals(get(value, key));
    }

    public static <T> T get(ConfigValues value) {
        if (!initialized) {
            load();
        }

        switch (value.getType()) {
            case STRING -> {
                return (T) db.get(value.getProperty().key()).getAsString();
            }
            case BOOLEAN -> {
                return (T) (Boolean) db.get(value.getProperty().key()).getAsBoolean();
            }
            case INTEGER -> {
                return (T) (Integer) db.get(value.getProperty().key()).getAsInt();
            }
            default -> {
                return null;
            }
        }
    }

    public static <T> T get(ConfigValues value, String key) {
        if (!initialized) {
            load();
        }

        if (value.getType() == ConfigValueType.LIST) {
            ListConfigValue configValue = (ListConfigValue) value.getProperty().value();
            JsonObject object = db.get(value.getProperty().key()).getAsJsonObject();
            ConfigProperty keyProperty = configValue.get(key);
            switch (keyProperty.value().getType()) {
                case STRING -> {
                    return (T) object.get(key).getAsString();
                }
                case BOOLEAN -> {
                    return (T) (Boolean) object.get(key).getAsBoolean();
                }
                case INTEGER -> {
                    return (T) (Integer) object.get(key).getAsInt();
                }
                case LIST -> {
                    throw new IllegalArgumentException("Cannot create lists inside lists!");
                }
                default -> {
                    return null;
                }
            }
        }

        return null;
    }

    public static void set(ConfigValues value, Object property) {
        if (!initialized) {
            load();
        }

        switch (value.getType()) {
            case STRING -> {
                if (property instanceof String)
                    db.addProperty(value.getProperty().key(), (String) property);
            }
            case BOOLEAN -> {
                if (property instanceof Boolean)
                    db.addProperty(value.getProperty().key(), (Boolean) property);
            }
            case INTEGER -> {
                if (property instanceof Integer) {
                    db.addProperty(value.getProperty().key(), (Integer) property);
                }
            }
        }

        save();
    }

    private static void convert(int version) {
        for (ConfigValues value : ConfigValues.values()) {
            if (value.getProperty().key().equals(ConfigValues.FOOD.getProperty().key())) {
                if (version < 6) {
                    db.remove(value.getProperty().key());
                }
            }
        }
        db.addProperty("version", VERSION);

        AdditionalAdditions.LOGGER.info(format("Converted outdated config."));
        save();
    }

    private static void repair() {
        int repaired = 0;
        for (ConfigValues value : ConfigValues.values()) {
            if (db.get(value.getProperty().key()) == null) {
                addPropertyTo(db, value.getProperty());
                repaired++;
            }
        }
        if (repaired > 0) {
            AdditionalAdditions.LOGGER.info(format("Repaired " + repaired + " config properties"));
        }

        save();
    }
}
