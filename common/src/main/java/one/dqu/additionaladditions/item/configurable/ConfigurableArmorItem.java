package one.dqu.additionaladditions.item.configurable;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ConfigurableArmorItem extends ArmorItem {
    private final Consumer<DataComponentMap.Builder> configurer;

    public ConfigurableArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties, Consumer<DataComponentMap.Builder> configurer) {
        super(material, type, properties);
        this.configurer = configurer;
    }

    @Override
    public @NotNull DataComponentMap components() {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(super.components());
        configurer.accept(builder);
        return builder.build();
    }

    public static ItemAttributeModifiers createAttributes(Type type, int protection, float toughness, float knockbackResistance) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
        ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
        builder.add(Attributes.ARMOR, new AttributeModifier(resourceLocation, protection, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
        builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourceLocation, toughness, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
        if (knockbackResistance > 0.0F) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourceLocation, knockbackResistance, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
        }
        return builder.build();
    }
}
