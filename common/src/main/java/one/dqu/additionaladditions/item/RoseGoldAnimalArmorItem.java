package one.dqu.additionaladditions.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ArmorItem;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.type.BodyArmorItemConfig;
import one.dqu.additionaladditions.item.configurable.ConfigurableAnimalArmorItem;
import one.dqu.additionaladditions.item.configurable.ConfigurableArmorItem;
import one.dqu.additionaladditions.registry.AAMaterials;

public class RoseGoldAnimalArmorItem extends ConfigurableAnimalArmorItem {
    public RoseGoldAnimalArmorItem(BodyType bodyType, boolean hasOverlay, Properties properties) {
        super(AAMaterials.ROSE_GOLD, bodyType, hasOverlay, properties, builder -> {
            BodyArmorItemConfig config = Config.ROSE_GOLD_BODY_ARMOR.get();
            if (config != null) {
                builder.set(DataComponents.ATTRIBUTE_MODIFIERS, ConfigurableArmorItem.createAttributes(
                        ArmorItem.Type.BODY,
                        config.protection(),
                        Config.ROSE_GOLD_ARMOR_MATERIAL.get().toughness(),
                        Config.ROSE_GOLD_ARMOR_MATERIAL.get().knockbackResistance()
                ));
            }
        });
    }
}

