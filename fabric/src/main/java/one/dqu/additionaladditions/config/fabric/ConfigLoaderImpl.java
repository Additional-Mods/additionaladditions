package one.dqu.additionaladditions.config.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class ConfigLoaderImpl {
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
