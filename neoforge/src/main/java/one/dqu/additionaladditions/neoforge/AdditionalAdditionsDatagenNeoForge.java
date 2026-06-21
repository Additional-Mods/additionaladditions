package one.dqu.additionaladditions.neoforge;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.core.datagen.*;
import one.dqu.additionaladditions.core.util.neoforge.CompostingImpl;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@EventBusSubscriber(modid = AdditionalAdditions.NAMESPACE)
public class AdditionalAdditionsDatagenNeoForge {
    // everything runs on client datagen because either neoforge or architectury are being silly
    // combined data run doesn't work and doing a server run clears client run data
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        event.createDatapackRegistryObjects(AAGameTestDatagen.registryBuilder());
        event.createProvider(AAModelProvider::new);
        event.createProvider(AAItemTagDatagenProvider::new);
        event.createProvider(AABlockTagDatagenProvider::new);
        event.createProvider(AANeoForgeRecipeProvider::new);
        event.createProvider(AADataMapProvider::new);
    }

    private static class AAModelProvider extends ModelProvider {
        AAModelProvider(PackOutput output) {
            super(output, AdditionalAdditions.NAMESPACE);
        }

        @Override
        protected void registerModels(BlockModelGenerators blocks, ItemModelGenerators items) {
            AABlockDatagen.generateBlockStateModels(blocks);
            AAItemDatagen.generateItemModels(items);
        }

        @Override
        protected Stream<? extends Holder<Block>> getKnownBlocks() {
            return AABlockDatagen.knownBlocks();
        }

        @Override
        protected Stream<? extends Holder<Item>> getKnownItems() {
            return AAItemDatagen.knownItems();
        }
    }

    // Neoforge-specific files
    private static class AADataMapProvider extends DataMapProvider {
        protected AADataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(packOutput, lookupProvider);
        }

        @Override
        protected void gather(HolderLookup.Provider provider) {
            var builder = builder(NeoForgeDataMaps.COMPOSTABLES);
            CompostingImpl.getCompostables().forEach((item, chance) -> {
                builder.add(item.get().builtInRegistryHolder().getKey(), new Compostable(chance), false);
            });
        }
    }
}
