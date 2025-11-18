package one.dqu.additionaladditions.item;

import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.item.configurable.ConfigurableCrossbowItem;
import net.minecraft.core.component.DataComponents;

public class CrossbowWithSpyglassItem extends ConfigurableCrossbowItem {
    public CrossbowWithSpyglassItem(Properties properties) {
        super(properties, builder -> {
            builder.set(DataComponents.MAX_DAMAGE, Config.CROSSBOW_WITH_SPYGLASS_DURABILITY.get().durability());
        });
    }
}
