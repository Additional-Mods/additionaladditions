package one.dqu.additionaladditions.feature.dye;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.core.builder.AAReg;
import one.dqu.additionaladditions.core.builder.CreativePosition;
import one.dqu.additionaladditions.core.datagen.template.Models;
import one.dqu.additionaladditions.feature.glint.GlintColor;
import one.dqu.additionaladditions.registry.AAMisc;
import one.dqu.additionaladditions.registry.AATags;

import java.util.function.Supplier;

public class SuspiciousDyes {
    public static Supplier<SuspiciousDyeItem> suspiciousDye(DyeColor color) {
        return AAReg.item(p -> new SuspiciousDyeItem(color, p))
                .config(Config.SUSPICIOUS_DYE)
                .props(p -> p
                        .stacksTo(1)
                        .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                        .component(AAMisc.GLINT_COLOR_COMPONENT.get(), new GlintColor(color)))
                .creative(Items.EXPERIENCE_BOTTLE, CreativeModeTabs.INGREDIENTS, CreativePosition.BEFORE)
                .model(Models::flat)
                .tags(AATags.SUSPICIOUS_DYES)
                .make(color.getName() + "_suspicious_dye");
    }
}
