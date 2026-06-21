package one.dqu.additionaladditions.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class AABlockTagDatagenProvider extends TagsProvider<Block> {
    public AABlockTagDatagenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.BLOCK, registries);
    }

    // shared method for vanilla and platform provider implementations (neoforge uses vanilla, fabric has its own)
    public static void generate(Function<TagKey<Block>, TagBuilder> builders) {
        for (AABlockDatagen.Entry entry : AABlockDatagen.entries()) {
            for (TagKey<Block> tag : entry.tags()) {
                builders.apply(tag).addElement(entry.id());
            }
        }
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        generate(this::getOrCreateRawBuilder);
    }
}
