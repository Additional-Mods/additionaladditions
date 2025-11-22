package one.dqu.additionaladditions.misc;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public record AlbumContents(List<ItemStack> items) implements TooltipProvider {
    public static final AlbumContents EMPTY = new AlbumContents(List.of());

    public static final Codec<AlbumContents> CODEC =
            ItemStack.CODEC.listOf().xmap(AlbumContents::new, AlbumContents::items);
    public static final StreamCodec<RegistryFriendlyByteBuf, AlbumContents> STREAM_CODEC =
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()).map(AlbumContents::new, AlbumContents::items);

    public AlbumContents {
        items = List.copyOf(items);
    }

    public static boolean isValidItem(ItemStack itemStack) {
        return itemStack != null && !itemStack.isEmpty() && itemStack.has(DataComponents.JUKEBOX_PLAYABLE);
    }

    public AlbumContents add(ItemStack itemStack) {
        if (!isValidItem(itemStack)) return this;

        List<ItemStack> newItems = new ArrayList<>(items);
        newItems.addFirst(itemStack.copyWithCount(1));

        return new AlbumContents(newItems);
    }

    public AlbumContents pop() {
        if (items.isEmpty()) return this;

        List<ItemStack> newItems = new ArrayList<>(items);
        newItems.removeFirst();

        return new AlbumContents(newItems);
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        consumer.accept(Component.translatable("additionaladditions.gui.album.discs").withStyle(ChatFormatting.GRAY));
        for (ItemStack disc : items) {
            disc.get(DataComponents.JUKEBOX_PLAYABLE).addToTooltip(
                    tooltipContext,
                    component -> {
                        consumer.accept(CommonComponents.space().append(component));
                    },
                    tooltipFlag
            );
        }
    }
}
