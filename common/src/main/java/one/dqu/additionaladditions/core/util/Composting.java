package one.dqu.additionaladditions.core.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class Composting {
    @ExpectPlatform
    public static void add(Supplier<Item> item, float chance) {
        throw new AssertionError();
    }
}
