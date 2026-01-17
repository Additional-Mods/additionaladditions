package one.dqu.additionaladditions.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.block.Block;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.feature.album.AlbumContents;
import one.dqu.additionaladditions.feature.album.AlbumDyeRecipe;
import one.dqu.additionaladditions.feature.glint.GlintColor;
import one.dqu.additionaladditions.misc.*;

import java.util.function.Supplier;

public class AAMisc {
    /* DATA COMPONENTS */

    public static final Supplier<DataComponentType<GlintColor>> GLINT_COLOR_COMPONENT = AARegistries.DATA_COMPONENT_TYPES.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "glint_color"),
            () -> DataComponentType.<GlintColor>builder().persistent(GlintColor.CODEC).networkSynchronized(GlintColor.STREAM_CODEC).build()
    );

    public static final Supplier<DataComponentType<AlbumContents>> ALBUM_CONTENTS_COMPONENT = AARegistries.DATA_COMPONENT_TYPES.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "album_contents"),
            () -> DataComponentType.<AlbumContents>builder().persistent(AlbumContents.CODEC).networkSynchronized(AlbumContents.STREAM_CODEC).cacheEncoding().build()
    );

    public static final Supplier<DataComponentType<Integer>> WATER_LEVEL_COMPONENT = AARegistries.DATA_COMPONENT_TYPES.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "water_level"),
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
    );

    /* TAGS */

    public static final TagKey<Item> SUSPICIOUS_DYES_TAG = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "suspicious_dyes"));
    public static final TagKey<Block> WRENCH_BLACKLIST_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "wrench_blacklisted"));
    public static final TagKey<Item> ALBUMS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "albums"));

    /* RECIPE SERIALIZERS */

    public static final Supplier<RecipeSerializer<RoseGoldTransmuteRecipe>> ROSE_GOLD_TRANSMUTE_RECIPE_SERIALIZER = AARegistries.RECIPE_SERIALIZERS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rose_gold_transmute"),
            () -> new SimpleCraftingRecipeSerializer<>(RoseGoldTransmuteRecipe::new)
    );

    public static final Supplier<RecipeSerializer<SuspiciousDyeRecipe>> SUSPICIOUS_DYE_RECIPE_SERIALIZER = AARegistries.RECIPE_SERIALIZERS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "suspicious_dye"),
            () -> new SimpleCraftingRecipeSerializer<>(SuspiciousDyeRecipe::new)
    );

    public static final Supplier<RecipeSerializer<AlbumDyeRecipe>> ALBUM_DYE_RECIPE_SERIALIZER = AARegistries.RECIPE_SERIALIZERS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "album_dye"),
            () -> new SimpleCraftingRecipeSerializer<>(AlbumDyeRecipe::new)
    );

    /* ADVANCEMENT TRIGGERS */

    public static final Supplier<SimplePlayerTrigger> PLAY_POCKET_JUKEBOX_TRIGGER = AARegistries.TRIGGERS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "play_pocket_jukebox"),
            SimplePlayerTrigger::new
    );

    public static final Supplier<SimplePlayerTrigger> FERTILIZE_WITH_WATERING_CAN_TRIGGER = AARegistries.TRIGGERS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "fertilize_with_watering_can"),
            SimplePlayerTrigger::new
    );

    public static final Supplier<SimplePlayerTrigger> PLAY_ALBUM_TRIGGER = AARegistries.TRIGGERS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "play_album"),
            SimplePlayerTrigger::new
    );

    public static final Supplier<SimplePlayerTrigger> FILL_ALBUM_SAME_DISC_TRIGGER = AARegistries.TRIGGERS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "fill_album_same_disc"),
            SimplePlayerTrigger::new
    );

    public static final Supplier<SimplePlayerTrigger> ACTIVATE_TINTED_LAMP_TRIGGER = AARegistries.TRIGGERS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "activate_tinted_redstone_lamp"),
            SimplePlayerTrigger::new
    );

    public static final Supplier<SimplePlayerTrigger> ROPE_WORLD_HEIGHT = AARegistries.TRIGGERS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rope_world_height"),
            SimplePlayerTrigger::new
    );

    public static void registerAll() {

    }
}
