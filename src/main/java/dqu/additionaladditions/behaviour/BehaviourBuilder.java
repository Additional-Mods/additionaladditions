package dqu.additionaladditions.behaviour;

import dqu.additionaladditions.config.ConfigProperty;
import dqu.additionaladditions.config.value.ConfigValue;
import dqu.additionaladditions.config.value.ListConfigValue;

public class BehaviourBuilder {
    private ListConfigValue behaviour;
    private String name;

    public BehaviourBuilder(String behaviour) {
        this.behaviour = new ListConfigValue();
        this.name = behaviour;
    }

    public BehaviourBuilder put(BehaviourValues behaviour, ConfigValue value) {
        this.behaviour.put(new ConfigProperty(behaviour.getName(), value));
        return this;
    }

    public ConfigProperty build() {
        return new ConfigProperty(this.name, this.behaviour);
    }
}
