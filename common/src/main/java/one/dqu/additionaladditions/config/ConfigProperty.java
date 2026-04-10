package one.dqu.additionaladditions.config;

import com.google.gson.JsonElement;
import com.mojang.datafixers.DSL;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import one.dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigProperty<T> {
    private static final Map<Identifier, ConfigProperty<?>> PROPERTIES = new HashMap<>();
    private static final Map<String, DSL.TypeReference> TYPE_REFERENCES = new HashMap<>();

    private final Identifier path;
    private final Codec<T> codec;
    private final DSL.TypeReference typeReference;
    private T value;

    protected ConfigProperty(String path, Codec<T> codec, T defaultValue) {
        this.path = Identifier.tryBuild(AdditionalAdditions.NAMESPACE, path);
        this.codec = codec;
        this.value = defaultValue;
        this.typeReference = TYPE_REFERENCES.computeIfAbsent(defaultValue.getClass().getSimpleName(), name -> () -> name);
        PROPERTIES.put(this.path, this);
    }

    public static ConfigProperty<?> getByPath(Identifier location) {
        return PROPERTIES.get(location);
    }

    public static List<ConfigProperty<?>> getAll() {
        return List.copyOf(PROPERTIES.values());
    }

    public Identifier path() {
        return path;
    }

    public Codec<T> codec() {
        return codec;
    }

    public DataResult<JsonElement> serialize() {
        return codec().encodeStart(JsonOps.INSTANCE, get());
    }

    public DataResult<T> deserialize(JsonElement json) {
        return codec().parse(JsonOps.INSTANCE, json);
    }

    public static DSL.TypeReference typeReference(Class<?> clazz) {
        return TYPE_REFERENCES.computeIfAbsent(clazz.getSimpleName(), name -> () -> name);
    }

    public DSL.TypeReference typeReference() {
        return typeReference;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
