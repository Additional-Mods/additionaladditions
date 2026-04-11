package one.dqu.additionaladditions.material;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.enchantment.Enchantable;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.ArmorLikeConfig;
import one.dqu.additionaladditions.config.ToolLikeConfig;
import one.dqu.additionaladditions.config.type.ArmorItemConfig;
import one.dqu.additionaladditions.config.type.MaterialConfig;
import one.dqu.additionaladditions.config.type.SpearItemConfig;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public record AAMaterial(
        String name,
        Supplier<MaterialConfig> materialConfig,
        TagKey<Item> repairIngredient,
        Map<ToolType, Supplier<ToolLikeConfig>> toolConfigs,
        Map<ArmorType, Supplier<ArmorLikeConfig>> armorConfigs
) {
    public void applyFor(DataComponentMap.Builder builder, ToolType toolType) {
        DataComponentMap patch;

        if (toolType == ToolType.SPEAR) {
            patch = spearProperties(name, materialConfig.get(), toolConfigs.get(toolType).get());
        } else {
            patch = toolProperties(toolType, materialConfig.get(), toolConfigs.get(toolType).get());
        }

        builder.addAll(patch);
    }

    public void applyFor(DataComponentMap.Builder builder, ArmorType armorType) {
        if (armorType == ArmorType.BODY) {
            throw new IllegalArgumentException("BODY armor type not supported by this method. Material: " + name);
        }

        var patch = AAMaterial.humanoidArmorProperties(
                name,
                armorType,
                materialConfig.get(),
                armorConfigs.get(armorType).get()
        );

        builder.addAll(patch);
    }

    public void applyFor(DataComponentMap.Builder builder, AnimalArmorType armorType) {
        var patch = AAMaterial.animalArmorProperties(
                name,
                armorType,
                materialConfig.get(),
                armorConfigs.get(ArmorType.BODY).get()
        );

        builder.addAll(patch);
    }

    private static DataComponentMap toolProperties(ToolType type, MaterialConfig material, ToolLikeConfig toolLike) {
        DataComponentMap.Builder builder = DataComponentMap.builder();
        HolderGetter<Block> registry = BuiltInRegistries.BLOCK;

        builder.set(DataComponents.MAX_DAMAGE, toolLike.durability());
        builder.set(DataComponents.DAMAGE, 0);
        builder.set(DataComponents.MAX_STACK_SIZE, 1);
        builder.set(DataComponents.ENCHANTABLE, new Enchantable(material.enchantability()));

        if (type == ToolType.SWORD) {
            Tool toolComponent = new Tool(
                    List.of(
                            Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F),
                            Tool.Rule.overrideSpeed(registry.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                            Tool.Rule.overrideSpeed(registry.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
                    ),
                    1.0F, 2, false
            );
            builder.set(DataComponents.TOOL, toolComponent);
        } else if (!type.isWeapon()) {
            Tool toolComponent = new Tool(
                    List.of(
                            Tool.Rule.deniesDrops(registry.getOrThrow(material.incorrectBlocksForDrops())),
                            Tool.Rule.minesAndDrops(registry.getOrThrow(type.mineableTag()), toolLike.blockBreakSpeed())
                    ),
                    1.0F, 1, true
            );
            builder.set(DataComponents.TOOL, toolComponent);
        }

        if (type.isWeapon()) {
            builder.set(DataComponents.WEAPON, new Weapon(1));
        } else {
            //todo maybe make this configurable
            float disableBlockingForSeconds = type == ToolType.AXE ? 5.0F : 0.0F;
            builder.set(DataComponents.WEAPON, new Weapon(2, disableBlockingForSeconds));
        }

        ItemAttributeModifiers attributes = ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                Item.BASE_ATTACK_DAMAGE_ID,
                                toolLike.attackDamage() - 1,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                Item.BASE_ATTACK_SPEED_ID,
                                toolLike.attackSpeed(),
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
        builder.set(DataComponents.ATTRIBUTE_MODIFIERS, attributes);

        return builder.build();
    }

    private static DataComponentMap spearProperties(String name, MaterialConfig material, ToolLikeConfig toolLike) {
        if (!(toolLike instanceof SpearItemConfig spear)) {
            throw new IllegalArgumentException("ToolLike of tool type SPEAR of material " + name + " is not an instance of SpearItemConfig.");
        }

        DataComponentMap.Builder builder = DataComponentMap.builder();

        DataComponentMap tool = toolProperties(ToolType.SPEAR, material, toolLike);
        builder.addAll(tool);

        builder.set(DataComponents.DAMAGE_TYPE, new EitherHolder<>(DamageTypes.SPEAR));
        builder.set(DataComponents.KINETIC_WEAPON, spear.kineticWeapon());
        builder.set(DataComponents.PIERCING_WEAPON, spear.piercingWeapon());
        builder.set(DataComponents.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, spear.swingAnimationTicks()));

        // constant values from Item.Properties#spear
        builder.set(DataComponents.ATTACK_RANGE, new AttackRange(2.0F, 4.5F, 2.0F, 6.5F, 0.125F, 0.5F));
        builder.set(DataComponents.MINIMUM_ATTACK_CHARGE, 1.0F);
        builder.set(DataComponents.USE_EFFECTS, new UseEffects(true, false, 1.0F));

        return builder.build();
    }

    private static ItemAttributeModifiers createArmorAttributes(ArmorType armorType, MaterialConfig material, ArmorLikeConfig armorLike) {
        EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(armorType.getSlot());
        Identifier identifier = Identifier.withDefaultNamespace("armor." + armorType.getName());

        ItemAttributeModifiers.Builder attributes = ItemAttributeModifiers.builder()
                .add(
                        Attributes.ARMOR,
                        new AttributeModifier(
                                identifier,
                                armorLike.protection(),
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        slotGroup
                )
                .add(
                        Attributes.ARMOR_TOUGHNESS,
                        new AttributeModifier(
                                identifier,
                                material.toughness(),
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        slotGroup
                );

        if (material.knockbackResistance() > 0) {
            attributes.add(
                    Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(
                            identifier,
                            material.knockbackResistance(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    slotGroup
            );
        }

        return attributes.build();
    }

    private static DataComponentMap humanoidArmorProperties(String name, ArmorType type, MaterialConfig material, ArmorLikeConfig armorLike) {
        DataComponentMap.Builder builder = DataComponentMap.builder();

        if (armorLike instanceof ArmorItemConfig armorItemConfig) {
            builder.set(DataComponents.MAX_DAMAGE, armorItemConfig.durability());
            builder.set(DataComponents.DAMAGE, 0);
        } else {
            throw new IllegalArgumentException("ArmorLike of armor type " + type.getName() + " of material " + name + " is not an instance of ArmorItemConfig.");
        }

        builder.set(DataComponents.MAX_STACK_SIZE, 1);
        builder.set(DataComponents.ENCHANTABLE, new Enchantable(material.enchantability()));

        builder.set(DataComponents.ATTRIBUTE_MODIFIERS, createArmorAttributes(type, material, armorLike));

        ResourceKey<EquipmentAsset> assetId = ResourceKey.create(
                ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset")),
                Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, name)
        );

        Equippable equippable = Equippable.builder(type.getSlot())
                .setEquipSound(material.equipSound())
                .setAsset(assetId)
                .build();
        builder.set(DataComponents.EQUIPPABLE, equippable);

        return builder.build();
    }

    private static DataComponentMap animalArmorProperties(String name, AnimalArmorType armorType, MaterialConfig material, ArmorLikeConfig armorLike) {
        DataComponentMap.Builder builder = DataComponentMap.builder();

        builder.set(DataComponents.MAX_STACK_SIZE, 1);
        builder.set(DataComponents.ENCHANTABLE, new Enchantable(material.enchantability()));

        builder.set(DataComponents.ATTRIBUTE_MODIFIERS, createArmorAttributes(ArmorType.BODY, material, armorLike));

        HolderGetter<EntityType<?>> registry = BuiltInRegistries.ENTITY_TYPE;

        ResourceKey<EquipmentAsset> assetId = ResourceKey.create(
                ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset")),
                Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, name)
        );

        Equippable equippable = Equippable.builder(EquipmentSlot.BODY)
                .setEquipSound(armorType.equipSound())
                .setAsset(assetId)
                .setAllowedEntities(registry.getOrThrow(armorType.canWearTag()))
                .setDamageOnHurt(false)
                .setEquipOnInteract(true)
                .setCanBeSheared(true)
                .setShearingSound(armorType.unequipSound())
                .build();
        builder.set(DataComponents.EQUIPPABLE, equippable);

        return builder.build();
    }
}
