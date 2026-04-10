package one.dqu.additionaladditions.item;

import net.minecraft.world.item.equipment.ArmorType;
import one.dqu.additionaladditions.item.configurable.ConfigurableItem;
import one.dqu.additionaladditions.material.AAMaterial;

public class ArmorItem extends ConfigurableItem {
    public ArmorItem(AAMaterial material, ArmorType armorType, Properties properties) {
        super(properties.repairable(material.getRepairTag()), builder -> material.applyFor(builder, armorType));
    }
}
