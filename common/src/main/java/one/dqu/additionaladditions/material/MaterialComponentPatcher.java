package one.dqu.additionaladditions.material;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantable;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

/**
 * Replicates logic from ToolMaterial and ArmorMaterial apply methods.
 * Uses a DataComponentMap directly over Item.Properties.
 */
public class MaterialComponentPatcher {
    // ToolMaterial

    private static void applyCommonProperties(ToolMaterial material, DataComponentMap.Builder builder) {
        builder.set(DataComponents.MAX_DAMAGE, material.durability());
        builder.set(DataComponents.DAMAGE, 0);
        builder.set(DataComponents.MAX_STACK_SIZE, 1);
        builder.set(DataComponents.ENCHANTABLE, new Enchantable(material.enchantmentValue()));
    }

    public static DataComponentMap applyToolProperties(ToolMaterial material, TagKey<Block> tagKey, float f, float g) {
        DataComponentMap.Builder builder = DataComponentMap.builder();
        applyCommonProperties(material, builder);

        HolderGetter<Block> holderGetter = BuiltInRegistries.BLOCK;
        builder.set(DataComponents.TOOL, new Tool(List.of(
                Tool.Rule.deniesDrops(holderGetter.getOrThrow(material.incorrectBlocksForDrops())),
                Tool.Rule.minesAndDrops(holderGetter.getOrThrow(tagKey), material.speed())
        ), 1.0F, 1, true));

        builder.set(DataComponents.ATTRIBUTE_MODIFIERS, createToolAttributes(material, f, g));

        return builder.build();
    }

    private static ItemAttributeModifiers createToolAttributes(ToolMaterial material, float f, float g) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, f + material.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, g, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    public static DataComponentMap applySwordProperties(ToolMaterial material, float f, float g) {
        DataComponentMap.Builder builder = DataComponentMap.builder();
        applyCommonProperties(material, builder);

        HolderGetter<Block> holderGetter = BuiltInRegistries.BLOCK;
        builder.set(DataComponents.TOOL, new Tool(List.of(
                Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F),
                Tool.Rule.overrideSpeed(holderGetter.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
        ), 1.0F, 2, true));

        builder.set(DataComponents.ATTRIBUTE_MODIFIERS, createSwordAttributes(material, f, g));

        return builder.build();
    }

    private static ItemAttributeModifiers createSwordAttributes(ToolMaterial material, float f, float g) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, f + material.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, g, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    // ArmorMaterial

    public static DataComponentMap humanoidProperties(ArmorMaterial material, ArmorType armorType) {
        DataComponentMap.Builder builder = DataComponentMap.builder();

        builder.set(DataComponents.MAX_DAMAGE, armorType.getDurability(material.durability()));
        builder.set(DataComponents.MAX_STACK_SIZE, 1);
        builder.set(DataComponents.ENCHANTABLE, new Enchantable(material.enchantmentValue()));
        builder.set(DataComponents.ATTRIBUTE_MODIFIERS, createAttributes(material, armorType));

        builder.set(DataComponents.EQUIPPABLE, Equippable.builder(armorType.getSlot())
                .setEquipSound(material.equipSound())
                .setAsset(material.assetId())
                .build());

        return builder.build();
    }

    public static DataComponentMap animalProperties(ArmorMaterial material, HolderSet<EntityType<?>> holderSet) {
        DataComponentMap.Builder builder = DataComponentMap.builder();

        builder.set(DataComponents.MAX_DAMAGE, ArmorType.BODY.getDurability(material.durability()));
        builder.set(DataComponents.MAX_STACK_SIZE, 1);
        builder.set(DataComponents.ATTRIBUTE_MODIFIERS, createAttributes(material, ArmorType.BODY));

        builder.set(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.BODY)
                .setEquipSound(material.equipSound())
                .setAsset(material.assetId())
                .setAllowedEntities(holderSet)
                .build());

        return builder.build();
    }

    public static DataComponentMap animalProperties(ArmorMaterial material, Holder<SoundEvent> holder, boolean bl, HolderSet<EntityType<?>> holderSet) {
        DataComponentMap.Builder builder = DataComponentMap.builder();

        if (bl) {
            builder.set(DataComponents.MAX_DAMAGE, ArmorType.BODY.getDurability(material.durability()));
            builder.set(DataComponents.MAX_STACK_SIZE, 1);
        }

        builder.set(DataComponents.ATTRIBUTE_MODIFIERS, createAttributes(material, ArmorType.BODY));

        builder.set(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.BODY)
                .setEquipSound(holder)
                .setAsset(material.assetId())
                .setAllowedEntities(holderSet)
                .setDamageOnHurt(bl)
                .build());

        return builder.build();
    }

    private static ItemAttributeModifiers createAttributes(ArmorMaterial material, ArmorType armorType) {
        int i = material.defense().getOrDefault(armorType, 0);
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(armorType.getSlot());
        Identifier resourceLocation = Identifier.withDefaultNamespace("armor." + armorType.getName());

        builder.add(Attributes.ARMOR, new AttributeModifier(resourceLocation, i, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
        builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourceLocation, material.toughness(), AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);

        if (material.knockbackResistance() > 0.0F) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourceLocation, material.knockbackResistance(), AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
        }

        return builder.build();
    }

}
