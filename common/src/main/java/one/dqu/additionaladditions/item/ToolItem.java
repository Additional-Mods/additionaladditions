package one.dqu.additionaladditions.item;

import one.dqu.additionaladditions.item.configurable.ConfigurableItem;
import one.dqu.additionaladditions.material.AAMaterial;
import one.dqu.additionaladditions.material.ToolType;

public class ToolItem extends ConfigurableItem {
    public ToolItem(AAMaterial material, ToolType toolType, Properties properties) {
        super(properties.repairable(material.repairIngredient()), builder -> material.applyFor(builder, toolType));
    }
}
