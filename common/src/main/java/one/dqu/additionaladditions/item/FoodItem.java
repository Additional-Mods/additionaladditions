package one.dqu.additionaladditions.item;

import net.minecraft.core.component.DataComponents;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.type.FoodConfig;
import one.dqu.additionaladditions.item.configurable.ConfigurableItem;

public class FoodItem extends ConfigurableItem {
    public FoodItem(Properties properties, ConfigProperty<FoodConfig> config) {
        super(properties, builder -> {
            builder.set(DataComponents.FOOD, config.get().food());
        });
    }
}
