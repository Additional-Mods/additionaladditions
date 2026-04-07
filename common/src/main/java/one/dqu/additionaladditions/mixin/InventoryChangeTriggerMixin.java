package one.dqu.additionaladditions.mixin;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.feature.album.AlbumContents;
import one.dqu.additionaladditions.registry.AAMisc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin that checks and fires the "fill_album_same_disc" advancement trigger.
 */
@Mixin(InventoryChangeTrigger.class)
public class InventoryChangeTriggerMixin {
    @Inject(method = "trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
    private void additionaladditions$onTrigger(ServerPlayer player, Inventory inventory, ItemStack stack, CallbackInfo ci) {
        if (!Config.ALBUM.get().enabled()) return;
        if (!stack.is(AAMisc.ALBUMS_TAG)) return;

        AlbumContents contents = stack.getOrDefault(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY);
        int capacity = Config.ALBUM.get().capacity();

        if (contents.items().size() != capacity) return;

        ItemStack first = contents.items().getFirst();
        boolean allIdentical = contents.items().stream()
                .allMatch(item -> ItemStack.isSameItemSameComponents(item, first));

        if (allIdentical) {
            AAMisc.FILL_ALBUM_SAME_DISC_TRIGGER.get().trigger(player);
        }
    }
}
