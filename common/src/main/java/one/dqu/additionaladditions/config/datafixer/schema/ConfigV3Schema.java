package one.dqu.additionaladditions.config.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.type.FoodConfig;
import one.dqu.additionaladditions.config.type.MaterialConfig;
import one.dqu.additionaladditions.config.type.SpearItemConfig;

import java.util.Map;
import java.util.function.Supplier;

public class ConfigV3Schema extends Schema {
    public ConfigV3Schema(int versionKey, Schema parent) {
        super(versionKey, parent);
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
        super.registerTypes(schema, entityTypes, blockEntityTypes);

        schema.registerType(false, ConfigProperty.typeReference(MaterialConfig.class), () ->
                DSL.allWithRemainder(
                        DSL.field("toughness", DSL.constType(DSL.floatType())),
                        DSL.field("knockback_resistance", DSL.constType(DSL.floatType())),
                        DSL.field("enchantability", DSL.constType(DSL.intType())),
                        DSL.field("equip_sound", DSL.fields(
                                "sound_event", DSL.constType(DSL.string())
                        )),
                        DSL.field("incorrect_blocks_for_drops", DSL.fields(
                                "block_tag", DSL.constType(DSL.string())
                        ))
                )
        );

        schema.registerType(false, ConfigProperty.typeReference(FoodConfig.class), () ->
                DSL.fields(
                        "enabled", DSL.constType(DSL.bool()),
                        "food", DSL.allWithRemainder(
                                DSL.field("nutrition", DSL.constType(DSL.intType())),
                                DSL.field("saturation", DSL.constType(DSL.floatType())),
                                DSL.optional(DSL.field("can_always_eat", DSL.constType(DSL.bool())))
                        )
                )
        );

        schema.registerType(false, ConfigProperty.typeReference(SpearItemConfig.class), () ->
                DSL.allWithRemainder(
                        DSL.field("attack_speed", DSL.constType(DSL.floatType())),
                        DSL.field("attack_damage", DSL.constType(DSL.floatType())),
                        DSL.field("durability", DSL.constType(DSL.intType())),
                        DSL.field("kinetic_weapon", DSL.allWithRemainder(
                                DSL.optional(DSL.field("contact_cooldown_ticks", DSL.constType(DSL.intType()))),
                                DSL.optional(DSL.field("delay_ticks", DSL.constType(DSL.intType()))),
                                DSL.optional(DSL.field("dismount_conditions", DSL.allWithRemainder(
                                        DSL.field("max_duration_ticks", DSL.constType(DSL.intType())),
                                        DSL.optional(DSL.field("min_speed", DSL.constType(DSL.floatType()))),
                                        DSL.optional(DSL.field("min_relative_speed", DSL.constType(DSL.floatType())))
                                ))),
                                DSL.optional(DSL.field("knockback_conditions", DSL.allWithRemainder(
                                        DSL.field("max_duration_ticks", DSL.constType(DSL.intType())),
                                        DSL.optional(DSL.field("min_speed", DSL.constType(DSL.floatType()))),
                                        DSL.optional(DSL.field("min_relative_speed", DSL.constType(DSL.floatType())))
                                ))),
                                DSL.optional(DSL.field("damage_conditions", DSL.allWithRemainder(
                                        DSL.field("max_duration_ticks", DSL.constType(DSL.intType())),
                                        DSL.optional(DSL.field("min_speed", DSL.constType(DSL.floatType()))),
                                        DSL.optional(DSL.field("min_relative_speed", DSL.constType(DSL.floatType())))
                                ))),
                                DSL.optional(DSL.field("forward_movement", DSL.constType(DSL.floatType()))),
                                DSL.optional(DSL.field("damage_multiplier", DSL.constType(DSL.floatType()))),
                                DSL.optional(DSL.field("sound", DSL.constType(DSL.string()))),
                                DSL.optional(DSL.field("hit_sound", DSL.constType(DSL.string())))
                        )),
                        DSL.field("piercing_weapon", DSL.allWithRemainder(
                                DSL.optional(DSL.field("deals_knockback", DSL.constType(DSL.bool()))),
                                DSL.optional(DSL.field("dismounts", DSL.constType(DSL.bool()))),
                                DSL.optional(DSL.field("sound", DSL.constType(DSL.string()))),
                                DSL.optional(DSL.field("hit_sound", DSL.constType(DSL.string())))
                        ))
                )
        );
    }
}
