package dqu.additionaladditions.misc;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

@Environment(EnvType.CLIENT)
public class PocketMusicSoundInstance extends MovingSoundInstance {
    private final PlayerEntity playerEntity;
    private final ItemStack stack;
    public static PocketMusicSoundInstance instance;

    public PocketMusicSoundInstance(SoundEvent soundEvent, PlayerEntity playerEntity, ItemStack stack, boolean repeat, float volume) {
        super(soundEvent, SoundCategory.RECORDS);
        this.playerEntity = playerEntity;
        this.stack = stack;
        this.repeat = repeat;
        this.volume = volume;
        this.x = this.playerEntity.getX();
        this.y = this.playerEntity.getY();
        this.z = this.playerEntity.getZ();
    }

    @Override
    public void tick() {
        ItemStack cursor = this.playerEntity.currentScreenHandler.getCursorStack();
        if (cursor == null) cursor = ItemStack.EMPTY;
        boolean hasDisc = ItemStack.areEqual(cursor, stack) || this.playerEntity.getInventory().contains(stack);

        if (this.playerEntity.isDead() || !hasDisc || !Config.getBool(ConfigValues.POCKET_JUKEBOX)) {
            this.setDone();
        } else {
            this.x = this.playerEntity.getX();
            this.y = this.playerEntity.getY();
            this.z = this.playerEntity.getZ();
        }
    }

    public void stop() {
        this.setDone();
    }

    public void play() {
        MinecraftClient.getInstance().getSoundManager().play(this);
    }
 }
