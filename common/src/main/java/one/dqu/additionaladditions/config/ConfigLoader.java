package one.dqu.additionaladditions.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import dev.architectury.injectables.annotations.ExpectPlatform;
import one.dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.resources.ResourceLocation;
import one.dqu.additionaladditions.config.datafixer.ConfigFixerUpper;
import one.dqu.additionaladditions.config.type.VersionConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader {
    private static final String PATH = getConfigDirectory().resolve(AdditionalAdditions.NAMESPACE).toString();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * For each config property, adds its JSON to a map, then passes the map to the apply method.
     * If the file does not exist, creates it with the default values.
     */
    public static void load() {
        Config.VERSION.get().version(); // force static init

        try {
            Files.createDirectories(Paths.get(PATH));
        } catch (IOException e) {
            AdditionalAdditions.LOGGER.error("Failed to create config directory: {}", PATH, e);
            return;
        }

        Map<ResourceLocation, JsonElement> configFiles = new HashMap<>();

        for (ConfigProperty<?> property : ConfigProperty.getAll()) {
            ResourceLocation location = property.path();
            Path path = Paths.get(PATH).resolve(location.getPath() + ".json");

            try {
                Files.createDirectories(path.getParent());
                if (!Files.exists(path)) {
                    createDefault(property, path);
                }

                String jsonString = Files.readString(path);

                try {
                    JsonElement jsonElement = GSON.fromJson(jsonString, JsonElement.class);
                    if (jsonElement == null) {
                        throw new JsonSyntaxException("File is empty or null");
                    }
                    configFiles.put(location, jsonElement);
                } catch (JsonSyntaxException e) {
                    AdditionalAdditions.LOGGER.error("[{}] Malformed JSON in config file for property {} at path {}: {}", AdditionalAdditions.NAMESPACE, location, path, e);
                }
            } catch (IOException e) {
                AdditionalAdditions.LOGGER.error("[{}] Failed to create config file for property {} at path {}: {}", AdditionalAdditions.NAMESPACE, location, path, e);
            }
        }

        // This should always be true because the part above creates non-existent files
        assert configFiles.get(Config.VERSION.path()) != null : "Config version file is missing";

        // Load saved version
        apply(Map.of(Config.VERSION.path(), configFiles.get(Config.VERSION.path())));

        // Remove version from the list so it doesn't get passed into datafixer
        configFiles.remove(Config.VERSION.path());

        // DFU
        if (Config.VERSION.get().version() != ConfigFixerUpper.CURRENT_VERSION) {
            for (Map.Entry<ResourceLocation, JsonElement> entry : configFiles.entrySet()) {
                ResourceLocation location = entry.getKey();
                JsonElement json = entry.getValue();

                JsonElement result = ConfigFixerUpper.INSTANCE.update(
                        ConfigProperty.getByPath(location).typeReference(),
                        new Dynamic<>(JsonOps.INSTANCE, json),
                        Config.VERSION.get().version(),
                        ConfigFixerUpper.CURRENT_VERSION
                ).getValue();

                // put datafixed json
                configFiles.put(location, result);
            }

            // Put latest version into map to save it
            Config.VERSION.set(new VersionConfig(ConfigFixerUpper.CURRENT_VERSION));
            configFiles.put(Config.VERSION.path(), Config.VERSION.serialize().getOrThrow());

            // Write all files
            for (Map.Entry<ResourceLocation, JsonElement> entry : configFiles.entrySet()) {
                ResourceLocation location = entry.getKey();
                JsonElement json = entry.getValue();
                Path path = Paths.get(PATH).resolve(location.getPath() + ".json");

                try {
                    String updatedJsonString = GSON.toJson(json);
                    Files.writeString(path, updatedJsonString);
                } catch (IOException e) {
                    AdditionalAdditions.LOGGER.error("[{}] Failed to write updated config file for property {} at path {}: {}", AdditionalAdditions.NAMESPACE, location, path, e);
                }
            }

            AdditionalAdditions.LOGGER.info("[{}] Updated config files from version {} to {}", AdditionalAdditions.NAMESPACE, Config.VERSION.get().version(), ConfigFixerUpper.CURRENT_VERSION);
        }

        apply(configFiles);

        AdditionalAdditions.LOGGER.info("[{}] Loaded {} config files", AdditionalAdditions.NAMESPACE, configFiles.size());
    }

    /**
     * Applies the given config files to their respective ConfigProperty instances.
     */
    public static void apply(Map<ResourceLocation, JsonElement> configFiles) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : configFiles.entrySet()) {
            ResourceLocation location = entry.getKey();
            JsonElement json = entry.getValue();
            ConfigProperty<?> property = ConfigProperty.getByPath(location);

            parseAndSet(property, json);
        }
    }

    private static <T> void parseAndSet(ConfigProperty<T> property, JsonElement json) {
        DataResult<T> result = property.codec().parse(JsonOps.INSTANCE, json);
        result.resultOrPartial((error) -> {
            AdditionalAdditions.LOGGER.error("[{}] Failed to deserialize config property {}: {}", AdditionalAdditions.NAMESPACE, property.path(), error);
        }).ifPresent(property::set);
    }

    private static <T> void createDefault(ConfigProperty<T> property, Path path) {
        try {
            Files.createDirectories(path.getParent());

            T defaultValue = property.get();
            String defaultJson = GSON.toJson(property.codec().encodeStart(JsonOps.INSTANCE, defaultValue).getOrThrow());

            Files.writeString(path, defaultJson);
        } catch (IOException e) {
            AdditionalAdditions.LOGGER.error("[{}] Failed to create default config file for property {} at path {}: {}", AdditionalAdditions.NAMESPACE, property.path(), path, e);
        }
    }

    @ExpectPlatform
    private static Path getConfigDirectory() {
        throw new AssertionError();
    }
}
