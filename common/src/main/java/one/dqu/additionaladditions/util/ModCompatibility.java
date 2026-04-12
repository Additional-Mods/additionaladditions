package one.dqu.additionaladditions.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ModCompatibility {
    @ExpectPlatform
    public static void add(Supplier<Boolean> condition, String description, String... modids) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModPresent(String... modids) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isClientSide() {
        throw new AssertionError();
    }

    public static class Client {
        public static Level getClientLevel() {
            return Minecraft.getInstance().level;
        }
    }
}
