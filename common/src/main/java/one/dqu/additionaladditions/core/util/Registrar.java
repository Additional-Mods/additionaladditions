package one.dqu.additionaladditions.core.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public abstract class Registrar<T> {
    @ExpectPlatform
    public static void defer(Runnable runnable) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> Registrar<T> wrap(Registry<T> registry) {
        throw new AssertionError();
    }

    public abstract <I extends T> Supplier<I> register(Identifier id, Supplier<I> supplier);
}
