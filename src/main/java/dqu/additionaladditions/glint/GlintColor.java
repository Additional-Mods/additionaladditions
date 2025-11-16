package dqu.additionaladditions.glint;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record GlintColor(DyeColor color) implements StringRepresentable, TooltipProvider {
    public static final Codec<GlintColor> CODEC = DyeColor.CODEC
            .xmap(GlintColor::new, GlintColor::color);

    public static final StreamCodec<ByteBuf, GlintColor> STREAM_CODEC = DyeColor.STREAM_CODEC
            .map(GlintColor::new, GlintColor::color);

    @Override
    public String getSerializedName() {
        return color.getSerializedName();
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag) {
        String key = "additionaladditions.gui.glint." + color.getSerializedName();
        consumer.accept(
                MutableComponent.create(new TranslatableContents(key, null, new String[]{}))
                .withColor(color.getTextColor())
        );
    }
}
