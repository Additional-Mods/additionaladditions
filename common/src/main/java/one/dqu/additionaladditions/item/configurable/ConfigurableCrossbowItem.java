package one.dqu.additionaladditions.item.configurable;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.CrossbowItem;

import java.util.function.Consumer;

public class ConfigurableCrossbowItem extends CrossbowItem {
    Consumer<DataComponentMap.Builder> configurer = builder -> {};

    public ConfigurableCrossbowItem(Properties properties) {
        super(properties);
    }

    public ConfigurableCrossbowItem(Properties properties, Consumer<DataComponentMap.Builder> configurer) {
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
