package dqu.additionaladditions.config.value;

public class IntegerConfigValue implements ConfigValue {
    Integer value;

    public IntegerConfigValue(Integer value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public ConfigValueType getType() {
        return ConfigValueType.INTEGER;
    }
}
