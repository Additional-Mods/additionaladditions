package one.dqu.additionaladditions.item;

import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class ItemNamePlaceOnWaterBlockItem extends PlaceOnWaterBlockItem {
    public ItemNamePlaceOnWaterBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
