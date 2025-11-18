package one.dqu.additionaladditions.util.neoforge;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import one.dqu.additionaladditions.AdditionalAdditions;

import java.util.Optional;
import java.util.function.Supplier;

public class RegistrarImpl {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.createItems(AdditionalAdditions.NAMESPACE);
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.createBlocks(AdditionalAdditions.NAMESPACE);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Registries.ENTITY_TYPE, AdditionalAdditions.NAMESPACE);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, AdditionalAdditions.NAMESPACE);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, AdditionalAdditions.NAMESPACE);

    public static void defer(Runnable runnable) {
        //todo
    }

    public static <T extends Item> Supplier<T> item(ResourceLocation location, Supplier<T> item)  {
        return ITEM.register(location.getPath(), item);
    }

    public static <T extends Block> Supplier<T> block(ResourceLocation location, Supplier<T> block, Optional<Supplier<Item.Properties>> blockItemProperties) {
        Supplier<T> registered = BLOCK.register(location.getPath(), block);
        blockItemProperties.ifPresent(properties -> {
            item(location, () -> new BlockItem(registered.get(), properties.get()));
        });
        return registered;
    }

    public static <T extends Entity> Supplier<EntityType<T>> entityType(ResourceLocation location, Supplier<EntityType<T>> entityType) {
        return ENTITY_TYPE.register(location.getPath(), entityType);
    }

    public static <T> Supplier<DataComponentType<T>> dataComponentType(ResourceLocation location, Supplier<DataComponentType<T>> dataComponentType) {
        return DATA_COMPONENT_TYPE.register(location.getPath(), dataComponentType);
    }

    public static <T extends Recipe<?>> Supplier<RecipeSerializer<T>> recipeSerializer(ResourceLocation location, Supplier<RecipeSerializer<T>> recipeSerializer) {
        return RECIPE_SERIALIZER.register(location.getPath(), recipeSerializer);
    }

    public static void compostingChance(Item item, float chance) {

    }
}
