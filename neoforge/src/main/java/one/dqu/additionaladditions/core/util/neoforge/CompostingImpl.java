package one.dqu.additionaladditions.core.util.neoforge;

import net.minecraft.world.item.Item;
import one.dqu.additionaladditions.AdditionalAdditions;

import java.util.HashMap;
import java.util.function.Supplier;

public class CompostingImpl {
    private static HashMap<Supplier<Item>, Float> compostables = AdditionalAdditions.DATAGEN ? new HashMap<>() : null;

    public static void add(Supplier<Item> item, float chance) {
        if (compostables == null) return;
        compostables.put(item, chance);
    }

    public static HashMap<Supplier<Item>, Float> getCompostables() {
        return compostables;
    }
}
