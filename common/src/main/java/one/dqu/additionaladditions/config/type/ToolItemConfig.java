package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import one.dqu.additionaladditions.config.ToolLikeConfig;
import one.dqu.additionaladditions.config.io.Comment;

import java.util.List;

public record ToolItemConfig(
        float attackSpeed,

        float attackDamage,

        @Comment("Determines the speed of breaking blocks this tool is effective against (e.g. pickaxe -> stone). Look at Minecraft Wiki for vanilla values (mining efficiency, e.g. iron = 6.0).")
        float blockBreakSpeed,

        int durability
) implements ToolLikeConfig {
    public static final Codec<ToolItemConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.floatRange(0, Float.MAX_VALUE).fieldOf("attack_speed").forGetter(ToolItemConfig::rawAttackSpeed),
                    Codec.floatRange(0, Integer.MAX_VALUE).fieldOf("attack_damage").forGetter(ToolItemConfig::attackDamage),
                    Codec.floatRange(0, Float.MAX_VALUE).fieldOf("block_break_speed").forGetter(ToolItemConfig::blockBreakSpeed),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("durability").forGetter(ToolItemConfig::durability)
            ).apply(instance, ToolItemConfig::new)
    );

    private float rawAttackSpeed() {
        return attackSpeed;
    }

    // its 4.0f base or something and it ignores getSpeed() from tool material
    public float attackSpeed() {
        return attackSpeed - 4f;
    }

    public Tool toolProperties(ToolMaterial material, TagKey<Block> tagKey) {
        HolderGetter<Block> holderGetter = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return new Tool(List.of(Tool.Rule.deniesDrops(holderGetter.getOrThrow(material.incorrectBlocksForDrops())), Tool.Rule.minesAndDrops(holderGetter.getOrThrow(tagKey), this.blockBreakSpeed())), 1.0F, 1, true);
    }
}

