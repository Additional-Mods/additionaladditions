package one.dqu.additionaladditions.config.neoforge;

import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ConfigLoaderImpl {
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
