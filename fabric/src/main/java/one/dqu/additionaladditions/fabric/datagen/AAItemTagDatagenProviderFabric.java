package one.dqu.additionaladditions.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import one.dqu.additionaladditions.core.datagen.AAItemTagDatagenProvider;

import java.util.concurrent.CompletableFuture;

// fabric requires tag providers to extend FabricTagsProvider
public class AAItemTagDatagenProviderFabric extends FabricTagsProvider.ItemTagsProvider {
    public AAItemTagDatagenProviderFabric(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        AAItemTagDatagenProvider.generate(this::getOrCreateRawBuilder);
    }
}
