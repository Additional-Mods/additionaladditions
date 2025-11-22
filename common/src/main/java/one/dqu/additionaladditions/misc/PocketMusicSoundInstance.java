package one.dqu.additionaladditions.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.JukeboxSong;
import one.dqu.additionaladditions.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class PocketMusicSoundInstance extends AbstractTickableSoundInstance {
    private final Player playerEntity;
    private final ItemStack stack;
    private final JukeboxSong jukeboxSong;
    public static PocketMusicSoundInstance instance;

    public PocketMusicSoundInstance(JukeboxSong jukeboxSong, Player playerEntity, ItemStack stack, boolean repeat, float volume) {
        super(jukeboxSong.soundEvent().value(), SoundSource.RECORDS, RandomSource.create());
        this.jukeboxSong = jukeboxSong;
        this.playerEntity = playerEntity;
        this.stack = stack;
        this.looping = repeat;
        this.volume = volume;
        this.x = this.playerEntity.getX();
        this.y = this.playerEntity.getY();
        this.z = this.playerEntity.getZ();
    }

    @Override
    public void tick() {
        ItemStack cursor = this.playerEntity.containerMenu.getCarried();
        if (cursor == null) cursor = ItemStack.EMPTY;
        boolean hasDisc = ItemStack.matches(cursor, stack) || this.playerEntity.getInventory().contains(stack);

        if (this.playerEntity.isDeadOrDying() || !hasDisc || !Config.POCKET_JUKEBOX.get().enabled()) {
            this.cancel();
        } else {
            this.x = this.playerEntity.getX();
            this.y = this.playerEntity.getY();
            this.z = this.playerEntity.getZ();
        }
    }

    public void cancel() {
        this.stop();
    }

    public void play() {
        Minecraft.getInstance().getSoundManager().play(this);
        Minecraft.getInstance().gui.setNowPlaying(jukeboxSong.description());
    }
 }
