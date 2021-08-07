package dqu.additionaladditions;

import dqu.additionaladditions.block.BlockRegistry;
import dqu.additionaladditions.enchantment.EnchantmentRegistry;
import dqu.additionaladditions.entity.EntityRegistry;
import dqu.additionaladditions.item.ItemRegistry;
import dqu.additionaladditions.material.MaterialRegistry;
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
        ItemRegistry.registerAll();
        BlockRegistry.registerAll();
        EntityRegistry.registerAll();
        EnchantmentRegistry.registerAll();
        MaterialRegistry.registerAll();
    }
}
