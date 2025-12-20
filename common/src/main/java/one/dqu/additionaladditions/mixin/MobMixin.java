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
 * Handles mob despawning near tinted redstone lamps.
 */
@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
    @Shadow protected abstract boolean shouldDespawnInPeaceful();

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "checkDespawn", at = @At("TAIL"))
    public void checkDespawn(CallbackInfo ci) {
        if (!Config.TINTED_REDSTONE_LAMP.get().enabled()) return;
        if (this.tickCount > 0 || !shouldDespawnInPeaceful()) return;

        float chance = Config.TINTED_REDSTONE_LAMP.get().chance();
        int range = Config.TINTED_REDSTONE_LAMP.get().range();

        PoiManager poiManager = ((ServerLevel)level()).getPoiManager();
        long count = poiManager.getCountInRange(
                (poiType) -> poiType.is(AABlocks.AMETHYST_LAMP_POI_KEY),
                blockPosition(), range, PoiManager.Occupancy.ANY
        );

        if (count > 0 && getRandom().nextFloat() < chance) {
            this.discard();
        }
    }
}