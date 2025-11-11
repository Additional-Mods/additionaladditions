package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.entity.GlowStickEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class AdditionalEntities {
    public static final EntityType<GlowStickEntity> GLOW_STICK_ENTITY_ENTITY_TYPE = FabricEntityTypeBuilder.<GlowStickEntity>create(MobCategory.MISC, GlowStickEntity::new)
            .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
            .trackRangeBlocks(4).trackedUpdateRate(10).build();

    public static void registerAll() {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.tryBuild(AdditionalAdditions.namespace, "glow_stick"), GLOW_STICK_ENTITY_ENTITY_TYPE);
    }
}
