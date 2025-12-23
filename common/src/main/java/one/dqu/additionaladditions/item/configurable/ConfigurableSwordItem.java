package one.dqu.additionaladditions.item.configurable;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.core.component.DataComponentMap;

public class ConfigurableSwordItem extends SwordItem {
    private final Consumer<DataComponentMap.Builder> configurer;

    public ConfigurableSwordItem(Tier tier, Properties properties, Consumer<DataComponentMap.Builder> configurer) {
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
