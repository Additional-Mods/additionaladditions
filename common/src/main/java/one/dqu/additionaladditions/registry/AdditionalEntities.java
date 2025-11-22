package one.dqu.additionaladditions.registry;

import net.minecraft.world.level.Level;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.entity.GlowStickEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import one.dqu.additionaladditions.entity.RopeArrow;

import java.util.function.Supplier;

public class AdditionalEntities {
    public static final Supplier<EntityType<GlowStickEntity>> GLOW_STICK =
            AdditionalRegistries.ENTITY_TYPES.register(
                    ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "glow_stick"),
                    () -> EntityType.Builder.of(
                                    (EntityType<GlowStickEntity> type, Level level) -> new GlowStickEntity(type, level),
                                    MobCategory.MISC
                            )
                            .sized(0.25f, 0.25f).updateInterval(20)
                            .clientTrackingRange(4).build("glow_stick")
            );

    public static final Supplier<EntityType<RopeArrow>> ROPE_ARROW =
            AdditionalRegistries.ENTITY_TYPES.register(
                    ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rope_arrow"),
                    () -> EntityType.Builder.of(
                                    (EntityType<RopeArrow> type, Level level) -> new RopeArrow(type, level),
                                    MobCategory.MISC
                            )
                            .sized(0.5f, 0.5f).eyeHeight(0.13f).updateInterval(20)
                            .clientTrackingRange(4).build("rope_arrow")
            );

    public static void registerAll() {

    }
}
