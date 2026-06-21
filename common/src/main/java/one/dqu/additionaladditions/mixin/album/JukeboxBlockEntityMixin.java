package one.dqu.additionaladditions.mixin.album;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import one.dqu.additionaladditions.feature.album.AlbumContents;
import one.dqu.additionaladditions.feature.album.AlbumJukeboxExtension;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.registry.AATags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin implements AlbumJukeboxExtension {
    @Shadow
    public abstract ItemStack getTheItem();

    @Unique
    private int additionaladditions$track = -1;
    @Unique
    private boolean additionaladditions$playing = false;

    @Inject(method = "setTheItem", at = @At("TAIL"))
    private void setTheItem(ItemStack stack, CallbackInfo ci) {
        additionaladditions$track = -1;
        if (!stack.is(AATags.ALBUMS)) {
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
                jukebox.onSongChanged();
            } else {
                ItemStack nextRecord = album.items().get(nextTrack);
                albumJukebox.additionaladditions$setTrack(nextTrack);
                jukebox.getSongPlayer().play(
                        level,
                        nextRecord.get(DataComponents.JUKEBOX_PLAYABLE).song()
                );
            }
        }
    }

    @Inject(method = "getComparatorOutput", at = @At("RETURN"), cancellable = true)
    private void albumComparatorOutput(CallbackInfoReturnable<Integer> cir) {
        if (cir.getReturnValue() != 0) return;
        JukeboxBlockEntity jukebox = (JukeboxBlockEntity) (Object) this;
        if (jukebox instanceof AlbumJukeboxExtension albumJukebox) {
            if (!albumJukebox.additionaladditions$isPlaying()) return;

            AlbumContents album = getTheItem().getOrDefault(AAMisc.ALBUM_CONTENTS_COMPONENT.get(), AlbumContents.EMPTY);

            int track = albumJukebox.additionaladditions$getTrack();
            if (track < 0 || track >= album.items().size()) return;

            JukeboxPlayable song = album.items().get(track).get(DataComponents.JUKEBOX_PLAYABLE);
            if (song == null) return;

            cir.setReturnValue(song.song().value().comparatorOutput());
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
