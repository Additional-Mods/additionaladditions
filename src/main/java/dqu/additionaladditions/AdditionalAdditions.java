package dqu.additionaladditions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class AdditionalAdditions implements ModInitializer {
    public static boolean zoom = false;
    public static boolean lithiumInstalled = false;

    @Override
    public void onInitialize() {
        lithiumInstalled = FabricLoader.getInstance().isModLoaded("lithium");

        Registrar.registerBlocks();
        Registrar.registerItems();
        Registrar.registerOther();
    }
}
