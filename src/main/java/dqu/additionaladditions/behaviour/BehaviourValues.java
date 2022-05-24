package dqu.additionaladditions.behaviour;

import dqu.additionaladditions.config.value.ConfigValueType;

import java.util.Locale;
import java.util.Optional;

public enum BehaviourValues {
    DURABILITY (ConfigValueType.INTEGER),
    DEFENSE (ConfigValueType.INTEGER),
    TOUGHNESS (ConfigValueType.FLOAT),
    KNOCKBACK_RESISTANCE (ConfigValueType.FLOAT),
    ATTACK_DAMAGE (ConfigValueType.FLOAT),
    ATTACK_SPEED (ConfigValueType.FLOAT),
    MINING_SPEED (ConfigValueType.FLOAT);

    private final ConfigValueType type;

    BehaviourValues(ConfigValueType type) {
        this.type = type;
    }

    public ConfigValueType getType() {
        return type;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static Optional<BehaviourValues> getByName(String name) {
        for (BehaviourValues value : values()) {
            if (value.getName().equals(name)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
