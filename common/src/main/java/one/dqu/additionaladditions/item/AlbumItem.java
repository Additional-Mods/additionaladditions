package one.dqu.additionaladditions.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import one.dqu.additionaladditions.misc.AlbumContents;
import one.dqu.additionaladditions.registry.AdditionalMisc;

import java.util.List;
import java.util.Optional;

public class AlbumItem extends Item {
    public AlbumItem(Properties properties) {
        super(properties);
    }

    // CONTENTS

    private static AlbumContents getContents(ItemStack stack) {
        return stack.getOrDefault(AdditionalMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY);
    }

    private static void setContents(ItemStack stack, AlbumContents contents) {
        stack.set(AdditionalMisc.ALBUM_CONTENTS_COMPONENT.get(), contents);
    }

    private static boolean addDisc(ItemStack album, ItemStack disc) {
        AlbumContents contents = getContents(album);

        if (!AlbumContents.isValidItem(disc)) {
            return false;
        }

        if (contents.items().size() >= 8) {
            return false;
        }

        setContents(album, contents.add(disc));
        return true;
    }

    private static Optional<ItemStack> popDisc(ItemStack album) {
        AlbumContents contents = getContents(album);

        if (contents.items().isEmpty()) {
            return Optional.empty();
        }

        ItemStack popped = contents.items().getFirst();
        setContents(album, contents.pop());

        return Optional.of(popped);
    }

    // TOOLTIP

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        getContents(itemStack).addToTooltip(tooltipContext, list::add, tooltipFlag);
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }


    // CONTROLS

    @Override
    public boolean overrideStackedOnOther(ItemStack album, Slot slot, ClickAction clickAction, Player player) {
        if (clickAction != ClickAction.SECONDARY) {
            return false;
        }

        ItemStack other = slot.getItem();

        if (other.isEmpty()) {
            Optional<ItemStack> disc = popDisc(album);
            if (disc.isPresent()) {
                slot.safeInsert(disc.get());
                return true;
            }
        } else {
            if (addDisc(album, other)) {
                other.shrink(1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack album, ItemStack cursor, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        if (clickAction != ClickAction.SECONDARY || !slot.allowModification(player)) {
            return false;
        }

        if (cursor.isEmpty()) {
            Optional<ItemStack> disc = popDisc(album);
            if (disc.isPresent()) {
                slotAccess.set(disc.get());
                return true;
            }
        } else {
            if (addDisc(album, cursor)) {
                cursor.shrink(1);
                return true;
            }
        }
        return false;
    }
}
