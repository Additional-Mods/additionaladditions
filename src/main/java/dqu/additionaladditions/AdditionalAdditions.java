package dqu.additionaladditions;

import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionalAdditions implements ModInitializer {
    public static final String namespace = "additionaladditions";
    public static final Logger LOGGER = LoggerFactory.getLogger(namespace);
    public static boolean zoom = false;
    public static boolean lithiumInstalled = false;

    @Override
    public void onInitialize() {
        lithiumInstalled = FabricLoader.getInstance().isModLoaded("lithium");
        if (!Config.initialized) {
            Config.load();
        }

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(BehaviourManager.INSTANCE);

        AdditionalItems.registerAll();
        AdditionalBlocks.registerAll();
        AdditionalEntities.registerAll();
        AdditionalEnchantments.registerAll();
        AdditionalMaterials.registerAll();
        AdditionalPotions.registerAll();
        AdditionalMusicDiscs.registerAll();

        // LambDynamicLights integration
        DynamicLightHandlers.registerDynamicLightHandler(AdditionalEntities.GLOW_STICK_ENTITY_ENTITY_TYPE, entity -> 12);
    }
}
