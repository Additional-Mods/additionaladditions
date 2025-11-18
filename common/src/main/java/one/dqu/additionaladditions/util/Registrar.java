package one.dqu.additionaladditions.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

import java.util.Optional;
import java.util.function.Supplier;

public class Registrar {
    @ExpectPlatform
    public static void defer(Runnable runnable) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Item> Supplier<T> item(ResourceLocation location, Supplier<T> item) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Block> Supplier<T> block(ResourceLocation location, Supplier<T> block, Optional<Supplier<Item.Properties>> blockItemProperties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> entityType(ResourceLocation location, Supplier<EntityType<T>> entityType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> Supplier<DataComponentType<T>> dataComponentType(ResourceLocation location, Supplier<DataComponentType<T>> dataComponentType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Recipe<?>> Supplier<RecipeSerializer<T>> recipeSerializer(ResourceLocation location, Supplier<RecipeSerializer<T>> recipeSerializer) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void compostingChance(Item item, float chance) {
        throw new AssertionError();
    }
}
