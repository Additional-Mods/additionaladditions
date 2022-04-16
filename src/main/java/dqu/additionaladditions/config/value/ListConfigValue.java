package dqu.additionaladditions.config.value;

import dqu.additionaladditions.config.ConfigProperty;

import java.util.ArrayList;

public class ListConfigValue implements ConfigValue {
    ArrayList<ConfigProperty> list = new ArrayList<>();

    @Override
    public Object getValue() {
        return list;
    }

    @Override
    public ConfigValueType getType() {
        return ConfigValueType.LIST;
    }

    public ConfigProperty get(String key) {
        for (ConfigProperty property : list) {
            if (property.key().equals(key)) return property;
        }
        return null;
    }

    public ListConfigValue put(ConfigProperty property) {
        if (property.value().getType().equals(ConfigValueType.LIST))
            throw new IllegalArgumentException("Cannot create lists inside lists!");
        list.add(property);
        return this;
    }
}
