package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
    private final static boolean isFeatureEnabled = Config.getBool(ConfigValues.AMETHYST_LAMP);

    @Inject(method = "checkDespawn", at = @At("TAIL"))
    public void checkDespawn(CallbackInfo ci) {
        if (!isFeatureEnabled) return;
        if (this.tickCount > 0 || !shouldDespawnInPeaceful()) return;
        if (isLampNearby((int)this.getX(), (int)this.getY(), (int)this.getZ(), 8)) {
            this.discard();
        }
    }

    public boolean isLampNearby(int x, int y, int z, int r) {
        Iterable<BlockPos> poses = BlockPos.betweenClosed(x-r, y-r, z-r, x+r, y+r, z+r);
        for (BlockPos pos : poses) {
            if (level.getBlockState(pos).is(AdditionalBlocks.AMETHYST_LAMP)) {
                if (level.getBlockState(pos).getValue(BlockStateProperties.LIT)) return true;
            }
        }
        return false;
    }
}