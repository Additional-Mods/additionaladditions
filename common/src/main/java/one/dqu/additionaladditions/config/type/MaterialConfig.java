package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import one.dqu.additionaladditions.config.io.Comment;
import one.dqu.additionaladditions.config.type.unit.BlockTagUnitConfig;
import one.dqu.additionaladditions.config.type.unit.ItemTagUnitConfig;
import one.dqu.additionaladditions.config.type.unit.SoundEventUnitConfig;

public record MaterialConfig(
        @Comment("Determines armor toughness of this material.")
        float toughness,

        @Comment("Determines armor knockback resistance of this material.")
        float knockbackResistance,

        @Comment("Determines the enchantability of this material (affects how the item is enchanted on the enchanting table).")
        int enchantability,

        @Comment("Determines the equip sound of armor of this material.")
        Holder<SoundEvent> equipSound,

        @Comment("Repair ingredient for items of this material (on an anvil).")
        TagKey<Item> repairIngredient,

        @Comment("Block tag of blocks that won't drop when destroyed with tools of this material.\n  // This effectively determines the tier of the material.")
        TagKey<Block> incorrectBlocksForDrops
) {
    public static final Codec<MaterialConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.floatRange(0.0f, Float.MAX_VALUE).fieldOf("toughness").forGetter(MaterialConfig::toughness),
                    Codec.floatRange(0.0f, Float.MAX_VALUE).fieldOf("knockback_resistance").forGetter(MaterialConfig::knockbackResistance),
                    Codec.intRange(0, Integer.MAX_VALUE).fieldOf("enchantability").forGetter(MaterialConfig::enchantability),
                    SoundEventUnitConfig.CODEC.fieldOf("equip_sound").forGetter(MaterialConfig::equipSound),
                    ItemTagUnitConfig.CODEC.fieldOf("repair_ingredient").forGetter(MaterialConfig::repairIngredient),
                    BlockTagUnitConfig.CODEC.fieldOf("incorrect_blocks_for_drops").forGetter(MaterialConfig::incorrectBlocksForDrops)
            ).apply(instance, MaterialConfig::new)
    );
}
