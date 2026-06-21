package one.dqu.additionaladditions.registry;

import net.minecraft.world.entity.EntityType;
import one.dqu.additionaladditions.feature.glow_stick.GlowStickContent;
import one.dqu.additionaladditions.feature.glow_stick.GlowStickEntity;
import one.dqu.additionaladditions.feature.rope.RopeArrow;
import one.dqu.additionaladditions.feature.rope.RopeContent;

import java.util.function.Supplier;

public class AAEntities {
    public static final Supplier<EntityType<GlowStickEntity>> GLOW_STICK = GlowStickContent.glowStickEntity();

    public static final Supplier<EntityType<RopeArrow>> ROPE_ARROW = RopeContent.ropeArrowEntity();

    public static void registerAll() {

    }
}
