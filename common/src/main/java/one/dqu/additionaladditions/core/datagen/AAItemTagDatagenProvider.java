package one.dqu.additionaladditions.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class AAItemTagDatagenProvider extends TagsProvider<Item> {
    public AAItemTagDatagenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.ITEM, registries);
    }

    // shared method for vanilla and platform provider implementations (neoforge uses vanilla, fabric has its own)
    public static void generate(Function<TagKey<Item>, TagBuilder> builders) {
        for (AAItemDatagen.Entry entry : AAItemDatagen.entries()) {
            for (TagKey<Item> tag : entry.tags()) {
                builders.apply(tag).addElement(entry.id());
            }
        }
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        generate(this::getOrCreateRawBuilder);
    }
}
