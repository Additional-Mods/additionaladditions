package dqu.additionaladditions.item;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.misc.PocketMusicSoundInstance;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PocketJukeboxItem extends Item {
    public PocketJukeboxItem(Settings settings) {
        super(settings);
    }

    private static String nbtGetDisc(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        if (tag.contains("musicdisc")) {
            return tag.get("musicdisc").asString();
        } else return null;
    }

    private static void nbtRemoveDisc(ItemStack stack) {
        String disc = nbtGetDisc(stack);
        if (disc == null) return;

        NbtCompound tag = stack.getOrCreateNbt();
        tag.remove("musicdisc");
        stack.writeNbt(tag);
    }

    private static void nbtPutDisc(ItemStack stack, String disc) {
        String currentDisc = nbtGetDisc(stack);
        if (currentDisc != null) return;

        NbtCompound tag = stack.getOrCreateNbt();
        tag.putString("musicdisc", disc);
        stack.writeNbt(tag);
    }

    public static boolean hasDisc(ItemStack stack) {
        return (nbtGetDisc(stack) != null);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (!Config.getBool(ConfigValues.POCKET_JUKEBOX)) return false;
        if (clickType != ClickType.RIGHT) return false;
        World world = player.world;

        if (nbtGetDisc(stack) == null) {
            ItemStack cursor = cursorStackReference.get();
            if (cursor.getItem() instanceof MusicDiscItem) {
                Identifier id = Registry.ITEM.getId(cursor.getItem());
                MusicDiscItem discItem = (MusicDiscItem) Registry.ITEM.get(id);
                nbtPutDisc(stack, id.toString());
                cursorStackReference.set(ItemStack.EMPTY);

                if (world.isClient()) {
                    if (PocketMusicSoundInstance.instance != null) {
                        PocketMusicSoundInstance.instance.stop();
                        PocketMusicSoundInstance.instance = null;
                    }

                    PocketMusicSoundInstance.instance = new PocketMusicSoundInstance(discItem.getSound(), player, stack, false, 0.8f);
                    PocketMusicSoundInstance.instance.play();
                }
            }
            return true;
        } else {
            ItemStack cursor = cursorStackReference.get();
            if (!cursor.isEmpty()) return false;

            String disc = nbtGetDisc(stack);
            MusicDiscItem discItem = (MusicDiscItem) Registry.ITEM.get(new Identifier(disc));
            ItemStack discStack = new ItemStack(discItem, 1);
            cursorStackReference.set(discStack);

            if (world.isClient()) {
                if (PocketMusicSoundInstance.instance != null) {
                    PocketMusicSoundInstance.instance.stop();
                    PocketMusicSoundInstance.instance = null;
                }
            }

            nbtRemoveDisc(stack);
            return true;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String disc = nbtGetDisc(stack);
        if (disc == null) {
            tooltip.add(new TranslatableText("additionaladditions.gui.pocket_jukebox.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        } else {
            Item discItem = Registry.ITEM.get(new Identifier(disc));
            String description = discItem.getTranslationKey() + ".desc";
            tooltip.add(new TranslatableText(description));
        }
    }
}
