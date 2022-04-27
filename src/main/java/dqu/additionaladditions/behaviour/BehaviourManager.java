package dqu.additionaladditions.behaviour;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.ConfigProperty;
import dqu.additionaladditions.config.value.*;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.*;

public class BehaviourManager extends SimpleJsonResourceReloadListener implements IdentifiableResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private Map<ResourceLocation, ConfigProperty> behaviours = ImmutableMap.of();
    public static BehaviourManager INSTANCE;
    public static boolean didLoad = false;

    public BehaviourManager() {
        super(GSON, "behaviour");
    }

    @Override
    public String getName() {
        return "behaviour";
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        HashMap<ResourceLocation, ConfigProperty> hashMap = new HashMap<>();
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            ResourceLocation resourceLocation = (ResourceLocation) entry.getKey();

            if (!resourceLocation.getNamespace().equals(AdditionalAdditions.namespace)) {
                continue;
            }

            JsonElement jsonElement = (JsonElement) entry.getValue();
            List<ConfigProperty> properties = loadBehaviour(resourceLocation, jsonElement);
            ListConfigValue listConfigValue = new ListConfigValue().putAll(properties);
            hashMap.put(resourceLocation, new ConfigProperty(resourceLocation.getPath(), listConfigValue));
        }

        this.behaviours = ImmutableMap.copyOf(hashMap);
        AdditionalAdditions.LOGGER.info("[{}] Loaded {} behaviours", AdditionalAdditions.namespace, this.behaviours.size());
        didLoad = true;
    }

    private static List<ConfigProperty> loadBehaviour(ResourceLocation resourceLocation, JsonElement jsonElement) {
        JsonObject object = jsonElement.getAsJsonObject();
        List<ConfigProperty> properties = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            BehaviourValues.getByName(key).ifPresentOrElse(behaviour -> {
                switch (behaviour.getType()) {
                    case STRING -> properties.add(new ConfigProperty(key, new StringConfigValue(value.getAsString())));
                    case BOOLEAN -> properties.add(new ConfigProperty(key, new BooleanConfigValue(value.getAsBoolean())));
                    case INTEGER -> properties.add(new ConfigProperty(key, new IntegerConfigValue(value.getAsInt())));
                    case FLOAT -> properties.add(new ConfigProperty(key, new FloatConfigValue(value.getAsFloat())));
                    default -> AdditionalAdditions.LOGGER.warn("[{}] Incorrect property: {} in behaviour {}. This shouldn't happen, please report.", AdditionalAdditions.namespace, key, resourceLocation.getPath());
                }
            }, () -> AdditionalAdditions.LOGGER.warn("[{}] Unknown property: {} in behaviour {}.", AdditionalAdditions.namespace, key, resourceLocation.getPath()));
        }

        return properties;
    }

    public ConfigProperty getBehaviour(String name) {
        return this.behaviours.get(new ResourceLocation(AdditionalAdditions.namespace, name));
    }

    public ConfigProperty getBehaviour(Behaviours behaviour) {
        return this.behaviours.get(new ResourceLocation(AdditionalAdditions.namespace, behaviour.name()));
    }

    public <T> T getBehaviourValue(String name, BehaviourValues values) {
        ConfigProperty property = getBehaviour(name);
        if (property == null) return null;
        ListConfigValue list = (ListConfigValue) property.value();
        String key = values.getName();
        if (list.get(key) == null || list.get(key).value() == null) {
            ListConfigValue value = Behaviours.getByName(name);
            if (value == null || value.get(key) == null || value.get(key).value() == null) {
                return null;
            } else {
                return (T) value.get(key).value().getValue();
            }
        } else {
            return (T) list.get(key).value().getValue();
        }
    }

    public <T> T getBehaviourValue(Behaviours behaviour, BehaviourValues values) {
        return getBehaviourValue(behaviour.name(), values);
    }

    public Map<ResourceLocation, ConfigProperty> getBehaviours() {
        return this.behaviours;
    }

    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(AdditionalAdditions.namespace, "behaviour");
    }

    static {
        INSTANCE = new BehaviourManager();
    }
}
