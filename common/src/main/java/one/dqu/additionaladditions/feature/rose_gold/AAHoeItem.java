package one.dqu.additionaladditions.feature.rose_gold;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ToolMaterial;

public class AAHoeItem extends HoeItem {
    // dummy values. real values should be overriden in component initializer of AAItem builder
    public AAHoeItem(Properties properties) {
        super(ToolMaterial.WOOD, 0, 0, properties);
    }
}
