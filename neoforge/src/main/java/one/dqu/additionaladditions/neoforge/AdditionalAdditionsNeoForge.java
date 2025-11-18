package one.dqu.additionaladditions.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.common.NeoForge;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.util.neoforge.RegistrarImpl;

@Mod(AdditionalAdditions.NAMESPACE)
public final class AdditionalAdditionsNeoForge {
    public AdditionalAdditionsNeoForge(IEventBus modEventBus) {
        AdditionalAdditions.init();

        RegistrarImpl.ITEM.register(modEventBus);
        RegistrarImpl.BLOCK.register(modEventBus);
        RegistrarImpl.ENTITY_TYPE.register(modEventBus);
        RegistrarImpl.DATA_COMPONENT_TYPE.register(modEventBus);
        RegistrarImpl.RECIPE_SERIALIZER.register(modEventBus);

//        NeoForge.EVENT_BUS.register(new AdditionalEvents());
    }
}
