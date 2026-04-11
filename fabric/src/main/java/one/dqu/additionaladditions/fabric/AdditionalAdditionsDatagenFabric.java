package one.dqu.additionaladditions.fabric;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.datagen.AAGameTestDatagen;

import java.util.concurrent.CompletableFuture;

public class AdditionalAdditionsDatagenFabric implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(RegistryProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.TEST_INSTANCE, AAGameTestDatagen::bootstrap);
    }

    private static class RegistryProvider extends FabricDynamicRegistryProvider {
        RegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider registries, Entries entries) {
            entries.addAll(registries.lookupOrThrow(Registries.TEST_INSTANCE));
        }

        @Override
        public String getName() {
            return AdditionalAdditions.NAMESPACE;
        }
    }
}