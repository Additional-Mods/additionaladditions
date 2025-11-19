package one.dqu.additionaladditions.util.fabric;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import one.dqu.additionaladditions.util.Registrar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RegistrarImpl {
    private static final List<Runnable> DEFERRED = new ArrayList<>();
    private static final List<FabricRegistrar<?>> REGISTERS = new ArrayList<>();

    public static void defer(Runnable runnable) {
        DEFERRED.add(runnable);
    }

    public static void runDeferred() {
        DEFERRED.forEach(Runnable::run);
        DEFERRED.clear();
    }

    public static <T> Registrar<T> wrap(Registry<T> registry) {
        return new FabricRegistrar<>(registry);
    }

    private static class FabricRegistrar<T> extends Registrar<T> {
        private final Registry<T> registry;

        public FabricRegistrar(Registry<T> registry) {
            this.registry = registry;
            REGISTERS.add(this);
        }

        @Override
        public <I extends T> Supplier<I> register(ResourceLocation id, Supplier<I> supplier) {
            I instance = supplier.get();
            Registry.register(this.registry, id, instance);
            return () -> instance;
        }
    }
}