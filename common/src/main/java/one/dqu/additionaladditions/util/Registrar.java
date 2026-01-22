package one.dqu.additionaladditions.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

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

    public abstract <I extends T> Supplier<I> register(ResourceLocation id, Supplier<I> supplier);
}
