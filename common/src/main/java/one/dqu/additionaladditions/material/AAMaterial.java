package one.dqu.additionaladditions.material;

import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.ArmorLikeConfig;
import one.dqu.additionaladditions.config.ToolLikeConfig;
import one.dqu.additionaladditions.config.io.ConfigLoader;
import one.dqu.additionaladditions.config.type.ArmorItemConfig;
import one.dqu.additionaladditions.config.type.MaterialConfig;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class AAMaterial {
    private final EnumMap<ToolType, Supplier<ToolLikeConfig>> toolConfigs = new EnumMap<>(ToolType.class);
    private final EnumMap<ArmorType, Supplier<ArmorLikeConfig>> armorConfigs = new EnumMap<>(ArmorType.class);
    private final Supplier<MaterialConfig> materialConfig;
    private final TagKey<Item> repairIngredient;
    private final String name;

    private final EnumMap<ToolType, ToolMaterial> toolMaterials = new EnumMap<>(ToolType.class);
    private final EnumMap<ArmorType, ArmorMaterial> armorMaterials = new EnumMap<>(ArmorType.class);

    public AAMaterial(String name, Supplier<MaterialConfig> materialConfigSupplier, TagKey<Item> repairIngredient, Map<ToolType, Supplier<ToolLikeConfig>> toolConfigSuppliers, Map<ArmorType, Supplier<ArmorLikeConfig>> armorConfigSuppliers) {
        this.name = name;
        this.materialConfig = materialConfigSupplier;
        this.repairIngredient = repairIngredient;
        this.toolConfigs.putAll(toolConfigSuppliers);
        this.armorConfigs.putAll(armorConfigSuppliers);

        refreshMaps();
        ConfigLoader.onPostReload(
                Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, name + "_material"),
                this::refreshMaps
        );
    }

    public void applyFor(DataComponentMap.Builder builder, ToolType toolType) {
        ToolMaterial material = toolMaterials.get(toolType);
        ToolLikeConfig config = toolConfigs.get(toolType).get();
        DataComponentMap patch;

        if (toolType == ToolType.SWORD) {
            patch = MaterialComponentPatcher.applySwordProperties(material, config.attackDamage(), config.attackSpeed());
        } else {
            patch = MaterialComponentPatcher.applyToolProperties(material, toolType.mineableTag(), config.attackDamage(), config.attackSpeed());
        }

        builder.addAll(patch);
    }

    public void applyFor(DataComponentMap.Builder builder, ArmorType armorType) {
        ArmorMaterial material = armorMaterials.get(armorType);
        DataComponentMap patch;

        if (armorType == ArmorType.BODY) {
            // horse is hardcoded
            patch = MaterialComponentPatcher.animalProperties(material, HolderSet.direct(EntityType.HORSE.builtInRegistryHolder()));
        } else {
            patch = MaterialComponentPatcher.humanoidProperties(material, armorType);
        }

        builder.addAll(patch);
    }

    public ToolMaterial getToolMaterial(ToolType type) {
        return toolMaterials.get(type);
    }

    public ArmorMaterial getArmorMaterial(ArmorType type) {
        return armorMaterials.get(type);
    }

    public TagKey<Item> getRepairTag() {
        return repairIngredient;
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
        MaterialConfig materialConfig = this.materialConfig.get();

        return new ToolMaterial(
                materialConfig.incorrectBlocksForDrops(),
                config.durability(),
                config.blockBreakSpeed(),
                -1.0F,
                materialConfig.enchantability(),
                repairIngredient
        );
    }

    private ArmorMaterial createArmorMaterialFor(ArmorType type) {
        ArmorLikeConfig config = armorConfigs.get(type).get();
        MaterialConfig materialConfig = this.materialConfig.get();
        int durability = 1; // horse armor has no durability. BodyArmorItemConfig is used for horse armor only and doesnt have a durability field.

        if (config instanceof ArmorItemConfig armorItemConfig) {
            durability = armorItemConfig.durability();
        }

        return new ArmorMaterial(
                durability,
                new EnumMap<>(Map.of(
                        type, config.protection()
                )),
                materialConfig.enchantability(),
                materialConfig.equipSound(),
                materialConfig.toughness(),
                materialConfig.knockbackResistance(),
                repairIngredient,
                ResourceKey.create(
                        ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset")),
                        Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, name)
                )
        );
    }
}
