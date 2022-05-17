package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalBlocks;
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

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
    @Shadow protected abstract boolean shouldDespawnInPeaceful();

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }
    private final static boolean isFeatureEnabled = Config.getBool(ConfigValues.AMETHYST_LAMP, "enabled");

    @Inject(method = "checkDespawn", at = @At("TAIL"))
    public void checkDespawn(CallbackInfo ci) {
        if (!isFeatureEnabled) return;
        if (this.tickCount > 0 || !shouldDespawnInPeaceful()) return;

        PoiManager poiManager = ((ServerLevel)level).getPoiManager();
        long count = poiManager.getCountInRange(
                (poiType) -> poiType == AdditionalBlocks.AMETHYST_LAMP_POI,
                blockPosition(),
                8,
                PoiManager.Occupancy.ANY
        );

        Float chance = Config.get(ConfigValues.AMETHYST_LAMP, "despawnChance");
        if (count > 0 && chance != null && getRandom().nextFloat() < chance) {
            this.discard();
        }
    }
}