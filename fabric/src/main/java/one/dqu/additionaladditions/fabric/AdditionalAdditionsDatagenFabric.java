package one.dqu.additionaladditions.fabric;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.core.datagen.*;

import java.util.concurrent.CompletableFuture;

public class AdditionalAdditionsDatagenFabric implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(RegistryProvider::new);

        pack.addProvider(ModelProvider::new);
        pack.addProvider(AAItemTagDatagenProvider::new);
        pack.addProvider(AABlockTagDatagenProvider::new);
        pack.addProvider(RecipeProviderRunner::new);
    }

    private static class ModelProvider extends FabricModelProvider {
        ModelProvider(FabricPackOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators gen) {
            AABlockDatagen.generateBlockStateModels(gen);
        }

        @Override
        public void generateItemModels(ItemModelGenerators gen) {
            AAItemDatagen.generateItemModels(gen);
        }
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.TEST_ENVIRONMENT, AAGameTestDatagen::bootstrapEnvironments);
        registryBuilder.add(Registries.TEST_INSTANCE, AAGameTestDatagen::bootstrap);
    }

    private static class RecipeProviderRunner extends FabricRecipeProvider {
        RecipeProviderRunner(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new AARecipeDatagenProvider(registries, output);
        }

        @Override
        public String getName() {
            return AdditionalAdditions.NAMESPACE + " Recipes";
        }
    }

    private static class RegistryProvider extends FabricDynamicRegistryProvider {
        RegistryProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
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
