package one.dqu.additionaladditions.feature.rope;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AARegistries;
import one.dqu.additionaladditions.registry.AATags;

import java.util.Map;
import java.util.function.Supplier;

public class RopeContent {
    public static Supplier<RopeBlock> ropeBlock() {
        return AAReg.block(RopeBlock::new)
                .props(p -> p
                        .mapColor(MapColor.COLOR_BROWN)
                        .pushReaction(PushReaction.DESTROY)
                        .noCollision()
                        .sound(SoundType.WOOL)
                        .explosionResistance(0f)
                        .destroyTime(0.2f))
                .tags(AATags.C_ROPES_BLOCK, BlockTags.CLIMBABLE)
                .make("rope");
    }

    public static Supplier<BlockItem> rope() {
        return AAReg.blockItem(AABlocks.ROPE_BLOCK)
                .config(Config.ROPE)
                .creative(Items.LADDER, CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativePosition.AFTER)
                .creative(Items.SPYGLASS, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .model(Models::flat)
                .tags(AATags.C_ROPES_ITEM)
                .recipe(Recipes.shaped("S", "S", Map.of('S', Items.STRING), RecipeCategory.MISC).unlockedBy(Items.STRING))
                .make("rope");
    }

    public static Supplier<RopeArrowItem> ropeArrow() {
        ItemLike rope = AAItems.ROPE::get;
        return AAReg.item(RopeArrowItem::new)
                .config(Config.ROPE)
                .creative(Items.ARROW, CreativeModeTabs.COMBAT, CreativePosition.AFTER)
                .model(Models::flat)
                .tags(ItemTags.ARROWS)
                .recipe(Recipes.shapeless(RecipeCategory.MISC, Items.ARROW, rope, rope, rope, rope, rope, rope, rope, rope).unlockedBy(rope))
                .make("rope_arrow");
    }

    public static Supplier<EntityType<RopeArrow>> ropeArrowEntity() {
        Identifier id = Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "rope_arrow");
        return AARegistries.ENTITY_TYPES.register(id, () -> EntityType.Builder
                .of((EntityType<RopeArrow> type, Level level) -> new RopeArrow(type, level), MobCategory.MISC)
                .sized(0.5f, 0.5f).eyeHeight(0.13f).updateInterval(20).clientTrackingRange(4)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, id)));
    }
}
