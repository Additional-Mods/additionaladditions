package one.dqu.additionaladditions.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import one.dqu.additionaladditions.core.datagen.AABlockTagDatagenProvider;

import java.util.concurrent.CompletableFuture;

// fabric requires tag providers to extend FabricTagsProvider
public class AABlockTagDatagenProviderFabric extends FabricTagsProvider.BlockTagsProvider {
    public AABlockTagDatagenProviderFabric(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        AABlockTagDatagenProvider.generate(this::getOrCreateRawBuilder);
    }
}
