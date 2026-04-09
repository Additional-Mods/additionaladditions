package one.dqu.additionaladditions.config.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.type.FoodConfig;
import one.dqu.additionaladditions.config.type.MaterialConfig;

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
    }
}
