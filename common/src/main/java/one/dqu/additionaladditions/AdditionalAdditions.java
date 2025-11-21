package one.dqu.additionaladditions;

import one.dqu.additionaladditions.config.ConfigLoader;
import one.dqu.additionaladditions.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AdditionalAdditions {
    public static final String NAMESPACE = "additionaladditions";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);
    public static boolean zoom = false;

    public static void init() {
        ConfigLoader.load();

        AdditionalMisc.registerAll();
        AdditionalEntities.registerAll();
        AdditionalItems.registerAll();
        AdditionalBlocks.registerAll();
    }
}
