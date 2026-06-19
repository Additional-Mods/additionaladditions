package one.dqu.additionaladditions.feature.trident;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;

import java.util.function.Supplier;

public class TridentShardContent {
    public static Supplier<Item> tridentShard() {
        return AAReg.item()
                .config(Config.TRIDENT_SHARD)
                .creative(Items.PRISMARINE_CRYSTALS, CreativeModeTabs.INGREDIENTS, CreativePosition.AFTER)
                .model(Models::flat)
                .make("trident_shard");
    }
}
