package one.dqu.additionaladditions.neoforge;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.core.datagen.*;

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
}
