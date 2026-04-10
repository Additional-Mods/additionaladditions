package one.dqu.additionaladditions.config.type.unit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public record BlockTagUnitConfig(
        String blockTag
) {
    private static final Codec<BlockTagUnitConfig> ICODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("block_tag").forGetter(BlockTagUnitConfig::blockTag)
            ).apply(instance, BlockTagUnitConfig::new)
    );

    public static final Codec<TagKey<Block>> CODEC = ICODEC.xmap(
            BlockTagUnitConfig::toBlock,
            BlockTagUnitConfig::fromBlock
    );

    private TagKey<Block> toBlock() {
        String block = this.blockTag;
        if (block.startsWith("#")) block = block.substring(1);
        return TagKey.create(Registries.BLOCK, Identifier.tryParse(block));
    }

    private static BlockTagUnitConfig fromBlock(TagKey<Block> block) {
        return new BlockTagUnitConfig("#" + block.location());
    }
}
