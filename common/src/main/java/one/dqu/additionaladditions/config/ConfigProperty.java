package one.dqu.additionaladditions.config;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import one.dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ConfigProperty<T> {
    private final ResourceLocation path;
    private final Codec<T> codec;
    private T value;

    protected ConfigProperty(String path, Codec<T> codec, T defaultValue) {
        this.path = ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, path);
        this.codec = codec;
        this.value = defaultValue;
        Config.PROPERTIES.put(this.path, this);
    }

    public static ConfigProperty<?> getByPath(ResourceLocation location) {
        return Config.PROPERTIES.get(location);
    }

    public static List<ConfigProperty<?>> getAll() {
        return List.copyOf(Config.PROPERTIES.values());
    }

    public ResourceLocation path() {
        return path;
    }

    public Codec<T> codec() {
        return codec;
    }

    public DataResult<JsonElement> serialize() {
        return codec().encodeStart(JsonOps.INSTANCE, get());
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
