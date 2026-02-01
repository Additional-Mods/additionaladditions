package one.dqu.additionaladditions.item.configurable;

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

    /**
     * This determines the map of default item components of an Item.
     * <p>
     * We use this to make otherwise static data (like durability or attribute modifiers) dynamic,
     * so the server-client config sync works properly.
     * <p>
     * This should have a negligible impact on performance as this method is only called once
     * per initialization of an ItemStack instance.
     */
    @Override
    public DataComponentMap components() {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(super.components());
        configurer.accept(builder);
        return builder.build();
    }
}
