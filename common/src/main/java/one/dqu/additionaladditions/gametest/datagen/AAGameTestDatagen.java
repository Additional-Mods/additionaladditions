package one.dqu.additionaladditions.gametest.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.gametest.framework.*;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import one.dqu.additionaladditions.AdditionalAdditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AAGameTestDatagen {
    private static List<Entry> entries = AdditionalAdditions.DATAGEN ? new ArrayList<>() : null;

    public record Entry(
            Identifier id,
            ResourceKey<Consumer<GameTestHelper>> function,
            Identifier structure,
            int maxTicks,
            ResourceKey<TestEnvironmentDefinition> environment
    ) {}

    public static void register(Entry entry) {
        if (entries == null) return;
        entries.add(entry);
    }

    public static void bootstrap(BootstrapContext<GameTestInstance> context) {
        if (entries == null) return;

        HolderGetter<TestEnvironmentDefinition> environments = context.lookup(Registries.TEST_ENVIRONMENT);

        for (Entry entry : entries) {
            AdditionalAdditions.LOGGER.info("[{}] GameTestDatagen: generating {}", AdditionalAdditions.NAMESPACE, entry.id());

            ResourceKey<GameTestInstance> key = ResourceKey.create(Registries.TEST_INSTANCE, entry.id());

            TestData<Holder<TestEnvironmentDefinition>> data = new TestData<>(
                    environments.getOrThrow(entry.environment()),
                    entry.structure(),
                    entry.maxTicks(),
                    0,
                    true
            );

            context.register(key, new FunctionGameTestInstance(entry.function(), data));
        }

        entries = null;
    }

    public static RegistrySetBuilder registryBuilder() {
        return new RegistrySetBuilder().add(Registries.TEST_INSTANCE, AAGameTestDatagen::bootstrap);
    }
}
