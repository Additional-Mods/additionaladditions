package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.entity.GlowStickEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AdditionalEntities {
    public static final EntityType<GlowStickEntity> GLOW_STICK_ENTITY_ENTITY_TYPE = FabricEntityTypeBuilder.<GlowStickEntity>create(SpawnGroup.MISC, GlowStickEntity::new)
            .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
            .trackRangeBlocks(4).trackedUpdateRate(10).build();

    public static void registerAll() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(AdditionalAdditions.namespace, "glow_stick"), GLOW_STICK_ENTITY_ENTITY_TYPE);
    }
}
