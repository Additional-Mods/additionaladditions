package dqu.additionaladditions.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.behaviour.BehaviourValues;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class AdditionalSwordItem extends SwordItem {
    private Multimap<Attribute, AttributeModifier> modifiers = null;
    private int previousLoads = BehaviourManager.loads;
    private final float attackSpeed;

    public AdditionalSwordItem(Tier tier, int i, float f, Properties properties) {
        super(tier, i, f, properties);
        attackSpeed = f;
    }

    private void rebuildModifiers() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID , "Weapon modifier", getDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID , "Weapon modifier", getAttackSpeed(), AttributeModifier.Operation.ADDITION));
        this.modifiers = builder.build();
    }

    private void rebuildModifiersIfNeeded() {
        if (modifiers == null) {
            rebuildModifiers();
            return;
        }
        if (previousLoads != BehaviourManager.loads) {
            previousLoads = BehaviourManager.loads;
            rebuildModifiers();
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        rebuildModifiersIfNeeded();
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.modifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public float getDamage() {
        String path = getTier().toString().toLowerCase() + "/sword";
        Float damage = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.ATTACK_DAMAGE);
        return (damage == null) ? super.getDamage() : damage;
    }

    public float getAttackSpeed() {
        String path = getTier().toString().toLowerCase() + "/sword";
        Float speed = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.ATTACK_SPEED);
        return (speed == null) ? attackSpeed : speed;
    }
}
