package dqu.additionaladditions.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.behaviour.BehaviourValues;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import java.util.UUID;

public class AdditionalArmorItem extends ArmorItem {
    private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    private Multimap<Attribute, AttributeModifier> modifiers = null;
    private int previousLoads = BehaviourManager.loads;

    public AdditionalArmorItem(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
        super(armorMaterial, equipmentSlot, properties);
        rebuildModifiers(equipmentSlot);
    }

    private void rebuildModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid , "Armor modifier", getDefense(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid , "Armor toughness", getToughness(), AttributeModifier.Operation.ADDITION));
        if (getKnockbackResistance() > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", getKnockbackResistance(), AttributeModifier.Operation.ADDITION));
        }
        this.modifiers = builder.build();
    }

    private void rebuildModifiersIfNeeded() {
        if (modifiers == null) {
            rebuildModifiers(getSlot());
            return;
        }
        if (previousLoads != BehaviourManager.loads) {
            previousLoads = BehaviourManager.loads;
            rebuildModifiers(getSlot());
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        rebuildModifiersIfNeeded();
        return (getSlot() == equipmentSlot) ? this.modifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public float getToughness() {
        if (getSlot().getIndex() > 3 || slot.getIndex() < 0) return super.getToughness();
        String path = getMaterial().getName() + "/" + slotIndexToName(getSlot().getIndex());
        Float toughness = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.TOUGHNESS);
        return (toughness == null) ? super.getToughness() : toughness;
    }

    @Override
    public int getDefense() {
        if (getSlot().getIndex() > 3 || slot.getIndex() < 0) return super.getDefense();
        String path = getMaterial().getName() + "/" + slotIndexToName(getSlot().getIndex());
        Integer defense = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.DEFENSE);
        return (defense == null) ? super.getDefense() : defense;
    }

    public float getKnockbackResistance() {
        if (getSlot().getIndex() > 3 || slot.getIndex() < 0) return 0;
        String path = getMaterial().getName() + "/" + slotIndexToName(getSlot().getIndex());
        Float knockback = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.KNOCKBACK_RESISTANCE);
        return (knockback == null) ? super.knockbackResistance : knockback;
    }

    private static String slotIndexToName(int slot) {
        if (slot == 0) return "boots";
        if (slot == 1) return "leggings";
        if (slot == 2) return "chestplate";
        if (slot == 3) return "helmet";
        return null;
    }
}
