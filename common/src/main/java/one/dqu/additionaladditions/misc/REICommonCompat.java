package one.dqu.additionaladditions.misc;

import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.brewing.DefaultBrewingDisplay;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import one.dqu.additionaladditions.registry.AAMisc;

public class REICommonCompat implements REICommonPlugin {
    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        registry.beginRecipeFiller(BrewingRecipe.class)
                .filterType(AAMisc.BREWING_RECIPE_TYPE.get())
                .fill(holder -> {
                    BrewingRecipe recipe = holder.value();
                    return new DefaultBrewingDisplay(
                            EntryIngredients.of(PotionContents.createItemStack(Items.POTION, recipe.getPotion())),
                            EntryIngredients.ofIngredient(recipe.getIngredient()),
                            EntryIngredients.of(recipe.getResult())
                    );
                });
    }
}
