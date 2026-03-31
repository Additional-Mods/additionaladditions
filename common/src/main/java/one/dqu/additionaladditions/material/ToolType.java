package one.dqu.additionaladditions.material;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public enum ToolType {
    SWORD,
    PICKAXE,
    AXE,
    SHOVEL,
    HOE;

    public TagKey<Block> mineableTag() {
        return mineableTag(this);
    }

    public static TagKey<Block> mineableTag(ToolType toolType) {
        return switch (toolType) {
            case PICKAXE -> BlockTags.MINEABLE_WITH_PICKAXE;
            case AXE -> BlockTags.MINEABLE_WITH_AXE;
            case SHOVEL -> BlockTags.MINEABLE_WITH_SHOVEL;
            case HOE -> BlockTags.MINEABLE_WITH_HOE;
            case SWORD -> BlockTags.SWORD_EFFICIENT;
        };
    }
}
