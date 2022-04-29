package dqu.additionaladditions.config.value;

public class FloatConfigValue implements ConfigValue {
    Float value;

    public FloatConfigValue(Float value) {
        this.value = value;
    }

    public FloatConfigValue(String value) {
        this.value = Float.parseFloat(value);
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public ConfigValueType getType() {
        return ConfigValueType.FLOAT;
    }
}
