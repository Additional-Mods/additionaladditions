package one.dqu.additionaladditions;

import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.ConfigLoader;
import one.dqu.additionaladditions.registry.*;
import one.dqu.additionaladditions.util.ModCompatibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AdditionalAdditions {
    public static final String NAMESPACE = "additionaladditions";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);
    public static boolean zoom = false;

    public static void init() {
        ConfigLoader.load();

        AAMisc.registerAll();
        AAEntities.registerAll();
        AAItems.registerAll();
        AABlocks.registerAll();

        ModCompatibility.add(
                () -> Config.COPPER_PATINA.get().enabled(),
                "Alternate Current mod is incompatible with the Copper Patina feature. Please disable Copper Patina in the config or uninstall Alternate Current.",
                "alternate-current", "alternate_current"
        );
    }
}

