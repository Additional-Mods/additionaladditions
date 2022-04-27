package dqu.additionaladditions.behaviour;

import dqu.additionaladditions.config.ConfigProperty;
import dqu.additionaladditions.config.value.IntegerConfigValue;
import dqu.additionaladditions.config.value.ListConfigValue;

public enum Behaviours {
    GILDED_NETHERITE_BOOTS (new BehaviourBuilder("gilded_netherite/boots")
            .put(BehaviourValues.DURABILITY, new IntegerConfigValue(481))
            .build()),
    GILDED_NETHERITE_CHESTPLATE (new BehaviourBuilder("gilded_netherite/chestplate")
            .put(BehaviourValues.DURABILITY, new IntegerConfigValue(592))
            .build()),
    GILDED_NETHERITE_HELMET (new BehaviourBuilder("gilded_netherite/helmet")
            .put(BehaviourValues.DURABILITY, new IntegerConfigValue(407))
            .build()),
    GILDED_NETHERITE_LEGGINGS (new BehaviourBuilder("gilded_netherite/leggings")
            .put(BehaviourValues.DURABILITY, new IntegerConfigValue(555))
            .build()),
    ROSE_GOLD_BOOTS (new BehaviourBuilder("rose_gold/boots")
            .put(BehaviourValues.DURABILITY, new IntegerConfigValue(264))
            .build()),
    ROSE_GOLD_CHESTPLATE (new BehaviourBuilder("rose_gold/chestplate")
            .put(BehaviourValues.DURABILITY, new IntegerConfigValue(384))
            .build()),
    ROSE_GOLD_HELMET (new BehaviourBuilder("rose_gold/helmet")
            .put(BehaviourValues.DURABILITY, new IntegerConfigValue(360))
            .build()),
    ROSE_GOLD_LEGGINGS (new BehaviourBuilder("rose_gold/leggings")
            .put(BehaviourValues.DURABILITY, new IntegerConfigValue(312))
            .build());

    private final ConfigProperty behaviour;

    Behaviours(ConfigProperty behaviour) {
        this.behaviour = behaviour;
    }

    public ConfigProperty get() {
        return behaviour;
    }

    public static ListConfigValue getByName(String name) {
        for (Behaviours behaviour : values()) {
            if (behaviour.get().key().equalsIgnoreCase(name)) {
                return (ListConfigValue) behaviour.get().value();
            }
        }
        return null;
    }
}
