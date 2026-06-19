package one.dqu.additionaladditions.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class AABlockTagDatagenProvider extends TagsProvider<Block> {
    public AABlockTagDatagenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.BLOCK, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (AABlockDatagen.Entry entry : AABlockDatagen.entries()) {
            for (TagKey<Block> tag : entry.tags()) {
                getOrCreateRawBuilder(tag).addElement(entry.id());
            }
        }
    }
}
