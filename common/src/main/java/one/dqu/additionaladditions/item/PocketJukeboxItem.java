package one.dqu.additionaladditions.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.misc.AlbumContents;
import one.dqu.additionaladditions.misc.PocketJukeboxPlayer;
import one.dqu.additionaladditions.registry.AdditionalItems;
import one.dqu.additionaladditions.registry.AdditionalMisc;

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

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
        if (!Config.POCKET_JUKEBOX.get().enabled()) return false;
        if (clickType != ClickAction.SECONDARY) return false;

        if (!hasDisc(stack)) {
            ItemStack cursor = cursorStackReference.get();
            if (cursor.isEmpty()) return false;

            AlbumContents contents = null;

            if (cursor.is(AdditionalItems.ALBUM.get())) {
                contents = cursor.getOrDefault(AdditionalMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY);
            } else if (AlbumContents.isValidItem(cursor)) {
                contents = new AlbumContents(List.of(cursor));
            }

            if (contents != null && !contents.items().isEmpty()) {
                storeDisc(stack, cursor);
                cursorStackReference.set(ItemStack.EMPTY); //todo this might need to be .shrink(1) just in case

                if (player.level().isClientSide()) {
                    PocketJukeboxPlayer.INSTANCE.play(contents, player, stack);
                }
                return true;
            }
            return false;
        } else {
            ItemStack cursor = cursorStackReference.get();
            if (!cursor.isEmpty()) return false;

            ItemStack storedDisc = getStoredDisc(stack);
            cursorStackReference.set(storedDisc);

            if (player.level().isClientSide()) {
                PocketJukeboxPlayer.INSTANCE.stop();
            }

            removeStoredDisc(stack);
            return true;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (!hasDisc(stack)) {
            tooltip.add(MutableComponent.create(new TranslatableContents("additionaladditions.gui.pocket_jukebox.tooltip", null, new String[]{})).withStyle(ChatFormatting.GRAY));
            return;
        }

        ItemStack storedDisc = getStoredDisc(stack);

        if (storedDisc.is(AdditionalItems.ALBUM.get())) {
            AlbumContents contents = storedDisc.getOrDefault(AdditionalMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY);
            contents.addToTooltip(context, tooltip::add, flag, storedDisc.getHoverName());
        } else {
            JukeboxPlayable jukeboxPlayable = storedDisc.get(DataComponents.JUKEBOX_PLAYABLE);
            if (jukeboxPlayable != null) {
                jukeboxPlayable.addToTooltip(context, tooltip::add, flag);
            }
        }
    }
}
