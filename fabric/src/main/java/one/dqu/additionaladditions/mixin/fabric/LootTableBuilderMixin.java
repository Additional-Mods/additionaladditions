package one.dqu.additionaladditions.mixin.fabric;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import one.dqu.additionaladditions.util.LootTableExtension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LootTable.Builder.class)
public class LootTableBuilderMixin implements LootTableExtension {
    @Mutable
    @Shadow
    @Final
    private ImmutableList.Builder<LootPool> pools;

    @Override
    public void additionaladditions$clearPools() {
        this.pools = ImmutableList.builder();
    }
}
