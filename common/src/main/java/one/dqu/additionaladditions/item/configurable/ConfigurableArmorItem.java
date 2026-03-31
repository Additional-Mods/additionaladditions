package one.dqu.additionaladditions.item.configurable;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import one.dqu.additionaladditions.material.AAMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ConfigurableArmorItem extends ArmorItem {
    private final Consumer<DataComponentMap.Builder> configurer;

    public ConfigurableArmorItem(ArmorMaterial material, ArmorType type, Properties properties, Consumer<DataComponentMap.Builder> configurer) {
        super(material, type, properties);
        this.configurer = configurer;
    }

    public ConfigurableArmorItem(AAMaterial material, ArmorType type, Properties properties) {
        super(material.getArmorMaterial(type), type, properties);
        this.configurer = builder -> material.applyFor(builder, type);
    }

    @Override
    public @NotNull DataComponentMap components() {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(super.components());
        configurer.accept(builder);
        return builder.build();
    }
}
