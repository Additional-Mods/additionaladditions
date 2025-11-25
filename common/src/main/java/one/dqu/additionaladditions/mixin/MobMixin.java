package one.dqu.additionaladditions.mixin;

import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.registry.AABlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Handles mob despawning near amethyst lamps.
 */
@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
    @Shadow protected abstract boolean shouldDespawnInPeaceful();

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "checkDespawn", at = @At("TAIL"))
    public void checkDespawn(CallbackInfo ci) {
        if (!Config.AMETHYST_LAMP.get().enabled()) return;
        if (this.tickCount > 0 || !shouldDespawnInPeaceful()) return;

        PoiManager poiManager = ((ServerLevel)level()).getPoiManager();
        long count = poiManager.getCountInRange(
                (poiType) -> {
                    return poiType.is(AABlocks.AMETHYST_LAMP_POI_RL);
                },
                blockPosition(), 8, PoiManager.Occupancy.ANY
        );

        float chance = Config.AMETHYST_LAMP.get().chance();
        if (count > 0 && getRandom().nextFloat() < chance) {
            this.discard();
        }
    }
}