package one.dqu.additionaladditions.config.datafixer.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.type.*;

import java.util.Map;
import java.util.function.Supplier;

public class ConfigV1Schema extends Schema {
    public ConfigV1Schema(int versionKey, Schema parent) {
        super(versionKey, parent);
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
        schema.registerType(true, () -> "crashes_without_this", DSL::remainder);

        schema.registerType(false, ConfigProperty.typeReference(FoodConfig.class), () ->
                DSL.fields(
                        "enabled", DSL.constType(DSL.bool()),
                        "food", DSL.and(
                                DSL.fields(
                                        "nutrition", DSL.constType(DSL.intType()),
                                        "saturation", DSL.constType(DSL.floatType())
                                ),
                                DSL.optionalFields(
                                        "can_always_eat", DSL.constType(DSL.bool()),
                                        "eat_seconds", DSL.constType(DSL.floatType())
                                )
                        )

                )
        );

        schema.registerType(false, ConfigProperty.typeReference(FeatureConfig.class), () ->
                DSL.fields(
                        "enabled", DSL.constType(DSL.bool())
                )
        );

        schema.registerType(false, ConfigProperty.typeReference(DamageableItemConfig.class), () ->
                DSL.fields(
                        "enabled", DSL.constType(DSL.bool()),
                        "durability", DSL.constType(DSL.intType())
                )
        );

        schema.registerType(false, ConfigProperty.typeReference(WateringCanConfig.class), () ->
                DSL.fields(
                        "enabled", DSL.constType(DSL.bool()),
                        "max_water_level", DSL.constType(DSL.intType())
                )
        );

        schema.registerType(false, ConfigProperty.typeReference(BarometerConfig.class), () ->
                DSL.fields(
                        "enabled", DSL.constType(DSL.bool()),
                        "display_elevation_always", DSL.constType(DSL.bool())
                )
        );

        schema.registerType(false, ConfigProperty.typeReference(TintedRedstoneLampConfig.class), () ->
                DSL.fields(
                        "enabled", DSL.constType(DSL.bool()),
                        "despawn_chance", DSL.constType(DSL.floatType()),
                        "despawn_range", DSL.constType(DSL.intType())
                )
        );

        schema.registerType(false, ConfigProperty.typeReference(ArmorItemConfig.class), () ->
                DSL.fields(
                        "protection", DSL.constType(DSL.intType()),
                        "durability", DSL.constType(DSL.intType())
                )
        );

        schema.registerType(false, ConfigProperty.typeReference(ArmorMaterialConfig.class), () ->
                DSL.and(
                        DSL.fields(
                                "toughness", DSL.constType(DSL.floatType()),
                                "knockback_resistance", DSL.constType(DSL.floatType()),
                                "enchantability", DSL.constType(DSL.intType())
                        ),
                        DSL.fields(
                                "equip_sound", DSL.optionalFields(
                                        "name", DSL.constType(DSL.string())
                                ),
                                "repair_ingredient", DSL.optionalFields(
                                        "item", DSL.constType(DSL.string())
                                )
                        )
                )
        );

        schema.registerType(false, ConfigProperty.typeReference(BodyArmorItemConfig.class), () ->
                DSL.fields(
                        "protection", DSL.constType(DSL.intType())
                )
        );

        schema.registerType(false, ConfigProperty.typeReference(SwordItemConfig.class), () ->
                DSL.fields(
                        "attack_speed", DSL.constType(DSL.floatType()),
                        "attack_damage", DSL.constType(DSL.intType()),
                        "durability", DSL.constType(DSL.intType())
                )
        );

        schema.registerType(false, ConfigProperty.typeReference(ToolItemConfig.class), () ->
                DSL.fields(
                        "attack_speed", DSL.constType(DSL.floatType()),
                        "attack_damage", DSL.constType(DSL.floatType()),
                        "durability", DSL.constType(DSL.intType())
                )
        );
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        return Map.of();
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        return Map.of();
    }
}
