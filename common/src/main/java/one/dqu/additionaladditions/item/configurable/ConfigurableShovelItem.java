package one.dqu.additionaladditions.item.configurable;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.ShovelItem;
import one.dqu.additionaladditions.material.AAMaterial;
import one.dqu.additionaladditions.material.ToolType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Implements {@link ConfigurableItem} pattern for {@link ShovelItem}.
 */
public class ConfigurableShovelItem extends ShovelItem {
    private final Consumer<DataComponentMap.Builder> configurer;

    public ConfigurableShovelItem(AAMaterial material, ToolType toolType, Properties properties) {
        super(material.getToolMaterial(toolType), 0, 0, properties);
        this.configurer = builder -> material.applyFor(builder, toolType);
    }

    @Override
    public @NotNull DataComponentMap components() {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(super.components());
        configurer.accept(builder);
        return builder.build();
    }
}
