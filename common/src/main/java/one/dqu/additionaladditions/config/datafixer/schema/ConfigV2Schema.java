package one.dqu.additionaladditions.config.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.type.ToolItemConfig;

import java.util.Map;
import java.util.function.Supplier;

public class ConfigV2Schema extends Schema {
    public ConfigV2Schema(int versionKey, Schema parent) {
        super(versionKey, parent);
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
        super.registerTypes(schema, entityTypes, blockEntityTypes);

        schema.registerType(false, ConfigProperty.typeReference(ToolItemConfig.class), () ->
                DSL.allWithRemainder(
                        DSL.field("attack_speed", DSL.constType(DSL.floatType())),
                        DSL.field("attack_damage", DSL.constType(DSL.floatType())),
                        DSL.field("block_break_speed", DSL.constType(DSL.floatType())),
                        DSL.field("durability", DSL.constType(DSL.intType()))
                )
        );
    }
}
