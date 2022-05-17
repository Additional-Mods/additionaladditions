package dqu.additionaladditions;

//import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
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

    @Override
    public void onInitialize() {
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

        // TODO: LambDynamicLights integration
//        if (FabricLoader.getInstance().isModLoaded("lambdynlights")) {
//            DynamicLightHandlers.registerDynamicLightHandler(AdditionalEntities.GLOW_STICK_ENTITY_ENTITY_TYPE, entity -> 12);
//        }
    }
}
