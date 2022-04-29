package dqu.additionaladditions.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.behaviour.BehaviourValues;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

public class AdditionalShovelItem extends ShovelItem {
    private Multimap<Attribute, AttributeModifier> modifiers = null;
    private int previousLoads = BehaviourManager.loads;
    private final float attackSpeed;

    public AdditionalShovelItem(Tier tier, float f, float g, Properties properties) {
        super(tier, f, g, properties);
        attackSpeed = g;
    }

    private void rebuildModifiers() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID , "Tool modifier", getDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID , "Tool modifier", getAttackSpeed(), AttributeModifier.Operation.ADDITION));
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
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        return blockState.is(BlockTags.MINEABLE_WITH_SHOVEL) ? getMiningSpeed() : super.getDestroySpeed(itemStack, blockState);
    }

    public float getDamage() {
        String path = getTier().toString().toLowerCase() + "/shovel";
        Float damage = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.ATTACK_DAMAGE);
        return (damage == null) ? super.getAttackDamage() : damage;
    }

    public float getAttackSpeed() {
        String path = getTier().toString().toLowerCase() + "/shovel";
        Float speed = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.ATTACK_SPEED);
        return (speed == null) ? attackSpeed : speed;
    }

    public float getMiningSpeed() {
        String path = getTier().toString().toLowerCase() + "/shovel";
        Float speed = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.MINING_SPEED);
        return (speed == null) ? this.speed : speed;
    }
}
