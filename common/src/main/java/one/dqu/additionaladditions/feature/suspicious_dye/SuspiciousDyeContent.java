package one.dqu.additionaladditions.feature.suspicious_dye;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.core.datagen.template.Recipes;
import one.dqu.additionaladditions.feature.suspicious_dye.glint.GlintColor;
import one.dqu.additionaladditions.registry.AAItems;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.registry.AATags;

import java.util.function.Supplier;

public class SuspiciousDyeContent {
    public static Supplier<SuspiciousDyeItem> suspiciousDye(DyeColor color) {
        @SuppressWarnings("Convert2MethodRef") // the mod flowers are null at this point, would crash
        ItemLike ingredient = switch (color) {
            case WHITE -> () -> AAItems.COTTONSHIVER.get();
            case BROWN -> () -> AAItems.MUDFLOWER.get();
            case RED -> () -> AAItems.CRIMSON_BLOSSOM.get();
            case ORANGE -> Items.TORCHFLOWER;
            case YELLOW -> () -> AAItems.AMBER_BLOSSOM.get();
            case LIME -> () -> AAItems.BULBUS.get();
            case GREEN -> () -> AAItems.SAWTOOTH_FERN.get();
            case CYAN -> Items.PITCHER_PLANT;
            case LIGHT_BLUE -> () -> AAItems.WISTERIA.get();
            case BLUE -> () -> AAItems.FROSTLEAF.get();
            case PURPLE -> () -> AAItems.SPIKEBLOSSOM.get();
            case MAGENTA -> () -> AAItems.SNAPDRAGON.get();
            case PINK -> () -> AAItems.LOTUS_LILY.get();
            default -> throw new IllegalArgumentException("No suspicious dye ingredient for color: " + color);
        };

        return AAReg.item(p -> new SuspiciousDyeItem(color, p))
                .config(Config.SUSPICIOUS_DYE)
                .props(p -> p
                        .stacksTo(1)
                        .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                        .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(color)))
                .creative(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS, CreativePosition.BEFORE)
                .model(Models::flat)
                .tags(AATags.SUSPICIOUS_DYES)
                .recipe(Recipes.brewing(Potions.WATER, ingredient))
                .make(color.getName() + "_suspicious_dye");
    }
}
