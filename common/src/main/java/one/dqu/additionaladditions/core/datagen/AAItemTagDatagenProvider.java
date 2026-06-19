package one.dqu.additionaladditions.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class AAItemTagDatagenProvider extends TagsProvider<Item> {
    public AAItemTagDatagenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.ITEM, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (AAItemDatagen.Entry entry : AAItemDatagen.entries()) {
            for (TagKey<Item> tag : entry.tags()) {
                getOrCreateRawBuilder(tag).addElement(entry.id());
            }
        }
    }
}
