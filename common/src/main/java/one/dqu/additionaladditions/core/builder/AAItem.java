package one.dqu.additionaladditions.core.builder;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.component.DataComponentInitializers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.ItemLike;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.core.datagen.AAItemDatagen;
import one.dqu.additionaladditions.core.datagen.template.recipe.RecipeEntry;
import one.dqu.additionaladditions.core.material.AAMaterial;
import one.dqu.additionaladditions.core.material.AnimalArmorType;
import one.dqu.additionaladditions.core.material.ToolType;
import one.dqu.additionaladditions.core.util.CreativeAdder;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AARegistries;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AAItem<T extends Item> {
    private ConfigProperty<? extends Toggleable> config;
    Function<Item.Properties, T> factory;
    private Consumer<Item.Properties> props = p -> {
    };
    private final Map<ResourceKey<CreativeModeTab>, List<Pair<ItemLike, CreativePosition>>> creative = new HashMap<>();
    private DataComponentInitializers.Initializer<Item> initializer; // extra component initializer on top of vanilla's (in item properties)

    // datagen data
    private final List<TagKey<Item>> tags = new ArrayList<>();
    private Consumer<Item> model = null;
    private final List<RecipeEntry> recipes = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public AAItem() {
        // default factory = Item::new
        this.factory = (Function<Item.Properties, T>) (Function<Item.Properties, ? extends Item>) Item::new;
    }

    public AAItem<T> config(ConfigProperty<? extends Toggleable> config) {
        this.config = config;
        return this;
    }

    public AAItem<T> factory(Function<Item.Properties, T> factory) {
        this.factory = factory;
        return this;
    }

    public AAItem<T> props(Consumer<Item.Properties> props) {
        this.props = this.props.andThen(props);
        return this;
    }

    public AAItem<T> creative(ItemLike anchor, ResourceKey<CreativeModeTab> category, CreativePosition position) {
        creative.computeIfAbsent(category, _ -> new ArrayList<>()).add(Pair.of(anchor, position));
        return this;
    }

    // Datagen

    @SafeVarargs
    public final AAItem<T> tags(TagKey<Item>... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

    public AAItem<T> model(Consumer<Item> model) {
        this.model = model;
        return this;
    }

    public AAItem<T> recipe(RecipeEntry recipe) {
        this.recipes.add(recipe);
        return this;
    }

    public AAItem<T> recipe(RecipeEntry... recipes) {
        Collections.addAll(this.recipes, recipes);
        return this;
    }

    public AAItem<T> recipe(List<RecipeEntry> recipes) {
        this.recipes.addAll(recipes);
        return this;
    }

    // recipe that produces a different item than this one
    public AAItem<T> recipeFor(ItemLike result, RecipeEntry recipe) {
        this.recipes.add(recipe.result(result));
        return this;
    }

    //

    public AAItem<T> initializer(DataComponentInitializers.Initializer<Item> step) {
        this.initializer = this.initializer == null ? step : this.initializer.andThen(step);
        return this;
    }

    public AAItem<T> material(AAMaterial material, ToolType toolType) {
        return props(p -> p.repairable(material.repairIngredient()))
                .initializer(material.initializerFor(toolType));
    }

    public AAItem<T> material(AAMaterial material, ArmorType armorType) {
        return props(p -> p.repairable(material.repairIngredient()))
                .initializer(material.initializerFor(armorType));
    }

    public AAItem<T> material(AAMaterial material, AnimalArmorType armorType) {
        return props(p -> p.repairable(material.repairIngredient()))
                .initializer(material.initializerFor(armorType));
    }

    public Supplier<T> make(String id) {
        Identifier location = Identifier.tryBuild(AdditionalAdditions.NAMESPACE, id);
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, location);

        ConfigProperty<? extends Toggleable> capturedConfig = config;
        Supplier<Boolean> enabled = capturedConfig != null ? () -> capturedConfig.get().enabled() : () -> true;

        Consumer<Item.Properties> deferredProperties = props;
        DataComponentInitializers.Initializer<Item> deferredInitializer = initializer;
        Supplier<T> item = AARegistries.ITEMS.register(location, () -> {
            Item.Properties props = new Item.Properties();
            props.setId(itemKey);
            deferredProperties.accept(props);
            T instance = factory.apply(props);
            if (deferredInitializer != null) {
                BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.add(itemKey, deferredInitializer);
            }
            return instance;
        });

        creative.forEach((tab, items) -> {
            items.forEach(pair -> {
                ItemLike anchor = pair.getFirst();
                CreativePosition position = pair.getSecond();
                if (position == CreativePosition.BEFORE) {
                    CreativeAdder.addBefore(tab, enabled, anchor, item);
                } else {
                    CreativeAdder.add(tab, enabled, anchor, item);
                }
            });
        });

        if (config != null) {
            AAItems.addConfigItem(config, item);
        }

        if (AdditionalAdditions.DATAGEN) {
            AAItemDatagen.register(new AAItemDatagen.Entry(location, item, model, List.copyOf(recipes), List.copyOf(tags)));
        }

        return item;
    }
}
