package one.dqu.additionaladditions.feature.copper_patina;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAItems;

import java.util.Map;
import java.util.function.Supplier;

public class CopperPatinaContent {
    public static Supplier<CopperPatinaBlock> copperPatinaBlock() {
        return AAReg.block(CopperPatinaBlock::new)
                .props(p -> p
                        .mapColor(MapColor.COLOR_CYAN)
                        .pushReaction(PushReaction.DESTROY)
                        .noCollision()
                        .sound(SoundType.TUFF)
                        .instabreak())
                .make("copper_patina");
    }

    public static Supplier<PatinaBlock> patinaBlock() {
        return AAReg.block(PatinaBlock::new)
                .props(p -> p
                        .mapColor(MapColor.COLOR_CYAN)
                        .pushReaction(PushReaction.NORMAL)
                        .sound(SoundType.ROOTED_DIRT)
                        .strength(0.5f))
                .tags(BlockTags.MINEABLE_WITH_SHOVEL)
                .model(Models::cube)
                .make("patina_block");
    }

    public static Supplier<CopperPatinaItem> copperPatina() {
        //noinspection Convert2MethodRef - item is null at this point, would crash
        ItemLike patina = () -> AAItems.COPPER_PATINA.get();

        return AAReg.item(p -> new CopperPatinaItem(AABlocks.COPPER_PATINA.get(), p))
                .config(Config.COPPER_PATINA)
                .creative(Items.REDSTONE, CreativeModeTabs.REDSTONE_BLOCKS, CreativePosition.AFTER)
                .model(Models::flat)
                .recipeFor(Items.DYE.cyan(), Recipes.shapeless(RecipeCategory.MISC, 2, patina).unlockedBy(patina).group("cyan_dye"))
                .make("copper_patina");
    }

    public static Supplier<BlockItem> patinaBlockItem() {
        return AAReg.blockItem(AABlocks.PATINA_BLOCK)
                .config(Config.COPPER_PATINA)
                .creative(Items.COPPER_BLOCK.weathering().unaffected(), CreativeModeTabs.BUILDING_BLOCKS, CreativePosition.BEFORE)
                .recipe(Recipes.shaped("PP", "PP", Map.of('P', AAItems.COPPER_PATINA::get), RecipeCategory.BUILDING_BLOCKS).unlockedBy(AAItems.COPPER_PATINA::get))
                .make("patina_block");
    }
}
