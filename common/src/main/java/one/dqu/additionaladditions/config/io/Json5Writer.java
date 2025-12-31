package one.dqu.additionaladditions.config.io;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;

public class Json5Writer {
    private static final Gson GSON = new Gson();
    private static final Map<Class<?>, Class<?>> TYPES = new HashMap<>();
    private static final String INDENT = "  ";

    public static void registerType(Class<?> type, Class<?> unitConfig) {
        TYPES.put(type, unitConfig);
    }

    public static String write(JsonElement json, Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        writeElement(sb, json, clazz, 0);
        return sb.toString();
    }

    private static void writeElement(StringBuilder sb, JsonElement json, Class<?> clazz, int indentLevel) {
        if (json.isJsonObject()) {
            writeObject(sb, json.getAsJsonObject(), clazz, indentLevel);
        } else if (json.isJsonArray()) {
            writeArray(sb, json.getAsJsonArray(), indentLevel);
        } else if (json.isJsonPrimitive()) {
            writePrimitive(sb, json.getAsJsonPrimitive());
        } else if (json.isJsonNull()) {
            sb.append("null");
        }
    }

    private static void writeObject(StringBuilder sb, JsonObject jsonObject, Class<?> clazz, int indentLevel) {
        sb.append("{\n");
        int i = 0;
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if (clazz != null) {
                String comment = getComment(clazz, key);
                if (comment != null) {
                    if (i > 0) sb.append("\n");
                    writeIndent(sb, indentLevel + 1);
                    sb.append("// ").append(comment).append("\n");
                }
            }

            writeIndent(sb, indentLevel + 1);
            sb.append(key).append(": ");

            Class<?> nextClass = null;
            if (clazz != null && value.isJsonObject()) {
                nextClass = getType(clazz, key);
            }

            writeElement(sb, value, nextClass, indentLevel + 1);

            if (i < jsonObject.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
            i++;
        }
        writeIndent(sb, indentLevel);
        sb.append("}");
    }

    private static void writeArray(StringBuilder sb, JsonArray jsonArray, int indentLevel) {
        sb.append("[\n");
        for (int i = 0; i < jsonArray.size(); i++) {
            writeIndent(sb, indentLevel + 1);
            writeElement(sb, jsonArray.get(i), null, indentLevel + 1);
            if (i < jsonArray.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        writeIndent(sb, indentLevel);
        sb.append("]");
    }

    private static void writePrimitive(StringBuilder sb, JsonPrimitive primitive) {
        sb.append(primitive.toString());
    }

    private static void writeIndent(StringBuilder sb, int indentLevel) {
        sb.append(INDENT.repeat(Math.max(0, indentLevel)));
    }

    private static String getComment(Class<?> clazz, String fieldName) {
        String camelCaseName = toCamelCase(fieldName);
        if (clazz.isRecord()) {
            for (RecordComponent component : clazz.getRecordComponents()) {
                if (component.getName().equals(fieldName) || component.getName().equals(camelCaseName)) {
                    Comment annotation = component.getAnnotation(Comment.class);
                    return annotation != null ? annotation.value() : null;
                }
            }
        } else {
            try {
                Field field = getField(clazz, fieldName, camelCaseName);
                if (field != null) {
                    Comment annotation = field.getAnnotation(Comment.class);
                    return annotation != null ? annotation.value() : null;
                }
            } catch (Exception e) {

            }
        }
        return null;
    }

    private static Class<?> getType(Class<?> clazz, String fieldName) {
        String camelCaseName = toCamelCase(fieldName);
        Class<?> type = null;
        if (clazz.isRecord()) {
            for (RecordComponent component : clazz.getRecordComponents()) {
                if (component.getName().equals(fieldName) || component.getName().equals(camelCaseName)) {
                    type = component.getType();
                    break;
                }
            }
        } else {
            try {
                Field field = getField(clazz, fieldName, camelCaseName);
                if (field != null) {
                    type = field.getType();
                }
            } catch (Exception e) {

            }
        }

        if (type != null && TYPES.containsKey(type)) {
            return TYPES.get(type);
        }
        return type;
    }

    private static Field getField(Class<?> clazz, String name, String camelName) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            try {
                return clazz.getDeclaredField(camelName);
            } catch (NoSuchFieldException ex) {
                return null;
            }
        }
    }

    private static String toCamelCase(String s) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpper = false;
        for (char c : s.toCharArray()) {
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    sb.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}
