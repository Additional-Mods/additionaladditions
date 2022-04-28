package dqu.additionaladditions.mixin.behaviour;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.behaviour.BehaviourValues;
import dqu.additionaladditions.material.GildedNetheriteArmorMaterial;
import dqu.additionaladditions.material.RoseGoldArmorMaterial;
import dqu.additionaladditions.registry.AdditionalMaterials;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorItem.class)
public abstract class ArmorItemMixin {
    @Shadow @Final private static UUID[] ARMOR_MODIFIER_UUID_PER_SLOT;
    @Shadow @Final @Mutable private Multimap<Attribute, AttributeModifier> defaultModifiers;
    @Shadow @Final protected float knockbackResistance;
    @Shadow @Final protected ArmorMaterial material;

    @Shadow @Final protected EquipmentSlot slot;

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void constructor(ArmorMaterial material, EquipmentSlot slot, Item.Properties settings, CallbackInfo ci) {
        UUID uUID = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
        if (material == AdditionalMaterials.GILDED_NETHERITE_ARMOR_MATERIAL) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            this.defaultModifiers.forEach(builder::put);

            builder.put(
                    Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(uUID,
                            "Armor knockback resistance",
                            this.knockbackResistance,
                            AttributeModifier.Operation.ADDITION
                    )
            );

            this.defaultModifiers = builder.build();
        }
    }

    @Inject(method = "getDefense", at = @At(value = "RETURN"), cancellable = true)
    private void modifyDefense(CallbackInfoReturnable<Integer> cir) {
        System.out.println("modifyDefense");
        System.out.println(material);
        if (material == AdditionalMaterials.GILDED_NETHERITE_ARMOR_MATERIAL) {
            int index = slot.getIndex();
            System.out.println(index);
            if (index > 3) return;
            String path = GildedNetheriteArmorMaterial.NAME + "/" + slotIndexToName(index);
            System.out.println(path + ":defense");
            Integer defense = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.DEFENSE);
            if (defense != null) cir.setReturnValue(defense);
        } else if (material == AdditionalMaterials.ROSE_GOLD_ARMOR_MATERIAL) {
            int index = slot.getIndex();
            System.out.println(index);
            if (index > 3) return;
            String path = RoseGoldArmorMaterial.NAME + "/" + slotIndexToName(index);
            System.out.println(path + ":defense");
            Integer defense = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.DEFENSE);
            if (defense != null) cir.setReturnValue(defense);
        }
    }

    @Inject(method = "getToughness", at = @At(value = "RETURN"), cancellable = true)
    private void modifyToughness(CallbackInfoReturnable<Integer> cir) {
        System.out.println("modifyToughness");
        System.out.println(material);
        if (material == AdditionalMaterials.GILDED_NETHERITE_ARMOR_MATERIAL) {
            int index = slot.getIndex();
            System.out.println(index);
            if (index > 3) return;
            String path = GildedNetheriteArmorMaterial.NAME + "/" + slotIndexToName(index);
            System.out.println(path + ":toughness");
            Integer toughness = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.TOUGHNESS);
            if (toughness != null) cir.setReturnValue(toughness);
        } else if (material == AdditionalMaterials.ROSE_GOLD_ARMOR_MATERIAL) {
            int index = slot.getIndex();
            System.out.println(index);
            if (index > 3) return;
            String path = RoseGoldArmorMaterial.NAME + "/" + slotIndexToName(index);
            System.out.println(path + ":toughness");
            Integer toughness = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.TOUGHNESS);
            if (toughness != null) cir.setReturnValue(toughness);
        }
    }

    private String slotIndexToName(int slot) {
        if (slot == 0) return "boots";
        if (slot == 1) return "leggings";
        if (slot == 2) return "chestplate";
        if (slot == 3) return "helmet";
        return null;
    }
}
