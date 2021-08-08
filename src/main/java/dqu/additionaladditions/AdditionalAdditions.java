package dqu.additionaladditions;

import dqu.additionaladditions.registry.AdditionalBlocks;
import dqu.additionaladditions.registry.AdditionalEnchantments;
import dqu.additionaladditions.registry.AdditionalEntities;
import dqu.additionaladditions.registry.AdditionalItems;
import dqu.additionaladditions.registry.AdditionalMaterials;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdditionalAdditions implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String namespace = "additionaladditions";
    public static boolean zoom = false;
    public static boolean lithiumInstalled = false;

    @Override
    public void onInitialize() {
        lithiumInstalled = FabricLoader.getInstance().isModLoaded("lithium");

        Config.load();
        AdditionalItems.registerAll();
        AdditionalBlocks.registerAll();
        AdditionalEntities.registerAll();
        AdditionalEnchantments.registerAll();
        AdditionalMaterials.registerAll();
    }
}
