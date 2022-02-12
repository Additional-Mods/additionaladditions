package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.value.ConfigValues;
import dqu.additionaladditions.registry.AdditionalBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    @Shadow protected abstract boolean isDisallowedInPeaceful();

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    private final static boolean isFeatureEnabled = Config.getBool(ConfigValues.AMETHYST_LAMP);

    @Inject(method = "checkDespawn", at = @At("TAIL"))
    public void checkDespawn(CallbackInfo ci) {
        if (!isFeatureEnabled) return;
        if (this.age > 0 || !isDisallowedInPeaceful()) return;
        if (isLampNearby((int)this.getX(), (int)this.getY(), (int)this.getZ(), 8)) {
            this.discard();
        }
    }

    public boolean isLampNearby(int x, int y, int z, int r) {
        Iterable<BlockPos> poses = BlockPos.iterate(x-r, y-r, z-r, x+r, y+r, z+r);
        for (BlockPos pos : poses) {
            if (world.getBlockState(pos).isOf(AdditionalBlocks.AMETHYST_LAMP)) {
                if (world.getBlockState(pos).get(Properties.LIT)) return true;
            }
        }
        return false;
    }
}