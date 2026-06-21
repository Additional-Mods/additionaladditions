package one.dqu.additionaladditions.core.builder;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.core.datagen.AABlockDatagen;
import one.dqu.additionaladditions.registry.AARegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AABlock<T extends Block> {
    private final Function<BlockBehaviour.Properties, T> factory;
    private Consumer<BlockBehaviour.Properties> props = p -> {
    };
    // datagen data
    private final List<TagKey<Block>> tags = new ArrayList<>();
    private Consumer<Block> model;

    public AABlock(Function<BlockBehaviour.Properties, T> factory) {
        this.factory = factory;
    }

    public AABlock<T> props(Consumer<BlockBehaviour.Properties> props) {
        this.props = this.props.andThen(props);
        return this;
    }

    @SafeVarargs
    public final AABlock<T> tags(TagKey<Block>... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

    /**
     * optional datagen block template, e.g. Models::cube
     */
    public AABlock<T> model(Consumer<Block> model) {
        this.model = model;
        return this;
    }

    public Supplier<T> make(String id) {
        Identifier location = Identifier.tryBuild(AdditionalAdditions.NAMESPACE, id);

        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of();
        properties.setId(ResourceKey.create(Registries.BLOCK, location));
        props.accept(properties);

        Supplier<T> block = AARegistries.BLOCKS.register(location, () -> factory.apply(properties));

        if (AdditionalAdditions.DATAGEN) {
            AABlockDatagen.register(new AABlockDatagen.Entry(location, block, List.copyOf(tags), model));
        }

        return block;
    }
}
