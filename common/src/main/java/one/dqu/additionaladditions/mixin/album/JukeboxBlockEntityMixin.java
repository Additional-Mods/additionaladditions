package one.dqu.additionaladditions.mixin.album;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import one.dqu.additionaladditions.feature.album.AlbumContents;
import one.dqu.additionaladditions.feature.album.AlbumJukeboxExtension;
import one.dqu.additionaladditions.registry.AAMisc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlockEntity.class)
public class JukeboxBlockEntityMixin implements AlbumJukeboxExtension {
    @Unique
    private int additionaladditions$track = -1;
    @Unique
    private boolean additionaladditions$playing = false;

    @Inject(method = "setTheItem", at = @At("TAIL"))
    private void setTheItem(ItemStack stack, CallbackInfo ci) {
        additionaladditions$track = -1;
        if (!stack.is(AAMisc.ALBUMS_TAG)) {
            return;
        }
        additionaladditions$playing = true;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private static void tick(Level level, BlockPos blockPos, BlockState blockState, JukeboxBlockEntity jukebox, CallbackInfo ci) {
        if (jukebox.getSongPlayer().isPlaying()) {
            return;
        }

        if (jukebox instanceof AlbumJukeboxExtension albumJukebox) {
            if (!albumJukebox.additionaladditions$isPlaying()) {
                return;
            }

            AlbumContents album = jukebox.getTheItem().getOrDefault(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY);
            int nextTrack = albumJukebox.additionaladditions$getTrack() + 1;
            if (nextTrack >= album.items().size()) {
                albumJukebox.additionaladditions$setPlaying(false);
                return;
            } else {
                ItemStack nextRecord = album.items().get(nextTrack);
                jukebox.getSongPlayer().play(
                        level,
                        nextRecord.get(DataComponents.JUKEBOX_PLAYABLE).song().unwrap(level.registryAccess()).orElseThrow()
                );
                albumJukebox.additionaladditions$setTrack(nextTrack);
            }
        }
    }

    @Override
    public int additionaladditions$getTrack() {
        return additionaladditions$track;
    }

    @Override
    public void additionaladditions$setTrack(int track) {
        this.additionaladditions$track = track;
    }

    @Override
    public boolean additionaladditions$isPlaying() {
        return additionaladditions$playing;
    }

    @Override
    public void additionaladditions$setPlaying(boolean playing) {
        this.additionaladditions$playing = playing;
    }
}
