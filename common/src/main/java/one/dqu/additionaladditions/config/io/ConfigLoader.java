package one.dqu.additionaladditions.config.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.food.FoodProperties;
import one.dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.resources.ResourceLocation;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.datafixer.ConfigFileRelocator;
import one.dqu.additionaladditions.config.datafixer.ConfigFixerUpper;
import one.dqu.additionaladditions.config.type.VersionConfig;
import one.dqu.additionaladditions.config.type.unit.FoodUnitConfig;
import one.dqu.additionaladditions.util.Registrar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigLoader {
    private static final String PATH = getConfigDirectory().resolve(AdditionalAdditions.NAMESPACE).toString();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setLenient().create();

    /**
     * Handles config loading.
     * In order: init config -> create config directory -> load config version
     *           -> run relocator -> read or create config files
     *           -> datafix if version is outdated -> apply config values
     */
    public static void load() {
        AdditionalAdditions.LOGGER.info("[{}] Loading config files...", AdditionalAdditions.NAMESPACE);

        // Register config types so the comments can be found by the writer
        Json5Writer.registerType(FoodProperties.class, FoodUnitConfig.class);

        Path directory = Paths.get(PATH);
        Config.init();

        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            AdditionalAdditions.LOGGER.error("[{}] Failed to create config directory: {}", AdditionalAdditions.NAMESPACE, PATH, e);
            return;
        }

        // Load config version
        var configFiles = readFiles(directory, List.of(Config.VERSION));
        if (configFiles.isEmpty()) {
            AdditionalAdditions.LOGGER.error("[{}] Unknown config version! Skipping config load and using defaults.", AdditionalAdditions.NAMESPACE);
            return;
        }
        apply(configFiles);


        // This handles moving / renaming / removing files between versions
        if (Config.VERSION.get().version() != ConfigFixerUpper.CURRENT_VERSION) {
            AdditionalAdditions.LOGGER.info("[{}] Config version outdated (found {}, current {}). Updating...", AdditionalAdditions.NAMESPACE, Config.VERSION.get().version(), ConfigFixerUpper.CURRENT_VERSION);
            ConfigFileRelocator.update(directory, Config.VERSION.get().version());
        }

        configFiles = readFiles(directory, ConfigProperty.getAll());

        // This fixes actual file contents using DFU
        if (Config.VERSION.get().version() != ConfigFixerUpper.CURRENT_VERSION) {
            datafix(configFiles);
            AdditionalAdditions.LOGGER.info("[{}] Finished updating config.", AdditionalAdditions.NAMESPACE);
        }

        apply(configFiles);

        AdditionalAdditions.LOGGER.info("[{}] Config load complete.", AdditionalAdditions.NAMESPACE);
    }

    /**
     * Applies the given config files to their respective ConfigProperty instances.
     */
    public static void apply(Map<ResourceLocation, JsonElement> configFiles) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : configFiles.entrySet()) {
            ResourceLocation location = entry.getKey();
            JsonElement json = entry.getValue();
            ConfigProperty<?> property = ConfigProperty.getByPath(location);
            if (property == null) {
                AdditionalAdditions.LOGGER.warn("[{}] Unknown config property {}", AdditionalAdditions.NAMESPACE, location);
                continue;
            }

            parseAndSet(property, json);
        }
    }

    /**
     * Updates config property format to the latest version.
     */
    private static void datafix(Map<ResourceLocation, JsonElement> configFiles) {
        int version = Config.VERSION.get().version();

        // Remove version from the map as we don't need to datafix it
        configFiles.remove(Config.VERSION.path());

        // Run DFU
        for (Map.Entry<ResourceLocation, JsonElement> entry : configFiles.entrySet().stream().toList()) {
            ResourceLocation location = entry.getKey();
            JsonElement json = entry.getValue();

            JsonElement result;
            try {
                result = ConfigFixerUpper.INSTANCE.update(
                        ConfigProperty.getByPath(location).typeReference(),
                        new Dynamic<>(JsonOps.INSTANCE, json),
                        version, ConfigFixerUpper.CURRENT_VERSION
                ).getValue();
            } catch (Exception e) {
                AdditionalAdditions.LOGGER.error("[{}] Failed to datafix config file for property {}: {}", AdditionalAdditions.NAMESPACE, location, e);
                continue;
            }

            // put datafixed json
            configFiles.put(location, result);
        }

        // Put current version
        configFiles.put(
                Config.VERSION.path(),
                VersionConfig.CODEC.encodeStart(JsonOps.INSTANCE, new VersionConfig(ConfigFixerUpper.CURRENT_VERSION)).getOrThrow()
        );

        // Write all files
        for (Map.Entry<ResourceLocation, JsonElement> entry : configFiles.entrySet()) {
            ResourceLocation location = entry.getKey();
            JsonElement json = entry.getValue();
            Path path = Paths.get(PATH).resolve(location.getPath() + ".json5");

            try {
                ConfigProperty<?> property = ConfigProperty.getByPath(location);
                Class<?> configClass = property != null ? property.get().getClass() : null;
                String updatedJsonString = Json5Writer.write(json, configClass);
                Files.writeString(path, updatedJsonString);
            } catch (IOException e) {
                AdditionalAdditions.LOGGER.error("[{}] Failed to write updated config file for property {} at path {}: {}", AdditionalAdditions.NAMESPACE, location, path, e);
            }
        }
    }

    /**
     * Reads all given config properties from given directory and serializes them into JSON.
     * If a file does not exist, creates it with default values after the registries are ready.
     */
    private static Map<ResourceLocation, JsonElement> readFiles(Path directory, List<ConfigProperty<?>> properties) {
        Map<ResourceLocation, JsonElement> configFiles = new HashMap<>();
        for (ConfigProperty<?> property : properties) {
            Path path = directory.resolve(property.path().getPath() + ".json5");

            try {
                if (!Files.exists(path)) {
                    Registrar.defer(() -> createDefault(property, path));
                    continue;
                }

                JsonElement jsonElement = GSON.fromJson(Files.readString(path), JsonElement.class);
                if (jsonElement == null) {
                    throw new JsonSyntaxException("File is empty or null");
                }
                configFiles.put(property.path(), jsonElement);
            } catch (IOException e) {
                AdditionalAdditions.LOGGER.error("[{}] Failed to create config file for property {} at path {}: {}", AdditionalAdditions.NAMESPACE, property.path(), path, e);
            } catch (JsonSyntaxException e) {
                AdditionalAdditions.LOGGER.error("[{}] Malformed JSON in config file for property {} at path {}: {}", AdditionalAdditions.NAMESPACE, property.path(), path, e);
            }
        }
        return configFiles;
    }

    public static VersionConfig readVersion(Map<ResourceLocation, JsonElement> configFiles) {
        JsonElement json = configFiles.get(Config.VERSION.path());
        if (json == null) {
            return new VersionConfig(-1);
        }
        return Config.VERSION.deserialize(json).result().orElse(new VersionConfig(-1));
    }

    private static <T> void parseAndSet(ConfigProperty<T> property, JsonElement json) {
        property.deserialize(json).resultOrPartial((error) -> {
            AdditionalAdditions.LOGGER.error("[{}] Failed to deserialize config property {}: {}", AdditionalAdditions.NAMESPACE, property.path(), error);
        }).ifPresent(property::set);
    }

    private static <T> void createDefault(ConfigProperty<T> property, Path path) {
        try {
            Files.createDirectories(path.getParent());

            T defaultValue = property.get();
            JsonElement json = property.codec().encodeStart(JsonOps.INSTANCE, defaultValue).getOrThrow();
            String defaultJson = Json5Writer.write(json, defaultValue.getClass());

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
