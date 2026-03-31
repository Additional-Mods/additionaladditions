package one.dqu.additionaladditions.item.configurable;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import one.dqu.additionaladditions.material.AAMaterial;
import one.dqu.additionaladditions.material.ToolType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ConfigurableToolItem extends ConfigurableItem {
    public ConfigurableToolItem(AAMaterial material, ToolType toolType, Properties properties) {
        super(properties, builder -> material.applyFor(builder, toolType));
    }
}
