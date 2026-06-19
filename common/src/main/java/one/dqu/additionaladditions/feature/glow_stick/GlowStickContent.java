package one.dqu.additionaladditions.feature.glow_stick;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.registry.AABlocks;
import one.dqu.additionaladditions.registry.AARegistries;

import java.util.function.Supplier;

public class GlowStickContent {
    public static Supplier<EntityType<GlowStickEntity>> glowStickEntity() {
        Identifier id = Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "glow_stick");
        return AARegistries.ENTITY_TYPES.register(id, () -> EntityType.Builder
                .of((EntityType<GlowStickEntity> type, Level level) -> new GlowStickEntity(type, level), MobCategory.MISC)
                .sized(0.25f, 0.25f).updateInterval(20).clientTrackingRange(4)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, id)));
    }

    public static Supplier<GlowStickBlock> glowStickBlock() {
        return AAReg.block(GlowStickBlock::new)
                .props(p -> p
                        .mapColor(MapColor.NONE)
                        .pushReaction(PushReaction.DESTROY)
                        .noCollision()
                        .lightLevel(state -> 12)
                        .instabreak())
                .make("glow_stick");
    }

    public static Supplier<Item> glowStick() {
        return AAReg.<Item>item(p -> new GlowStickItem(AABlocks.GLOW_STICK_BLOCK.get(), p))
                .config(Config.GLOW_STICK)
                .creative(Items.BONE_MEAL, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativePosition.AFTER)
                .model(Models::flat)
                .recipe(Recipes.shapeless(RecipeCategory.MISC, 3, Items.STICK, Items.GLOW_INK_SAC).unlockedBy(Items.GLOW_INK_SAC))
                .make("glow_stick");
    }
}
