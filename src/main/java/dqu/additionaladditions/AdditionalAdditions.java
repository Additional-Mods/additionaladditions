package dqu.additionaladditions;

import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.misc.CreativeAdder;
import dqu.additionaladditions.misc.LootHandler;
import dqu.additionaladditions.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionalAdditions implements ModInitializer {
    public static final String namespace = "additionaladditions";
    public static final Logger LOGGER = LoggerFactory.getLogger(namespace);
    public static boolean zoom = false;

    @Override
    public void onInitialize() {
        if (!Config.initialized) {
            Config.load();
        }

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(BehaviourManager.INSTANCE);

        AdditionalItems.registerAll();
        AdditionalBlocks.registerAll();
        AdditionalEntities.registerAll();
        addItemsToCreative();

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            LootHandler.handle(key.location(), tableBuilder, registries);
        });

        LootHandler.postInit();

//        if (FabricLoader.getInstance().isModLoaded("lambdynlights")) {
//            DynamicLightHandlers.registerDynamicLightHandler(AdditionalEntities.GLOW_STICK_ENTITY_ENTITY_TYPE, entity -> 12);
//        }
    }

    public void addItemsToCreative() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(CreativeAdder.TOOLS_AND_UTILITIES::pushAllTo);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(CreativeAdder.INGREDIENTS::pushAllTo);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(CreativeAdder.BUILDING_BLOCKS::pushAllTo);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(CreativeAdder.REDSTONE_BLOCKS::pushAllTo);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(CreativeAdder.FOOD_AND_DRINKS::pushAllTo);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(CreativeAdder.FUNCTIONAL_BLOCKS::pushAllTo);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(CreativeAdder.COMBAT::pushAllTo);
    }
}
