package one.dqu.additionaladditions.core.builder;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.component.DataComponentInitializers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.ItemLike;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.Toggleable;
import one.dqu.additionaladditions.material.AAMaterial;
import one.dqu.additionaladditions.material.AnimalArmorType;
import one.dqu.additionaladditions.material.ToolType;
import one.dqu.additionaladditions.registry.AARegistries;
import one.dqu.additionaladditions.util.CreativeAdder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AAItem<T extends Item> {
    private ConfigProperty<? extends Toggleable> config;
    Function<Item.Properties, T> factory;
    private Consumer<Item.Properties> props = p -> {
    };
    private final Map<ResourceKey<CreativeModeTab>, List<Pair<ItemLike, Boolean>>> creative = new HashMap<>();
    private DataComponentInitializers.Initializer<Item> initializer; // extra component initializer on top of vanilla's (in item properties)

    @SuppressWarnings("unchecked")
    public AAItem() {
        // default factory = Item::new
        this.factory = (Function<Item.Properties, T>) (Function<Item.Properties, ? extends Item>) Item::new;
    }

    public AAItem(AAItem<T> template) {
        this.config = template.config;
        this.factory = template.factory;
        this.props = template.props;
        this.initializer = template.initializer;
    }

    public AAItem<T> config(ConfigProperty<? extends Toggleable> config) {
        this.config = config;
        return this;
    }

    public AAItem<T> factory(Function<Item.Properties, T> factory) {
        this.factory = factory;
        return this;
    }

    public AAItem<T> properties(Consumer<Item.Properties> props) {
        this.props = this.props.andThen(props);
        return this;
    }

    public AAItem<T> creative(ItemLike anchor, ResourceKey<CreativeModeTab> category, boolean isBefore) {
        creative.computeIfAbsent(category, _ -> new ArrayList<>()).add(Pair.of(anchor, isBefore));
        return this;
    }

    public AAItem<T> initializer(DataComponentInitializers.Initializer<Item> step) {
        this.initializer = this.initializer == null ? step : this.initializer.andThen(step);
        return this;
    }

    public AAItem<T> material(AAMaterial material, ToolType toolType) {
        return properties(p -> p.repairable(material.repairIngredient()))
                .initializer(material.initializerFor(toolType));
    }

    public AAItem<T> material(AAMaterial material, ArmorType armorType) {
        return properties(p -> p.repairable(material.repairIngredient()))
                .initializer(material.initializerFor(armorType));
    }

    public AAItem<T> material(AAMaterial material, AnimalArmorType armorType) {
        return properties(p -> p.repairable(material.repairIngredient()))
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
                boolean isBefore = pair.getSecond();
                if (isBefore) {
                    CreativeAdder.addBefore(tab, enabled, anchor, item);
                } else {
                    CreativeAdder.add(tab, enabled, anchor, item);
                }
            });
        });

        return item;
    }
}
