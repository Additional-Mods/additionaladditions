package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.Config;
import dqu.additionaladditions.item.AdditionalMusicDiscItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class AdditionalMusicDiscs {
    public static final Identifier IDENTIFIER_2305 = new Identifier(AdditionalAdditions.namespace, "2305");
    public static final Identifier IDENTIFIER_3107 = new Identifier(AdditionalAdditions.namespace, "3107");
    public static final Identifier IDENTIFIER_1206 = new Identifier(AdditionalAdditions.namespace, "1206");

    public static final SoundEvent SOUND_EVENT_2305 = new SoundEvent(IDENTIFIER_2305);
    public static final SoundEvent SOUND_EVENT_3107 = new SoundEvent(IDENTIFIER_3107);
    public static final SoundEvent SOUND_EVENT_1206 = new SoundEvent(IDENTIFIER_1206);

    public static final MusicDiscItem MUSIC_DISC_2305 = new AdditionalMusicDiscItem(5, SOUND_EVENT_2305, new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final MusicDiscItem MUSIC_DISC_3107 = new AdditionalMusicDiscItem(7, SOUND_EVENT_3107, new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final MusicDiscItem MUSIC_DISC_1206 = new AdditionalMusicDiscItem(6, SOUND_EVENT_1206, new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));

    private static void registerSoundEvents() {
        Registry.register(Registry.SOUND_EVENT, IDENTIFIER_2305, SOUND_EVENT_2305);
        Registry.register(Registry.SOUND_EVENT, IDENTIFIER_3107, SOUND_EVENT_3107);
        Registry.register(Registry.SOUND_EVENT, IDENTIFIER_1206, SOUND_EVENT_1206);
    }

    private static void registerDiscs() {
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "music_disc_2305"), MUSIC_DISC_2305);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "music_disc_3107"), MUSIC_DISC_3107);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "music_disc_1206"), MUSIC_DISC_1206);
    }

    public static void registerAll() {
        if(!Config.get("MusicDiscs")) return;
        registerSoundEvents();
        registerDiscs();
    }
}
