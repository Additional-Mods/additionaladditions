package dqu.additionaladditions.item;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

public class SuspiciousDyeItem extends Item {
    private final DyeColor dye;

    public SuspiciousDyeItem(DyeColor color, Properties properties) {
        super(properties);
        this.dye = color;
    }

    public DyeColor getDyeColor() {
        return this.dye;
    }
}
