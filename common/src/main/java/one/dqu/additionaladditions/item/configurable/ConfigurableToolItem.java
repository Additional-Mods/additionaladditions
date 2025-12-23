package one.dqu.additionaladditions.item.configurable;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ConfigurableToolItem extends TieredItem {
    private final Consumer<DataComponentMap.Builder> configurer;

    public ConfigurableToolItem(Tier tier, Properties properties, Consumer<DataComponentMap.Builder> configurer) {
        super(tier, properties);
        this.configurer = configurer;
    }

    @Override
    public @NotNull DataComponentMap components() {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(super.components());
        configurer.accept(builder);
        return builder.build();
    }
}

