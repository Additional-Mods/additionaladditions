package one.dqu.additionaladditions.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import one.dqu.additionaladditions.util.Registrar;

public class AdditionalRegistries {
    public static final Registrar<DataComponentType<?>> DATA_COMPONENT_TYPES = Registrar.wrap(BuiltInRegistries.DATA_COMPONENT_TYPE);
    public static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = Registrar.wrap(BuiltInRegistries.RECIPE_SERIALIZER);
    public static final Registrar<Block> BLOCKS = Registrar.wrap(BuiltInRegistries.BLOCK);
    public static final Registrar<Item> ITEMS = Registrar.wrap(BuiltInRegistries.ITEM);
    public static final Registrar<EntityType<?>> ENTITY_TYPES = Registrar.wrap(BuiltInRegistries.ENTITY_TYPE);
}
