package one.dqu.additionaladditions.config.datafixer;

import one.dqu.additionaladditions.AdditionalAdditions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;

/**
 * This handles file renames and deletions between config versions.
 * DFU cannot handle this because it only handles data
 */
public class ConfigFileRelocator {
    private static final List<Migration> MIGRATIONS = List.of(
    );

    public static void update(Path path, int version) {
        var sorted = MIGRATIONS.stream()
                .filter(m -> m.version() > version)
                .sorted(Comparator.comparingInt(Migration::version))
                .toList();
        for (Migration migration : sorted) {
            for (Handler handler : migration.handlers()) {
                try {
                    handler.run(path);
                } catch (IOException e) {
                    AdditionalAdditions.LOGGER.error("[{}] Failed to migrate config file: {}", AdditionalAdditions.NAMESPACE, e);
                }
            }
        }
    }

    private record Migration(int version, List<Handler> handlers) {
        private Migration(int version, Handler... handlers) {
            this(version, List.of(handlers));
        }
    }

    private interface Handler {
        void run(Path path) throws IOException;
    }

    private static Handler move(String oldPath, String newPath) {
        return path -> {
            Path from = path.resolve(oldPath + ".json");
            Path to = path.resolve(newPath + ".json");
            if (Files.exists(from)) {
                Files.createDirectories(to.getParent());
                Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
            }
        };
    }

    private static Handler delete(String targetPath) {
        return path -> {
            Path target = path.resolve(targetPath + ".json");
            Files.deleteIfExists(target);
        };
    }
}
