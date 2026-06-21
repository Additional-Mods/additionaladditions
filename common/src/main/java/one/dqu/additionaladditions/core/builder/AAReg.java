package one.dqu.additionaladditions.core.builder;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Helper class for creating builders.
 */
public final class AAReg {
    private AAReg() {
    }

    public static <T extends Item> AAItem<T> item(Function<Item.Properties, T> factory) {
        return new AAItem<T>().factory(factory);
    }

    public static AAItem<Item> item() {
        return new AAItem<>();
    }

    public static <T extends Block> AABlock<T> block(Function<BlockBehaviour.Properties, T> factory) {
        return new AABlock<>(factory);
    }

    public static AAItem<BlockItem> blockItem(Supplier<? extends Block> block) {
        return new AAItem<BlockItem>().factory(p -> new BlockItem(block.get(), p));
    }
}
