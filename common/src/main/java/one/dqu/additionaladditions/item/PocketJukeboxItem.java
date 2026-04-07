package one.dqu.additionaladditions.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.feature.album.AlbumContents;
import one.dqu.additionaladditions.feature.PocketJukeboxPlayer;
import one.dqu.additionaladditions.registry.AAMisc;

import java.util.List;

public class PocketJukeboxItem extends Item {
    public PocketJukeboxItem(Properties settings) {
        super(settings);
    }

    public static ItemStack getStoredDisc(ItemStack stack) {
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

    private boolean insertDisc(ItemStack stack, ItemStack discStack, Player player) {
        AlbumContents contents = null;
        if (discStack.is(AAMisc.ALBUMS_TAG)) {
            contents = discStack.getOrDefault(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY);
        } else if (AlbumContents.isValidItem(discStack)) {
            contents = new AlbumContents(List.of(discStack));
        }

        if (contents == null || contents.items().isEmpty()) return false;

        storeDisc(stack, discStack);

        if (player.level().isClientSide()) {
            PocketJukeboxPlayer.INSTANCE.play(contents, player, stack);
        }
        return true;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
        if (!Config.POCKET_JUKEBOX.get().enabled()) return false;

        if (clickType == ClickAction.PRIMARY && !otherStack.isEmpty() && !hasDisc(stack)) {
            ItemStack cursor = cursorStackReference.get();
            if (!insertDisc(stack, cursor, player)) return false;
            cursor.shrink(1);
            return true;
        } else if (clickType == ClickAction.SECONDARY && otherStack.isEmpty() && hasDisc(stack)) {
            if (!cursorStackReference.get().isEmpty()) return false;
            cursorStackReference.set(getStoredDisc(stack));
            if (player.level().isClientSide()) PocketJukeboxPlayer.INSTANCE.stop();
            removeStoredDisc(stack);
            return true;
        }
        return false;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickAction, Player player) {
        if (!Config.POCKET_JUKEBOX.get().enabled()) return false;
        if (!slot.allowModification(player)) return false;

        if (clickAction == ClickAction.PRIMARY && !slot.getItem().isEmpty() && !hasDisc(stack)) {
            ItemStack slotItem = slot.getItem();
            if (!insertDisc(stack, slotItem, player)) return false;
            slot.set(ItemStack.EMPTY);
            return true;
        } else if (clickAction == ClickAction.SECONDARY && hasDisc(stack)) {
            ItemStack leftover = slot.safeInsert(getStoredDisc(stack));
            if (!leftover.isEmpty()) return false;
            if (player.level().isClientSide()) PocketJukeboxPlayer.INSTANCE.stop();
            removeStoredDisc(stack);
            return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (!hasDisc(stack)) {
            return;
        }

        ItemStack storedDisc = getStoredDisc(stack);

        if (storedDisc.is(AAMisc.ALBUMS_TAG)) {
            AlbumContents contents = storedDisc.getOrDefault(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY);

            int index = PocketJukeboxPlayer.INSTANCE.isPlayingFrom(stack)
                    ? PocketJukeboxPlayer.INSTANCE.getCurrentTrack()
                    : -1;

            contents.addToTooltip(context, tooltip::add, flag, storedDisc.getHoverName(), index);
        } else {
            JukeboxPlayable jukeboxPlayable = storedDisc.get(DataComponents.JUKEBOX_PLAYABLE);
            if (jukeboxPlayable != null) {
                jukeboxPlayable.addToTooltip(context, tooltip::add, flag);
            }
        }
    }
}
