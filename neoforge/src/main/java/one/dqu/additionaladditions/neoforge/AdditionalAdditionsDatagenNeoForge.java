package one.dqu.additionaladditions.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.gametest.datagen.AAGameTestDatagen;

@EventBusSubscriber(modid = AdditionalAdditions.NAMESPACE)
public class AdditionalAdditionsDatagenNeoForge {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Server event) {
        event.createDatapackRegistryObjects(AAGameTestDatagen.registryBuilder());
    }
}
