package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.item.AdditionalMusicDiscItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class AdditionalMusicDiscs {
    public static final Identifier IDENTIFIER_0308 = new Identifier(AdditionalAdditions.namespace, "0308");
    public static final Identifier IDENTIFIER_1007 = new Identifier(AdditionalAdditions.namespace, "1007");
    public static final Identifier IDENTIFIER_1507 = new Identifier(AdditionalAdditions.namespace, "1507");

    public static final SoundEvent SOUND_EVENT_0308 = new SoundEvent(IDENTIFIER_0308);
    public static final SoundEvent SOUND_EVENT_1007 = new SoundEvent(IDENTIFIER_1007);
    public static final SoundEvent SOUND_EVENT_1507 = new SoundEvent(IDENTIFIER_1507);

    public static final MusicDiscItem MUSIC_DISC_0308 = new AdditionalMusicDiscItem(5, SOUND_EVENT_0308, new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final MusicDiscItem MUSIC_DISC_1007 = new AdditionalMusicDiscItem(7, SOUND_EVENT_1007, new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
    public static final MusicDiscItem MUSIC_DISC_1507 = new AdditionalMusicDiscItem(6, SOUND_EVENT_1507, new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));

    private static void registerSoundEvents() {
        Registry.register(Registry.SOUND_EVENT, IDENTIFIER_0308, SOUND_EVENT_0308);
        Registry.register(Registry.SOUND_EVENT, IDENTIFIER_1007, SOUND_EVENT_1007);
        Registry.register(Registry.SOUND_EVENT, IDENTIFIER_1507, SOUND_EVENT_1507);
    }

    private static void registerDiscs() {
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "music_disc_0308"), MUSIC_DISC_0308);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "music_disc_1007"), MUSIC_DISC_1007);
        Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "music_disc_1507"), MUSIC_DISC_1507);
    }

    public static void registerAll() {
        registerSoundEvents();
        registerDiscs();
    }
}
