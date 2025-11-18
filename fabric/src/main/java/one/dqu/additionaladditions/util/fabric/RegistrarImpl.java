package one.dqu.additionaladditions.util.fabric;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

import java.util.Optional;
import java.util.function.Supplier;

public class RegistrarImpl {
    public static void defer(Runnable runnable) {
        //todo
    }

    public static <T extends Item> Supplier<T> item(ResourceLocation location, Supplier<T> item) {
        T registered = Registry.register(BuiltInRegistries.ITEM, location, item.get());
        return () -> registered;
    }

    public static <T extends Block> Supplier<T> block(ResourceLocation location, Supplier<T> block, Optional<Supplier<Item.Properties>> blockItemProperties) {
        T registered = Registry.register(BuiltInRegistries.BLOCK, location, block.get());
        blockItemProperties.ifPresent(properties -> {
            item(location, () -> new BlockItem(registered, properties.get()));
        });
        return () -> registered;
    }

    public static <T extends Entity> Supplier<EntityType<T>> entityType(ResourceLocation location, Supplier<EntityType<T>> entityType) {
        EntityType<T> registered = Registry.register(BuiltInRegistries.ENTITY_TYPE, location, entityType.get());
        return () -> registered;
    }

    public static <T> Supplier<DataComponentType<T>> dataComponentType(ResourceLocation location, Supplier<DataComponentType<T>> dataComponentType) {
        DataComponentType<T> registered = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, location, dataComponentType.get());
        return () -> registered;
    }

    public static <T extends Recipe<?>> Supplier<RecipeSerializer<T>> recipeSerializer(ResourceLocation location, Supplier<RecipeSerializer<T>> recipeSerializer) {
        RecipeSerializer<T> registered = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, location, recipeSerializer.get());
        return () -> registered;
    }

    public static void compostingChance(Item item, float chance) {
        CompostingChanceRegistry.INSTANCE.add(item, chance);
    }
}
