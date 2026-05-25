package one.dqu.additionaladditions.item;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ToolMaterial;

public class AAShovelItem extends ShovelItem {
    // dummy values. real values should be overriden in component initializer of AAItem builder
    public AAShovelItem(Properties properties) {
        super(ToolMaterial.WOOD, 0, 0, properties);
    }
}
