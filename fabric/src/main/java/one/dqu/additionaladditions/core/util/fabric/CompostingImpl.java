package one.dqu.additionaladditions.core.util.fabric;

import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.function.Supplier;

public class CompostingImpl {
    private static HashMap<Supplier<Item>, Float> compostables = new HashMap<>();

    public static void add(Supplier<Item> item, float chance) {
        if (compostables == null) return;
        compostables.put(item, chance);
    }

    public static HashMap<Supplier<Item>, Float> getCompostables() {
        return compostables;
    }

    public static void clear() {
        compostables.clear();
        compostables = null;
    }
}
