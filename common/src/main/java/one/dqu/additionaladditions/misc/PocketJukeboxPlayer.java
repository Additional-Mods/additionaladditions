package one.dqu.additionaladditions.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.item.JukeboxSong;
import one.dqu.additionaladditions.config.Config;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PocketJukeboxPlayer {
    public static final PocketJukeboxPlayer INSTANCE = new PocketJukeboxPlayer();

    private boolean isPlaying = false;
    private AlbumContents album;
    private Player player;
    private ItemStack jukeboxStack;
    private int currentTrack = -1;
    private PocketJukeboxPlayer.SoundInstance currentSong;

    private PocketJukeboxPlayer() {}

    public void play(AlbumContents album, Player player, ItemStack jukeboxStack) {
        if (this.isPlaying) {
            this.stop();
        }

        this.album = album;
        this.player = player;
        this.jukeboxStack = jukeboxStack;
        this.currentTrack = -1;
        this.isPlaying = true;

        this.startNextTrack();
    }

    public void stop() {
        if (this.currentSong != null) {
            Minecraft.getInstance().getSoundManager().stop(this.currentSong);
            this.currentSong = null;
        }
        this.isPlaying = false;
        this.album = null;
        this.player = null;
        this.jukeboxStack = null;
    }

    public void tick() {
        if (!this.isPlaying) {
            return;
        }

        if (!canContinuePlaying()) {
            this.stop();
            return;
        }

        if (this.currentSong == null || !Minecraft.getInstance().getSoundManager().isActive(this.currentSong)) {
            this.currentSong = null;
            this.startNextTrack();
        }
    }

    private boolean canContinuePlaying() {
        if (this.player == null || this.player.isDeadOrDying() || !Config.POCKET_JUKEBOX.get().enabled()) {
            return false;
        }
        if (this.jukeboxStack == null || this.jukeboxStack.isEmpty()) {
            return false;
        }

        // true if jukebox in player inventory or cursor
        if (ItemStack.matches(this.jukeboxStack, this.player.containerMenu.getCarried())) {
            return true;
        }
        if (this.player.getInventory().contains(this.jukeboxStack)) {
            return true;
        }

        return false;
    }

    private void startNextTrack() {
        if (this.album == null || this.player == null) {
            this.stop();
            return;
        }

        this.currentTrack++;

        if (this.currentTrack >= this.album.items().size()) {
            this.stop();
            return;
        }

        ItemStack discStack = this.album.items().get(this.currentTrack);
        JukeboxSong song = extractSong(discStack, this.player);

        if (song != null) {
            this.currentSong = SoundInstance.playFor(song, this.player);
        } else {
            this.startNextTrack(); //fallback
        }
    }

    @Nullable
    private static JukeboxSong extractSong(ItemStack discStack, Player player) {
        JukeboxPlayable jukeboxPlayable = discStack.get(DataComponents.JUKEBOX_PLAYABLE);
        if (jukeboxPlayable == null) {
            return null;
        }

        var songOptional = jukeboxPlayable.song().unwrap(player.level().registryAccess());
        return songOptional.map(holder -> holder.value()).orElse(null);
    }

    private static class SoundInstance extends AbstractTickableSoundInstance {
        private final Player player;

        private SoundInstance(JukeboxSong song, Player player) {
            super(song.soundEvent().value(), SoundSource.RECORDS, RandomSource.create());
            this.player = player;
            this.volume = 0.8f;
            this.looping = false;

            this.attenuation = Attenuation.NONE;
            this.relative = true;

            // coordinates are relative to player position when relative = true
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }

        // creates and automatically plays sound instance
        static SoundInstance playFor(JukeboxSong song, Player player) {
            SoundInstance music = new SoundInstance(song, player);
            Minecraft.getInstance().getSoundManager().play(music);
            Minecraft.getInstance().gui.setNowPlaying(song.description());
            return music;
        }

        @Override
        public void tick() {
            if (this.player.isDeadOrDying()) {
                this.stop();
                return;
            }
        }
    }
}
