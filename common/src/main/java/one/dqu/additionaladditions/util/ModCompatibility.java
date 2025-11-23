package one.dqu.additionaladditions.util;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.util.function.Supplier;

public class ModCompatibility {
    @ExpectPlatform
    public static void add(Supplier<Boolean> condition, String description, String... modids) {
        throw new AssertionError();
    }
}
