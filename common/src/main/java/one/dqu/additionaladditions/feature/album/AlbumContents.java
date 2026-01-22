package one.dqu.additionaladditions.feature.album;

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
import one.dqu.additionaladditions.config.Config;
import org.jetbrains.annotations.Nullable;

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
        items = items.stream().map(ItemStack::copy).toList();
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
        addToTooltip(tooltipContext, consumer, tooltipFlag, null, null);
    }

    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, @Nullable Component albumName, @Nullable Integer highlight) {
        if (albumName != null) {
            consumer.accept(
                    Component.literal("")
                            .append(albumName)
                            .withStyle(ChatFormatting.YELLOW)
            );
        } else {
            consumer.accept(
                    Component.literal("")
                            .append(String.format("%s/%s ", items.size(), Config.ALBUM.get().capacity()))
                            .append(Component.translatable("additionaladditions.gui.album.discs"))
                            .withStyle(ChatFormatting.GRAY)
            );
        }

        int start = 0;
        int end = items.size();

        if (items.size() > 8) {
            if (highlight != null) {
                start = highlight - 4;
                if (start < 0) start = 0;
                if (start + 8 > items.size()) {
                    start = items.size() - 8;
                }
            }
            end = start + 8;
        }

        if (start > 0) {
            consumer.accept(CommonComponents.space().append(Component.literal("...").withStyle(ChatFormatting.GRAY)));
            start++;
        }

        for (int i = start; i < end; i++) {
            ItemStack disc = items.get(i);
            final int j = i;

            var jukeboxPlayable = disc.get(DataComponents.JUKEBOX_PLAYABLE);
            if (jukeboxPlayable != null) {
                jukeboxPlayable.addToTooltip(
                        tooltipContext,
                        component -> {
                            Component song = component;
                            if (highlight != null && j == highlight) {
                                song = song.copy().withStyle(ChatFormatting.WHITE);
                            }
                            consumer.accept(
                                    CommonComponents.space().append(song)
                            );
                        },
                        tooltipFlag
                );
            }
        }

        if (end < items.size()) {
            consumer.accept(CommonComponents.space().append(Component.literal("...").withStyle(ChatFormatting.GRAY)));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof AlbumContents other) {
            return ItemStack.listMatches(this.items, other.items);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ItemStack.hashStackList(this.items);
    }
}
