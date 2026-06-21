package one.dqu.additionaladditions.feature.sniffer_plants;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAItem;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.core.datagen.template.recipe.RecipeEntry;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AATags;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class SnifferPlantsContent {
    private static final Consumer<BlockBehaviour.Properties> PLANT_PROPS = p -> p
            .mapColor(MapColor.PLANT).noCollision().instabreak().sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);
    private static final Consumer<BlockBehaviour.Properties> CROP_PROPS = p -> p
            .mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.CROP)
            .pushReaction(PushReaction.DESTROY);

    // Helpers

    private static Supplier<Block> doublePlant(String id) {
        return AAReg.<Block>block(DoublePlantBlock::new).props(PLANT_PROPS)
                .tags(AATags.C_FLOWERS_TALL_BLOCK, BlockTags.BEE_ATTRACTIVE, BlockTags.MAINTAINS_FARMLAND)
                .model(Models::doublePlant)
                .make(id);
    }

    private static Supplier<Block> flower(String id, Holder<MobEffect> effect) {
        return flower(id, effect, Models::cross);
    }

    private static Supplier<Block> flower(String id, Holder<MobEffect> effect, Consumer<Block> model) {
        return AAReg.<Block>block(p -> new FlowerBlock(effect, 5f, p)).props(PLANT_PROPS)
                .tags(AATags.C_FLOWERS_SMALL_BLOCK, BlockTags.BEE_ATTRACTIVE, BlockTags.MAINTAINS_FARMLAND)
                .model(model)
                .make(id);
    }

    private static Supplier<Block> crop(String id, Supplier<Block> grown, int maxAge, VoxelShape[] shapes) {
        return AAReg.<Block>block(p -> new FlowerCropBlock(grown, maxAge, shapes, p)).props(CROP_PROPS)
                .tags(BlockTags.CROPS, BlockTags.MAINTAINS_FARMLAND)
                .make(id);
    }

    private static VoxelShape[] shapes(VoxelShape... s) {
        return s;
    }

    private static Supplier<Item> plantItem(String id, Supplier<? extends Block> block, ItemLike anchor, TagKey<Item> flowerTag, ItemLike dye) {
        return plantItem(id, block, anchor, flowerTag, dye, "%s");
    }

    private static Supplier<Item> plantItem(String id, Supplier<? extends Block> block, ItemLike anchor, TagKey<Item> flowerTag, ItemLike dye, String textureFormat) {
        return itemBuilder(p -> new BlockItem(block.get(), p), anchor, false)
                .tags(flowerTag, ItemTags.BEE_FOOD)
                .model(Models.flatBlock(textureFormat))
                .recipeFor(dye, dyeRecipe(id, dye))
                .make(id);
    }

    // shapeless dye recipe with the plant flower as the single ingredient
    private static RecipeEntry dyeRecipe(String id, ItemLike dye) {
        ItemLike plant = () -> BuiltInRegistries.ITEM.getValue(Identifier.tryBuild(AdditionalAdditions.NAMESPACE, id));
        String dyePath = BuiltInRegistries.ITEM.getKey(dye.asItem()).getPath();
        return Recipes.shapeless(RecipeCategory.MISC, plant)
                .named("%s_from_" + id)
                .group(dyePath)
                .unlockedBy(plant);
    }

    private static Supplier<Item> podItem(String id, Supplier<? extends Block> block, ItemLike anchor) {
        return itemBuilder(p -> new BlockItem(block.get(), p), anchor, true)
                .tags(AATags.C_SEEDS)
                .model(Models::flat)
                .make(id);
    }

    private static Supplier<Item> vineItem(String id, Supplier<? extends Block> block, ItemLike anchor, Consumer<Item> model) {
        return itemBuilder(p -> new BlockItem(block.get(), p), anchor, true).model(model).make(id);
    }

    private static AAItem<Item> itemBuilder(Function<Item.Properties, Item> factory, ItemLike anchor, boolean pod) {
        var builder = AAReg.item(factory)
                .config(Config.SNIFFER_PLANTS)
                .creative(anchor, CreativeModeTabs.NATURAL_BLOCKS, CreativePosition.AFTER);
        if (pod) builder.props(Item.Properties::useItemDescriptionPrefix);
        return builder;
    }


    // Blocks

    public static Supplier<Block> cottonshiver() {
        return doublePlant("cottonshiver");
    }

    public static Supplier<Block> cottonshiverCrop() {
        return crop("cottonshiver_crop", AABlocks.COTTONSHIVER, 2,
                shapes(Block.box(4, 0, 4, 12, 8, 12), Block.box(4, 0, 4, 12, 15, 12), Shapes.block()));
    }

    public static Supplier<Block> mudflower() {
        return doublePlant("mudflower");
    }

    public static Supplier<Block> mudflowerCrop() {
        return crop("mudflower_crop", AABlocks.MUDFLOWER, 2,
                shapes(Block.box(5, 0, 5, 11, 9, 11), Block.box(4, 0, 4, 12, 15, 12), Shapes.block()));
    }

    public static Supplier<Block> crimsonBlossom() {
        return flower("crimson_blossom", MobEffects.STRENGTH);
    }

    public static Supplier<Block> crimsonBlossomCrop() {
        return crop("crimson_blossom_crop", AABlocks.CRIMSON_BLOSSOM, 2,
                shapes(Block.box(3, 0, 3, 13, 10, 13), Block.box(3, 0, 3, 13, 12, 13), Shapes.block()));
    }

    public static Supplier<Block> amberBlossom() {
        return doublePlant("amber_blossom");
    }

    public static Supplier<Block> amberBlossomCrop() {
        return crop("amber_blossom_crop", AABlocks.AMBER_BLOSSOM, 2,
                shapes(Block.box(5, 0, 5, 11, 7, 11), Block.box(4, 0, 4, 12, 12, 12), Shapes.block()));
    }

    public static Supplier<Block> bulbus() {
        return flower("bulbus", MobEffects.NIGHT_VISION, Models::tallCross);
    }

    public static Supplier<Block> bulbusCrop() {
        return crop("bulbus_crop", AABlocks.BULBUS, 2,
                shapes(Block.box(5, 0, 5, 11, 10, 11), Block.box(4, 0, 4, 12, 15, 12), Shapes.block()));
    }

    public static Supplier<Block> sawtoothFern() {
        return doublePlant("sawtooth_fern");
    }

    public static Supplier<Block> sawtoothFernCrop() {
        return crop("sawtooth_fern_crop", AABlocks.SAWTOOTH_FERN, 3,
                shapes(Block.box(5, 0, 5, 11, 10, 11), Block.box(5, 0, 5, 11, 10, 11), Block.box(4, 0, 4, 12, 15, 12), Shapes.block()));
    }

    public static Supplier<Block> frostleaf() {
        return flower("frostleaf", MobEffects.SPEED);
    }

    public static Supplier<Block> frostleafCrop() {
        return crop("frostleaf_crop", AABlocks.FROSTLEAF, 2,
                shapes(Block.box(1, 0, 1, 14, 6, 14), Block.box(1, 0, 1, 14, 6, 14), Shapes.block()));
    }

    public static Supplier<Block> wisteria() {
        return AAReg.<Block>block(WisteriaBlock::new)
                .props(p -> p.mapColor(MapColor.PLANT).noCollision().instabreak().sound(SoundType.VINE)
                        .strength(0.2F).pushReaction(PushReaction.DESTROY))
                .tags(AATags.C_FLOWERS_BLOCK, BlockTags.BEE_ATTRACTIVE, BlockTags.MAINTAINS_FARMLAND)
                .make("wisteria");
    }

    public static Supplier<Block> wisteriaCrop() {
        return AAReg.<Block>block(WisteriaCropBlock::new)
                .props(p -> p.mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.VINE)
                        .pushReaction(PushReaction.DESTROY))
                .tags(BlockTags.CROPS, BlockTags.MAINTAINS_FARMLAND)
                .make("wisteria_crop");
    }

    public static Supplier<Block> spikeblossom() {
        return doublePlant("spikeblossom");
    }

    public static Supplier<Block> spikeblossomCrop() {
        return crop("spikeblossom_crop", AABlocks.SPIKEBLOSSOM, 2,
                shapes(Block.box(5, 0, 5, 11, 7, 11), Block.box(4, 0, 4, 12, 11, 12), Shapes.block()));
    }

    public static Supplier<Block> snapdragon() {
        return doublePlant("snapdragon");
    }

    public static Supplier<Block> snapdragonCrop() {
        return crop("snapdragon_crop", AABlocks.SNAPDRAGON, 2,
                shapes(Block.box(5, 0, 5, 11, 8, 11), Block.box(4, 0, 4, 12, 15, 12), Shapes.block()));
    }

    public static Supplier<Block> lotusLily() {
        return AAReg.<Block>block(LilyPadBlock::new)
                .props(p -> p.mapColor(MapColor.PLANT).instabreak().sound(SoundType.LILY_PAD).noOcclusion()
                        .pushReaction(PushReaction.DESTROY))
                .tags(AATags.C_FLOWERS_BLOCK, BlockTags.BEE_ATTRACTIVE, BlockTags.MAINTAINS_FARMLAND)
                .make("lotus_lily");
    }

    public static Supplier<Block> lotusLilyCrop() {
        return AAReg.<Block>block(LotusLilyCropBlock::new)
                .props(p -> p.mapColor(MapColor.PLANT).randomTicks().instabreak().sound(SoundType.LILY_PAD).noOcclusion()
                        .pushReaction(PushReaction.DESTROY))
                .tags(BlockTags.CROPS, BlockTags.MAINTAINS_FARMLAND)
                .make("lotus_lily_crop");
    }

    // Items

    public static Supplier<Item> cottonshiverItem() {
        return plantItem("cottonshiver", AABlocks.COTTONSHIVER, Items.PITCHER_PLANT, AATags.C_FLOWERS_TALL_ITEM, Items.WHITE_DYE, "%s_top");
    }

    public static Supplier<Item> cottonshiverPod() {
        return podItem("cottonshiver_pod", AABlocks.COTTONSHIVER_CROP, Items.PITCHER_POD);
    }

    public static Supplier<Item> mudflowerItem() {
        return plantItem("mudflower", AABlocks.MUDFLOWER, Items.PITCHER_PLANT, AATags.C_FLOWERS_TALL_ITEM, Items.BROWN_DYE, "%s_top");
    }

    public static Supplier<Item> mudflowerSeeds() {
        return podItem("mudflower_seeds", AABlocks.MUDFLOWER_CROP, Items.PITCHER_POD);
    }

    public static Supplier<Item> crimsonBlossomItem() {
        return plantItem("crimson_blossom", AABlocks.CRIMSON_BLOSSOM, Items.TORCHFLOWER, AATags.C_FLOWERS_SMALL_ITEM, Items.RED_DYE);
    }

    public static Supplier<Item> crimsonBlossomSeeds() {
        return podItem("crimson_blossom_seeds", AABlocks.CRIMSON_BLOSSOM_CROP, Items.PITCHER_POD);
    }

    public static Supplier<Item> amberBlossomItem() {
        return plantItem("amber_blossom", AABlocks.AMBER_BLOSSOM, Items.PITCHER_PLANT, AATags.C_FLOWERS_TALL_ITEM, Items.YELLOW_DYE, "%s_bottom");
    }

    public static Supplier<Item> amberBlossomSeeds() {
        return podItem("amber_blossom_seeds", AABlocks.AMBER_BLOSSOM_CROP, Items.PITCHER_POD);
    }

    public static Supplier<Item> bulbusItem() {
        return plantItem("bulbus", AABlocks.BULBUS, Items.TORCHFLOWER, AATags.C_FLOWERS_SMALL_ITEM, Items.LIME_DYE, "%s_bottom");
    }

    public static Supplier<Item> bulbusPod() {
        return podItem("bulbus_pod", AABlocks.BULBUS_CROP, Items.PITCHER_POD);
    }

    public static Supplier<Item> sawtoothFernItem() {
        return plantItem("sawtooth_fern", AABlocks.SAWTOOTH_FERN, Items.PITCHER_PLANT, AATags.C_FLOWERS_TALL_ITEM, Items.GREEN_DYE, "%s_bottom");
    }

    public static Supplier<Item> sawtoothFernFiddlehead() {
        return podItem("sawtooth_fern_fiddlehead", AABlocks.SAWTOOTH_FERN_CROP, Items.PITCHER_POD);
    }

    public static Supplier<Item> frostleafItem() {
        return plantItem("frostleaf", AABlocks.FROSTLEAF, Items.TORCHFLOWER, AATags.C_FLOWERS_SMALL_ITEM, Items.BLUE_DYE);
    }

    public static Supplier<Item> frostleafPod() {
        return podItem("frostleaf_pod", AABlocks.FROSTLEAF_CROP, Items.PITCHER_POD);
    }

    public static Supplier<Item> wisteriaItem() {
        return plantItem("wisteria", AABlocks.WISTERIA, Items.TORCHFLOWER, AATags.C_FLOWERS_ITEM, Items.LIGHT_BLUE_DYE, "%s_crop_stage1");
    }

    public static Supplier<Item> wisteriaVines() {
        return vineItem("wisteria_vines", AABlocks.WISTERIA_CROP, Items.PITCHER_POD, Models.flatBlock("wisteria_crop_stage0"));
    }

    public static Supplier<Item> spikeblossomItem() {
        return plantItem("spikeblossom", AABlocks.SPIKEBLOSSOM, Items.PITCHER_PLANT, AATags.C_FLOWERS_TALL_ITEM, Items.PURPLE_DYE, "%s_bottom");
    }

    public static Supplier<Item> spikeblossomSeeds() {
        return podItem("spikeblossom_seeds", AABlocks.SPIKEBLOSSOM_CROP, Items.PITCHER_POD);
    }

    public static Supplier<Item> snapdragonItem() {
        return plantItem("snapdragon", AABlocks.SNAPDRAGON, Items.PITCHER_PLANT, AATags.C_FLOWERS_TALL_ITEM, Items.MAGENTA_DYE, "%s_bottom");
    }

    public static Supplier<Item> snapdragonPod() {
        return podItem("snapdragon_pod", AABlocks.SNAPDRAGON_CROP, Items.PITCHER_POD);
    }

    public static Supplier<Item> lotusLilyItem() {
        return AAReg.<Item>item(p -> new PlaceOnWaterBlockItem(AABlocks.LOTUS_LILY.get(), p))
                .config(Config.SNIFFER_PLANTS)
                .creative(Items.TORCHFLOWER, CreativeModeTabs.NATURAL_BLOCKS, CreativePosition.AFTER)
                .tags(AATags.C_FLOWERS_ITEM, ItemTags.BEE_FOOD)
                .model(Models::flatBlock)
                .recipeFor(Items.PINK_DYE, dyeRecipe("lotus_lily", Items.PINK_DYE))
                .make("lotus_lily");
    }

    public static Supplier<Item> lotusLilyPod() {
        return itemBuilder(p -> new PlaceOnWaterBlockItem(AABlocks.LOTUS_LILY_CROP.get(), p), Items.PITCHER_POD, true)
                .model(Models::flat)
                .make("lotus_lily_pod");
    }
}
