package one.dqu.additionaladditions.material;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public enum AnimalArmorType {
    HORSE,
    NAUTILUS;

    public Holder<SoundEvent> equipSound() {
        return switch (this) {
            case HORSE -> SoundEvents.HORSE_ARMOR;
            case NAUTILUS -> SoundEvents.ARMOR_EQUIP_NAUTILUS;
        };
    }

    public Holder<SoundEvent> unequipSound() {
        return switch (this) {
            case HORSE -> SoundEvents.HORSE_ARMOR_UNEQUIP;
            case NAUTILUS -> SoundEvents.ARMOR_UNEQUIP_NAUTILUS;
        };
    }

    public TagKey<EntityType<?>> canWearTag() {
        return switch (this) {
            case HORSE -> EntityTypeTags.CAN_WEAR_HORSE_ARMOR;
            case NAUTILUS -> EntityTypeTags.CAN_WEAR_NAUTILUS_ARMOR;
        };
    }
}
