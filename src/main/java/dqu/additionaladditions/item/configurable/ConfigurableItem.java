package dqu.additionaladditions.item.configurable;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

public class ConfigurableItem extends Item {
    Consumer<DataComponentMap.Builder> configurer = builder -> {};

    public ConfigurableItem(Properties properties) {
        super(properties);
    }

    public ConfigurableItem(Properties properties, Consumer<DataComponentMap.Builder> configurer) {
        super(properties);
        this.configurer = configurer;
    }

    @Override
    public DataComponentMap components() {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(super.components());
        configurer.accept(builder);
        return builder.build();
    }
}
