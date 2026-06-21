package one.dqu.additionaladditions.core.datagen;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class AAGameTestDatagen {
    private static List<Entry> entries = AdditionalAdditions.DATAGEN ? new ArrayList<>() : null;
    private static final Set<ResourceKey<TestEnvironmentDefinition<?>>> environments = AdditionalAdditions.DATAGEN ? new HashSet<>() : null;

    public record Entry(
            Identifier id,
            ResourceKey<Consumer<GameTestHelper>> function,
            Identifier structure,
            int maxTicks,
            ResourceKey<TestEnvironmentDefinition<?>> environment
    ) {
    }

    public static void register(Entry entry) {
        if (entries == null || environments == null) return;
        entries.add(entry);
        environments.add(entry.environment());
    }

    public static void bootstrap(BootstrapContext<GameTestInstance> context) {
        if (entries == null) return;

        HolderGetter<TestEnvironmentDefinition<?>> environments = context.lookup(Registries.TEST_ENVIRONMENT);

        for (Entry entry : entries) {
            AdditionalAdditions.LOGGER.info("[{}] GameTestDatagen: generating {}", AdditionalAdditions.NAMESPACE, entry.id());

            ResourceKey<GameTestInstance> key = ResourceKey.create(Registries.TEST_INSTANCE, entry.id());

            TestData<Holder<TestEnvironmentDefinition<?>>> data = new TestData<>(
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

    // for fabric
    // register empty test environments just so the test instance references resolve
    // because fabric doesnt load datapack jsons during datagen
    // actual definitions are manual in common/src/main/resources/...
    public static void bootstrapEnvironments(BootstrapContext<TestEnvironmentDefinition<?>> context) {
        for (ResourceKey<TestEnvironmentDefinition<?>> environment : environments) {
            if (environment.identifier().getNamespace().equals(AdditionalAdditions.NAMESPACE)) {
                context.register(environment, new TestEnvironmentDefinition.AllOf(List.of()));
            }
        }
    }

    public static RegistrySetBuilder registryBuilder() {
        return new RegistrySetBuilder().add(Registries.TEST_INSTANCE, AAGameTestDatagen::bootstrap);
    }
}
