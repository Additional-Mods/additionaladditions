package one.dqu.additionaladditions.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ArmorItem;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.type.ArmorItemConfig;
import one.dqu.additionaladditions.item.configurable.ConfigurableArmorItem;
import one.dqu.additionaladditions.registry.AAMaterials;

public class RoseGoldArmorItem extends ConfigurableArmorItem {
    public RoseGoldArmorItem(Type type, Properties properties) {
        super(AAMaterials.ROSE_GOLD, type, properties, builder -> {
            ArmorItemConfig config = getConfig(type);
            if (config != null) {
                builder.set(DataComponents.MAX_DAMAGE, config.durability());
                builder.set(DataComponents.ATTRIBUTE_MODIFIERS, ConfigurableArmorItem.createAttributes(
                        type,
                        config.protection(),
                        Config.ROSE_GOLD_ARMOR_MATERIAL.get().toughness(),
                        Config.ROSE_GOLD_ARMOR_MATERIAL.get().knockbackResistance()
                ));
            }
        });
    }

    private static ArmorItemConfig getConfig(Type type) {
        return switch (type) {
            case HELMET -> Config.ROSE_GOLD_HELMET.get();
            case CHESTPLATE -> Config.ROSE_GOLD_CHESTPLATE.get();
            case LEGGINGS -> Config.ROSE_GOLD_LEGGINGS.get();
            case BOOTS -> Config.ROSE_GOLD_BOOTS.get();
            default -> null;
        };
    }
}

