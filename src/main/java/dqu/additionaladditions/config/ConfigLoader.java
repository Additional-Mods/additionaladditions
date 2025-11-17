package dqu.additionaladditions.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import dqu.additionaladditions.AdditionalAdditions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader {
    private static final String PATH = FabricLoader.getInstance().getConfigDir().resolve(AdditionalAdditions.namespace).toString();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * For each config property, adds its JSON to a map, then passes the map to the apply method.
     * If the file does not exist, creates it with the default values.
     */
    public static void load() {
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
                JsonElement jsonElement = GSON.fromJson(jsonString, JsonElement.class);
                configFiles.put(location, jsonElement);
            } catch (IOException e) {
                AdditionalAdditions.LOGGER.error("[{}] Failed to create config file for property {} at path {}: {}", AdditionalAdditions.namespace, location, path, e);
            }
        }

        apply(configFiles);

        AdditionalAdditions.LOGGER.info("[{}] Loaded {} config files", AdditionalAdditions.namespace, configFiles.size());
    }

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
            AdditionalAdditions.LOGGER.error("[{}] Failed to deserialize config property {}: {}", AdditionalAdditions.namespace, property.path(), error);
        }).ifPresent(property::set);
    }

    private static <T> void createDefault(ConfigProperty<T> property, Path path) {
        try {
            Files.createDirectories(path.getParent());

            T defaultValue = property.get();
            String defaultJson = GSON.toJson(property.codec().encodeStart(JsonOps.INSTANCE, defaultValue).getOrThrow());

            Files.writeString(path, defaultJson);
        } catch (IOException e) {
            AdditionalAdditions.LOGGER.error("[{}] Failed to create default config file for property {} at path {}: {}", AdditionalAdditions.namespace, property.path(), path, e);
        }
    }
}
