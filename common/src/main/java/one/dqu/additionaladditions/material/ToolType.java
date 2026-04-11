package one.dqu.additionaladditions.material;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public enum ToolType {
    SWORD,
    SPEAR,
    PICKAXE,
    AXE,
    SHOVEL,
    HOE;


    public TagKey<Block> mineableTag() {
        return switch (this) {
            case PICKAXE -> BlockTags.MINEABLE_WITH_PICKAXE;
            case AXE -> BlockTags.MINEABLE_WITH_AXE;
            case SHOVEL -> BlockTags.MINEABLE_WITH_SHOVEL;
            case HOE -> BlockTags.MINEABLE_WITH_HOE;
            case SWORD, SPEAR -> null;
        };
    }

    public boolean isWeapon() {
        return switch (this) {
            case SWORD, SPEAR -> true;
            default -> false;
        };
    }
}
