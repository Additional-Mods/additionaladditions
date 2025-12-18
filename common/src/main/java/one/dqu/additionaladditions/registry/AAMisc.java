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
import one.dqu.additionaladditions.glint.GlintColor;
import one.dqu.additionaladditions.misc.AlbumContents;
import one.dqu.additionaladditions.misc.RoseGoldTransmuteRecipe;
import one.dqu.additionaladditions.misc.SuspiciousDyeRecipe;

import java.util.function.Supplier;

public class AAMisc {
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

    public static final TagKey<Item> SUSPICIOUS_DYES_TAG = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "suspicious_dyes"));
    public static final TagKey<Block> WRENCH_BLACKLIST_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "wrench_blacklisted"));
    public static final TagKey<Item> ALBUMS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "albums"));

    public static final Supplier<RecipeSerializer<RoseGoldTransmuteRecipe>> ROSE_GOLD_TRANSMUTE_RECIPE_SERIALIZER = AARegistries.RECIPE_SERIALIZERS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rose_gold_transmute"),
            () -> new SimpleCraftingRecipeSerializer<>(RoseGoldTransmuteRecipe::new)
    );

    public static final Supplier<RecipeSerializer<SuspiciousDyeRecipe>> SUSPICIOUS_DYE_RECIPE_SERIALIZER = AARegistries.RECIPE_SERIALIZERS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "suspicious_dye"),
            () -> new SimpleCraftingRecipeSerializer<>(SuspiciousDyeRecipe::new)
    );

    public static void registerAll() {

    }
}
