package dqu.additionaladditions.item;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.misc.PocketMusicSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class PocketJukeboxItem extends Item {
    public PocketJukeboxItem(Properties settings) {
        super(settings);
    }

    private static ItemStack getStoredDisc(ItemStack stack) {
        ItemContainerContents contents = stack.get(DataComponents.CONTAINER);
        if (contents != null && !contents.stream().toList().isEmpty()) {
            return contents.stream().toList().getFirst();
        }
        return ItemStack.EMPTY;
    }

    private static void removeStoredDisc(ItemStack stack) {
        stack.remove(DataComponents.CONTAINER);
    }

    private static void storeDisc(ItemStack stack, ItemStack discStack) {
        if (!hasDisc(stack)) {
            ItemContainerContents contents = ItemContainerContents.fromItems(List.of(discStack.copy()));
            stack.set(DataComponents.CONTAINER, contents);
        }
    }

    public static boolean hasDisc(ItemStack stack) {
        ItemContainerContents contents = stack.get(DataComponents.CONTAINER);
        return contents != null && !contents.stream().toList().isEmpty();
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
        if (!Config.getBool(ConfigValues.POCKET_JUKEBOX)) return false;
        if (clickType != ClickAction.SECONDARY) return false;

        if (!hasDisc(stack)) {
            ItemStack cursor = cursorStackReference.get();
            JukeboxPlayable jukeboxPlayable = cursor.get(DataComponents.JUKEBOX_PLAYABLE);

            if (jukeboxPlayable != null) {
                storeDisc(stack, cursor);
                cursorStackReference.set(ItemStack.EMPTY);

                if (player.level().isClientSide()) {
                    if (PocketMusicSoundInstance.instance != null) {
                        PocketMusicSoundInstance.instance.cancel();
                        PocketMusicSoundInstance.instance = null;
                    }

                    jukeboxPlayable.song().unwrap(player.level().registryAccess()).ifPresent(jukeboxSongHolder -> {
                        JukeboxSong jukeboxSong = jukeboxSongHolder.value();
                        Holder<SoundEvent> soundEvent = jukeboxSong.soundEvent();
                        PocketMusicSoundInstance.instance = new PocketMusicSoundInstance(
                            soundEvent.value(),
                            player,
                            stack,
                            false,
                            0.8f
                        );
                        PocketMusicSoundInstance.instance.play();
                    });
                }
            }
            return true;
        } else {
            ItemStack cursor = cursorStackReference.get();
            if (!cursor.isEmpty()) return false;

            ItemStack storedDisc = getStoredDisc(stack);
            cursorStackReference.set(storedDisc);

            if (player.level().isClientSide()) {
                if (PocketMusicSoundInstance.instance != null) {
                    PocketMusicSoundInstance.instance.cancel();
                    PocketMusicSoundInstance.instance = null;
                }
            }

            removeStoredDisc(stack);
            return true;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (!hasDisc(stack)) {
            tooltip.add(MutableComponent.create(new TranslatableContents("additionaladditions.gui.pocket_jukebox.tooltip", null, new String[]{})).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        } else {
            ItemStack storedDisc = getStoredDisc(stack);
            JukeboxPlayable jukeboxPlayable = storedDisc.get(DataComponents.JUKEBOX_PLAYABLE);
            if (jukeboxPlayable != null && context.registries() != null) {
                jukeboxPlayable.song().unwrap(context.registries()).ifPresent(jukeboxSongHolder -> {
                    JukeboxSong jukeboxSong = jukeboxSongHolder.value();
                    tooltip.add(jukeboxSong.description().copy().withStyle(ChatFormatting.GRAY));
                });
            }
        }
    }
}
