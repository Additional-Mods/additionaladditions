package dqu.additionaladditions;

import net.fabricmc.api.ModInitializer;

public class AdditionalAdditions implements ModInitializer {
    public static boolean zoom = false;

    @Override
    public void onInitialize() {
        Registrar.registerBlocks();
        Registrar.registerItems();
        Registrar.registerOther();
    }
}
