package one.dqu.additionaladditions.mixin.neoforge;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import one.dqu.additionaladditions.util.LootTableExtension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(LootTable.class)
public class LootTableMixin implements LootTableExtension {
    @Shadow
    @Final
    private List<LootPool> pools;

    @Override
    public void additionaladditions$clearPools() {
        this.pools.clear();
    }
}
