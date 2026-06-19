package one.dqu.additionaladditions.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.core.util.SimplePlayerTrigger;
import one.dqu.additionaladditions.feature.album.AlbumContents;
import one.dqu.additionaladditions.feature.glint.GlintColor;
import one.dqu.additionaladditions.recipe.BrewingRecipe;
import one.dqu.additionaladditions.recipe.SuspiciousDyeRecipe;

import java.util.function.Supplier;

public class AAMisc {
    /* DATA COMPONENTS */

    public static final Supplier<DataComponentType<GlintColor>> GLINT_COLOR_COMPONENT = AARegistries.DATA_COMPONENT_TYPES.register(
            Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "glint_color"),
            () -> DataComponentType.<GlintColor>builder().persistent(GlintColor.CODEC).networkSynchronized(GlintColor.STREAM_CODEC).build()
    );

    public static final Supplier<DataComponentType<AlbumContents>> ALBUM_CONTENTS_COMPONENT = AARegistries.DATA_COMPONENT_TYPES.register(
            Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "album_contents"),
            () -> DataComponentType.<AlbumContents>builder().persistent(AlbumContents.CODEC).networkSynchronized(AlbumContents.STREAM_CODEC).cacheEncoding().build()
    );

    public static final Supplier<DataComponentType<Integer>> WATER_LEVEL_COMPONENT = AARegistries.DATA_COMPONENT_TYPES.register(
            Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "water_level"),
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
    );

    /* RECIPE SERIALIZERS AND TYPES */

    public static final Supplier<RecipeSerializer<SuspiciousDyeRecipe>> SUSPICIOUS_DYE_RECIPE_SERIALIZER = AARegistries.RECIPE_SERIALIZERS.register(
            Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "suspicious_dye"),
            () -> SuspiciousDyeRecipe.SERIALIZER
    );

    public static final Supplier<RecipeSerializer<BrewingRecipe>> BREWING_RECIPE_SERIALIZER = AARegistries.RECIPE_SERIALIZERS.register(
            Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "brewing"),
            () -> BrewingRecipe.SERIALIZER
    );

    public static final Supplier<RecipeType<BrewingRecipe>> BREWING_RECIPE_TYPE = AARegistries.RECIPE_TYPES.register(
            Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "brewing"),
            () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "additionaladditions:brewing";
                }
            }
    );

    public static final ResourceKey<RecipePropertySet> BREWING_RECIPE_PROPERTY_SET =
            ResourceKey.create(RecipePropertySet.TYPE_KEY, Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "brewing"));

    /* ADVANCEMENT TRIGGERS */

    public static final Supplier<SimplePlayerTrigger> FERTILIZE_WITH_WATERING_CAN_TRIGGER = AARegistries.TRIGGERS.register(
            Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "fertilize_with_watering_can"),
            SimplePlayerTrigger::new
    );

    public static final Supplier<SimplePlayerTrigger> FILL_ALBUM_SAME_DISC_TRIGGER = AARegistries.TRIGGERS.register(
            Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "fill_album_same_disc"),
            SimplePlayerTrigger::new
    );

    public static final Supplier<SimplePlayerTrigger> ACTIVATE_TINTED_LAMP_TRIGGER = AARegistries.TRIGGERS.register(
            Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "activate_tinted_redstone_lamp"),
            SimplePlayerTrigger::new
    );

    public static final Supplier<SimplePlayerTrigger> ROPE_WORLD_HEIGHT = AARegistries.TRIGGERS.register(
            Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "rope_world_height"),
            SimplePlayerTrigger::new
    );

    public static void registerAll() {

    }
}
