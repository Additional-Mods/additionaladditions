package one.dqu.additionaladditions.gametest;

import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.gametest.framework.GameTestEnvironments;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.gametest.datagen.AAGameTestDatagen;
import one.dqu.additionaladditions.registry.AARegistries;

import java.util.function.Consumer;

public class AAGameTest {
    private Consumer<GameTestHelper> function = null;
    private String structure = "empty";
    private int maxTicks = 100;
    private ResourceKey<TestEnvironmentDefinition> environment = GameTestEnvironments.DEFAULT_KEY;

    public void create(String name) {
        if (function == null) {
            throw new IllegalStateException("Function is a mandatory parameter.");
        }

        Identifier id = Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, name);
        AARegistries.TEST_FUNCTIONS.register(id, () -> function);

        AAGameTestDatagen.register(new AAGameTestDatagen.Entry(
                id,
                ResourceKey.create(Registries.TEST_FUNCTION, id),
                Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, structure),
                maxTicks,
                environment
        ));
    }

    public AAGameTest function(Consumer<GameTestHelper> function) {
        this.function = function;
        return this;
    }

    public AAGameTest structure(String structure) {
        this.structure = structure;
        return this;
    }

    public AAGameTest maxTicks(int maxTicks) {
        this.maxTicks = maxTicks;
        return this;
    }

    public AAGameTest environment(ResourceKey<TestEnvironmentDefinition> environment) {
        this.environment = environment;
        return this;
    }
}
