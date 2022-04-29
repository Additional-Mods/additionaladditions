package dqu.additionaladditions.config.value;

public enum ConfigValueType {
    BOOLEAN,
    STRING,
    INTEGER,
    LIST,
    FLOAT;

    public static final BooleanConfigValue TRUE = new BooleanConfigValue(true);
    public static final BooleanConfigValue FALSE = new BooleanConfigValue(false);
}
