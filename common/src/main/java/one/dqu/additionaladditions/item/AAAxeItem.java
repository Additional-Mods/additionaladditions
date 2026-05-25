package one.dqu.additionaladditions.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ToolMaterial;

public class AAAxeItem extends AxeItem {
    // dummy values. real values should be overriden in component initializer of AAItem builder
    public AAAxeItem(Properties properties) {
        super(ToolMaterial.WOOD, 0, 0, properties);
    }
}
