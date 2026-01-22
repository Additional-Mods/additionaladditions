package one.dqu.additionaladditions.util.neoforge;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.util.Registrar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RegistrarImpl {
    private static final List<Runnable> DEFERRED = new ArrayList<>();
    private static final List<NeoForgeRegistrar<?>> REGISTERS = new ArrayList<>();

    public static void defer(Runnable runnable) {
        DEFERRED.add(runnable);
    }

    public static void runDeferred() {
        DEFERRED.forEach(Runnable::run);
        DEFERRED.clear();
    }

    public static <T> Registrar<T> wrap(Registry<T> registry) {
        return new NeoForgeRegistrar<>(registry);
    }

    public static void registerAll(IEventBus bus) {
        for (NeoForgeRegistrar<?> registrar : REGISTERS) {
            registrar.register(bus);
        }
    }

    private static class NeoForgeRegistrar<T> extends Registrar<T> {
        private final DeferredRegister<T> register;

        public NeoForgeRegistrar(Registry<T> registry) {
            this.register = DeferredRegister.create(registry, AdditionalAdditions.NAMESPACE);
            REGISTERS.add(this);
        }

        @Override
        public <I extends T> Supplier<I> register(ResourceLocation id, Supplier<I> supplier) {
            return this.register.register(id.getPath(), supplier);
        }

        public void register(IEventBus bus) {
            this.register.register(bus);
        }
    }
}
