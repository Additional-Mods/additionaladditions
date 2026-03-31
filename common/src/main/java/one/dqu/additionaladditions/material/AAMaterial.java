package one.dqu.additionaladditions.material;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.ToolLikeConfig;
import one.dqu.additionaladditions.config.io.ConfigLoader;
import one.dqu.additionaladditions.config.type.ArmorItemConfig;
import one.dqu.additionaladditions.config.type.ArmorMaterialConfig;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class AAMaterial {
    private final EnumMap<ToolType, Supplier<ToolLikeConfig>> toolConfigs = new EnumMap<>(ToolType.class);
    private final EnumMap<ArmorType, Supplier<ArmorItemConfig>> armorConfigs = new EnumMap<>(ArmorType.class);
    private final Supplier<ArmorMaterialConfig> materialConfig;
    private final String name;

    private final EnumMap<ToolType, ToolMaterial> toolMaterials = new EnumMap<>(ToolType.class);
    private final EnumMap<ArmorType, ArmorMaterial> armorMaterials = new EnumMap<>(ArmorType.class);

    public AAMaterial(String name, Supplier<ArmorMaterialConfig> materialConfigSupplier, Map<ToolType, Supplier<ToolLikeConfig>> toolConfigSuppliers, Map<ArmorType, Supplier<ArmorItemConfig>> armorConfigSuppliers) {
        this.name = name;
        this.materialConfig = materialConfigSupplier;
        this.toolConfigs.putAll(toolConfigSuppliers);
        this.armorConfigs.putAll(armorConfigSuppliers);

        refreshMaps();
        ConfigLoader.onPostReload(
                ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, name + "_material"),
                this::refreshMaps
        );
    }

    public void applyFor(DataComponentMap.Builder builder, ToolType toolType) {
        Item.Properties properties = new Item.Properties();
        ToolMaterial material = toolMaterials.get(toolType);
        ToolLikeConfig config = toolConfigs.get(toolType).get();

        if (toolType == ToolType.SWORD) {
            material.applySwordProperties(properties, config.attackDamage(), config.attackSpeed());
        } else {
            material.applyToolProperties(properties, toolType.mineableTag(), config.attackDamage(), config.attackSpeed());
        }

        DataComponentMap components = properties.buildAndValidateComponents(Component.literal(""), properties.effectiveModel());

        final Set<DataComponentType<?>> validComponents = Set.of(
                DataComponents.MAX_DAMAGE, DataComponents.ATTRIBUTE_MODIFIERS, DataComponents.TOOL, DataComponents.ENCHANTABLE,
                DataComponents.EQUIPPABLE, DataComponents.REPAIRABLE, DataComponents.REPAIR_COST
        );

        DataComponentMap patch = components.filter(validComponents::contains);

        builder.addAll(patch);
    }

    public void applyFor(DataComponentMap.Builder builder, ArmorType armorType) {
        Item.Properties properties = new Item.Properties();
        ArmorMaterial material = armorMaterials.get(armorType);

        material.humanoidProperties(properties, armorType);

        DataComponentMap components = properties.buildAndValidateComponents(Component.literal(""), properties.effectiveModel());

        final Set<DataComponentType<?>> validComponents = Set.of(
                DataComponents.MAX_DAMAGE, DataComponents.ATTRIBUTE_MODIFIERS, DataComponents.TOOL, DataComponents.ENCHANTABLE,
                DataComponents.EQUIPPABLE, DataComponents.REPAIRABLE, DataComponents.REPAIR_COST
        );

        DataComponentMap patch = components.filter(validComponents::contains);

        builder.addAll(patch);
    }

    public ToolMaterial getToolMaterial(ToolType type) {
        return toolMaterials.get(type);
    }

    public ArmorMaterial getArmorMaterial(ArmorType type) {
        return armorMaterials.get(type);
    }

    private void refreshMaps() {
        for (ToolType type : ToolType.values()) {
            toolMaterials.put(type, createToolMaterialFor(type));
        }
        for (ArmorType type : ArmorType.values()) {
            armorMaterials.put(type, createArmorMaterialFor(type));
        }
    }

    private ToolMaterial createToolMaterialFor(ToolType type) {
        ToolLikeConfig config = toolConfigs.get(type).get();
        ArmorMaterialConfig materialConfig = this.materialConfig.get();

        return new ToolMaterial(
                type.mineableTag(),
                config.durability(),
                config.blockBreakSpeed(),
                -1.0F,
                materialConfig.enchantability(),
                materialConfig.repairIngredient()
        );
    }

    private ArmorMaterial createArmorMaterialFor(ArmorType type) {
        ArmorItemConfig config = armorConfigs.get(type).get();
        ArmorMaterialConfig materialConfig = this.materialConfig.get();

        return new ArmorMaterial(
                config.durability(),
                new EnumMap<>(Map.of(
                        type, config.protection()
                )),
                materialConfig.enchantability(),
                materialConfig.equipSound(),
                materialConfig.toughness(),
                materialConfig.knockbackResistance(),
                materialConfig.repairIngredient(),
                ResourceKey.create(
                        ResourceKey.createRegistryKey(ResourceLocation.withDefaultNamespace("equipment_asset")),
                        ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, name)
                )
        );
    }
}
