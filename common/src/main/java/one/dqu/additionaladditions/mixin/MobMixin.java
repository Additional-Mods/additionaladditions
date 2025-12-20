package one.dqu.additionaladditions.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.LevelAccessor;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.registry.AABlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Handles mob despawning near tinted redstone lamps.
 */
@Mixin(Mob.class)
public abstract class MobMixin {
    @Inject(method = "checkMobSpawnRules", at = @At("TAIL"), cancellable = true)
    private static void checkMobSpawnRules(EntityType<? extends Mob> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.TINTED_REDSTONE_LAMP.get().enabled()) return;
        if (!(levelAccessor instanceof ServerLevel level)) return;
        if (entityType.getCategory() != MobCategory.MONSTER) return;
        if (mobSpawnType != MobSpawnType.NATURAL &&
            mobSpawnType != MobSpawnType.CHUNK_GENERATION &&
            mobSpawnType != MobSpawnType.PATROL
        ) return;

        float chance = Config.TINTED_REDSTONE_LAMP.get().chance();
        int range = Config.TINTED_REDSTONE_LAMP.get().range();

        PoiManager poiManager = level.getPoiManager();

        boolean hasLamp = poiManager.findAll(
                (poiType) -> poiType.is(AABlocks.AMETHYST_LAMP_POI_KEY),
                (pos) -> {
                    int dx = Math.abs(pos.getX() - blockPos.getX());
                    int dy = Math.abs(pos.getY() - blockPos.getY());
                    int dz = Math.abs(pos.getZ() - blockPos.getZ());
                    return (dx + dy + dz) <= range;
                },
                blockPos, range, PoiManager.Occupancy.ANY
        ).findAny().isPresent();

        if (hasLamp && randomSource.nextFloat() < chance) {
            cir.setReturnValue(false);
        }
    }
}