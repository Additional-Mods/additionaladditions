package dqu.additionaladditions.config.value;

public class StringConfigValue implements ConfigValue {
    String value;

    public StringConfigValue(String value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public ConfigValueType getType() {
        return ConfigValueType.STRING;
    }
}
