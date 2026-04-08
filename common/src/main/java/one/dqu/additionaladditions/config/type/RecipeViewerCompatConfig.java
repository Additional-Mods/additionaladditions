package one.dqu.additionaladditions.config.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import one.dqu.additionaladditions.config.io.Comment;

public record RecipeViewerCompatConfig(
        @Comment("This config is used to disable Additional Additions integration from recipe viewers. Note that this is done automatically when the corresponding feature is disabled.\n  // Those properties are intended to be modified if you're changing the recipes for the items. If you want to disable them completely, please do so in their own config.\n  // Note that disabling a feature may not completely hide it from display in the recipe viewers. It is advised that you use your recipe viewers blacklisting / filtering feature to hide them completely, if needed.\n  // This can be done using filtering rules in REI, or the blacklist in JEI. Please search for more information in their documentation.\n  // If you have any questions, please don't hesitate to ask them in the Additional Additions discord server.\n\n  //  If disabled, recipes for usage of Suspicious Dye items will be hidden from recipe viewers, but remain craftable.\n  // If you want to disable the recipes completely - set this to false AND override the according recipe in a datapack with `{}`, remove it with kubejs, or disable the feature completely in its config.")
        boolean suspiciousDyeing,

        @Comment("If disabled, recipes for Rose Gold Equipment will be hidden from recipe viewers, but remain craftable.\n  // If you want to disable the recipes completely - set this to false AND override the according recipe in a datapack with `{}`, remove it with kubejs, or disable the feature completely in its config.")
        boolean roseGold,

        @Comment("If disabled, recipes for crafts of Colored Albums will be hidden from recipe viewers, but remain craftable.\n  // If you want to disable the recipes completely - set this to false AND override the according recipe in a datapack with `{}`, remove it with kubejs, or disable the feature completely in its config.")
        boolean albumDyeing
) {
    public static final Codec<RecipeViewerCompatConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("suspicious_dyeing").forGetter(RecipeViewerCompatConfig::suspiciousDyeing),
                    Codec.BOOL.fieldOf("rose_gold").forGetter(RecipeViewerCompatConfig::roseGold),
                    Codec.BOOL.fieldOf("album_dyeing").forGetter(RecipeViewerCompatConfig::albumDyeing)
            ).apply(instance, RecipeViewerCompatConfig::new)
    );
}
