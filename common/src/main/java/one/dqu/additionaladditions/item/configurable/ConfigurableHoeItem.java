package one.dqu.additionaladditions.item.configurable;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;
import one.dqu.additionaladditions.config.type.ToolItemConfig;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigurableHoeItem extends HoeItem {
    private final Consumer<DataComponentMap.Builder> configurer;

    public ConfigurableHoeItem(Tier tier, Properties properties, Consumer<DataComponentMap.Builder> configurer) {
        super(tier, properties);
        this.configurer = configurer;
    }

    public ConfigurableHoeItem(Tier tier, Properties properties, Supplier<ToolItemConfig> configSupplier) {
        super(tier, properties);
        this.configurer = builder -> {
            ToolItemConfig config = configSupplier.get();
            builder.set(DataComponents.MAX_DAMAGE, config.durability());
            builder.set(DataComponents.ATTRIBUTE_MODIFIERS, HoeItem.createAttributes(tier, config.attackDamage(), config.attackSpeed()));
            builder.set(DataComponents.TOOL, config.toolProperties(tier, BlockTags.MINEABLE_WITH_HOE));
        };
    }

    @Override
    public @NotNull DataComponentMap components() {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(super.components());
        configurer.accept(builder);
        return builder.build();
    }
}

