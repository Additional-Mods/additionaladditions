package one.dqu.additionaladditions.item.configurable;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import one.dqu.additionaladditions.material.AAMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ConfigurableAnimalArmorItem extends AnimalArmorItem {
    private final Consumer<DataComponentMap.Builder> configurer;

    public ConfigurableAnimalArmorItem(ArmorMaterial material, BodyType type, Properties properties, Consumer<DataComponentMap.Builder> configurer) {
        super(material, type, properties);
        this.configurer = configurer;
    }

    public ConfigurableAnimalArmorItem(AAMaterial material, BodyType type, Properties properties) {
        super(material.getArmorMaterial(ArmorType.BODY), type, properties);
        this.configurer = builder -> material.applyFor(builder, ArmorType.BODY);
    }

    @Override
    public @NotNull DataComponentMap components() {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(super.components());
        configurer.accept(builder);
        return builder.build();
    }
}
