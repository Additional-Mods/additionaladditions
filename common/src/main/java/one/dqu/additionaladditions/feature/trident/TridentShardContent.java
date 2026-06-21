package one.dqu.additionaladditions.feature.trident;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.registry.AAItems;

import java.util.Map;
import java.util.function.Supplier;

public class TridentShardContent {
    public static Supplier<Item> tridentShard() {
        @SuppressWarnings("Convert2MethodRef") // would crash because AAItems.TRIDENT_SHARD is this item itself, it is null when this runs
        ItemLike shard = () -> AAItems.TRIDENT_SHARD.get();

        return AAReg.item()
                .config(Config.TRIDENT_SHARD)
                .creative(Items.PRISMARINE_CRYSTALS, CreativeModeTabs.INGREDIENTS, CreativePosition.AFTER)
                .model(Models::flat)
                .recipeFor(
                        Items.TRIDENT, Recipes.shaped(
                                " SS", " PS", "P  ",
                                Map.of('S', shard, 'P', Items.PRISMARINE_SHARD),
                                RecipeCategory.COMBAT
                        ).unlockedBy(shard)
                )
                .make("trident_shard");
    }
}
