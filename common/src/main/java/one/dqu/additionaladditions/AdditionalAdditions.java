package one.dqu.additionaladditions;

import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.io.ConfigLoader;
import one.dqu.additionaladditions.core.util.ModCompatibility;
import one.dqu.additionaladditions.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
TODOs:
- make sure datagen generates stuff properly
- make sure All old manual data that datagen handles is removed
- maybe loottable datagen
- maybe furnace/cooking recipes datagen

- test for any remaining issues on 26.1 port
 */
public final class AdditionalAdditions {
    public static final String NAMESPACE = "additionaladditions";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);
    public static final boolean DATAGEN = System.getProperty("fabric-api.datagen") != null
            || System.getProperty("additionaladditions.datagen") != null;

    public static void init() {
        ConfigLoader.load();

        AATags.registerAll();
        AAMisc.registerAll();
        AAEntities.registerAll();
        AAItems.registerAll();
        AABlocks.registerAll();
        AAGameTests.registerAll();

        ModCompatibility.add(
                () -> Config.COPPER_PATINA.get().enabled(),
                "Alternate Current mod is incompatible with the Copper Patina feature. Please disable Copper Patina in the config or uninstall Alternate Current.",
                "alternate-current", "alternate_current"
        );
    }
}

