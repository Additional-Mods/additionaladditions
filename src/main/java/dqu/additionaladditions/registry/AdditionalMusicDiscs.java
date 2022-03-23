package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.item.AdditionalMusicDiscItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;

public class AdditionalMusicDiscs {
    public static final ResourceLocation IDENTIFIER_0308 = new ResourceLocation(AdditionalAdditions.namespace, "0308");
    public static final ResourceLocation IDENTIFIER_1007 = new ResourceLocation(AdditionalAdditions.namespace, "1007");
    public static final ResourceLocation IDENTIFIER_1507 = new ResourceLocation(AdditionalAdditions.namespace, "1507");

    public static final SoundEvent SOUND_EVENT_0308 = new SoundEvent(IDENTIFIER_0308);
    public static final SoundEvent SOUND_EVENT_1007 = new SoundEvent(IDENTIFIER_1007);
    public static final SoundEvent SOUND_EVENT_1507 = new SoundEvent(IDENTIFIER_1507);

    public static final RecordItem MUSIC_DISC_0308 = new AdditionalMusicDiscItem(5, SOUND_EVENT_0308, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1).rarity(Rarity.RARE));
    public static final RecordItem MUSIC_DISC_1007 = new AdditionalMusicDiscItem(7, SOUND_EVENT_1007, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1).rarity(Rarity.RARE));
    public static final RecordItem MUSIC_DISC_1507 = new AdditionalMusicDiscItem(6, SOUND_EVENT_1507, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1).rarity(Rarity.RARE));

    private static void registerSoundEvents() {
        Registry.register(Registry.SOUND_EVENT, IDENTIFIER_0308, SOUND_EVENT_0308);
        Registry.register(Registry.SOUND_EVENT, IDENTIFIER_1007, SOUND_EVENT_1007);
        Registry.register(Registry.SOUND_EVENT, IDENTIFIER_1507, SOUND_EVENT_1507);
    }

    private static void registerDiscs() {
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "music_disc_0308"), MUSIC_DISC_0308);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "music_disc_1007"), MUSIC_DISC_1007);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "music_disc_1507"), MUSIC_DISC_1507);
    }

    public static void registerAll() {
        registerSoundEvents();
        registerDiscs();
    }
}
