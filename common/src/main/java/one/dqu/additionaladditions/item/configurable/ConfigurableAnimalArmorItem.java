package one.dqu.additionaladditions.item.configurable;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ConfigurableAnimalArmorItem extends AnimalArmorItem {
    private final Consumer<DataComponentMap.Builder> configurer;

    public ConfigurableAnimalArmorItem(Holder<ArmorMaterial> material, BodyType bodyType, boolean hasOverlay, Properties properties, Consumer<DataComponentMap.Builder> configurer) {
        super(material, bodyType, hasOverlay, properties);
        this.configurer = configurer;
    }

    @Override
    public @NotNull DataComponentMap components() {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(super.components());
        configurer.accept(builder);
        return builder.build();
    }
}

