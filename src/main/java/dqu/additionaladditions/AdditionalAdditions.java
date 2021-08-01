package dqu.additionaladditions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdditionalAdditions implements ModInitializer {
    public static final Identifier PacketID = new Identifier(Registrar.namespace, "spawn_packet");
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean zoom = false;
    public static boolean lithiumInstalled = false;

    @Override
    public void onInitialize() {
        lithiumInstalled = FabricLoader.getInstance().isModLoaded("lithium");

        Config.load();
        Registrar.registerBlocks();
        Registrar.registerItems();
        Registrar.registerOther();
    }
}
