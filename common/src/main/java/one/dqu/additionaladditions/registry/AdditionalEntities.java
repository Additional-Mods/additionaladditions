package one.dqu.additionaladditions.registry;

import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.entity.GlowStickEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class AdditionalEntities {
    public static final Supplier<EntityType<GlowStickEntity>> GLOW_STICK_ENTITY_ENTITY_TYPE =
            AdditionalRegistries.ENTITY_TYPES.register(
                    ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "glow_stick_entity"),
                    () -> EntityType.Builder.of(
                                    (EntityType<GlowStickEntity> type, Level level) -> new GlowStickEntity(type, level),
                                    MobCategory.MISC
                            )
                            .sized(0.25f, 0.25f).updateInterval(4)
                            .clientTrackingRange(10).build("glow_stick_entity")
            );

    public static void registerAll() {

    }
}
