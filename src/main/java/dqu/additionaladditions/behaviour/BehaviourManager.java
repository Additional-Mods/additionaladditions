package dqu.additionaladditions.behaviour;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.ConfigProperty;
import dqu.additionaladditions.config.value.BooleanConfigValue;
import dqu.additionaladditions.config.value.IntegerConfigValue;
import dqu.additionaladditions.config.value.ListConfigValue;
import dqu.additionaladditions.config.value.StringConfigValue;
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

            if (key.startsWith("s_")) {
                properties.add(new ConfigProperty(key.substring(2), new StringConfigValue(value.getAsString())));
            } else if (key.startsWith("b_")) {
                properties.add(new ConfigProperty(key.substring(2), new BooleanConfigValue(value.getAsBoolean())));
            } else if (key.startsWith("i_")) {
                properties.add(new ConfigProperty(key.substring(2), new IntegerConfigValue(value.getAsInt())));
            } else {
                AdditionalAdditions.LOGGER.warn("[{}] Unknown property: {} in behaviour {}", AdditionalAdditions.namespace, key, resourceLocation);
            }
        }

        return properties;
    }

    public ConfigProperty getBehaviour(String name) {
        return this.behaviours.get(new ResourceLocation(AdditionalAdditions.namespace, name));
    }

    public <T> T getBehaviourValue(String name, String key) {
        ConfigProperty property = this.behaviours.get(new ResourceLocation(AdditionalAdditions.namespace, name));
        ListConfigValue list = (ListConfigValue) property.value();
        return (T) list.get(key).value().getValue();
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
