package dqu.additionaladditions.item;

import dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class AdditionalSmithingTemplate extends SmithingTemplateItem {
    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;

    private AdditionalSmithingTemplate(Component appliesTo, Component ingredients, Component upgradeDescription, Component baseSlotDescription, Component additionsSlotDescription, List<ResourceLocation> baseSlotEmptyIcons, List<ResourceLocation> additionalSlotEmptyIcons) {
        super(appliesTo, ingredients, upgradeDescription, baseSlotDescription, additionsSlotDescription, baseSlotEmptyIcons, additionalSlotEmptyIcons);
    }

    public static SmithingTemplateItem create(String id, List<ResourceLocation> baseSlotEmptyIcons, List<ResourceLocation> additionalSlotEmptyIcons) {
        var appliesTo = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(AdditionalAdditions.namespace, "smithing_template."+id+".applies_to"))).withStyle(DESCRIPTION_FORMAT);
        var upgradeDescription = Component.translatable(Util.makeDescriptionId("upgrade", new ResourceLocation(AdditionalAdditions.namespace, id))).withStyle(TITLE_FORMAT);
        var ingredients = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(AdditionalAdditions.namespace, "smithing_template."+id+".ingredients"))).withStyle(DESCRIPTION_FORMAT);
        var baseSlotDescription = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(AdditionalAdditions.namespace, "smithing_template."+id+".base_slot_description")));
        var additionsSlotDescription = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(AdditionalAdditions.namespace, "smithing_template."+id+".additions_slot_description")));

        return new AdditionalSmithingTemplate(
                appliesTo,
                ingredients,
                upgradeDescription,
                baseSlotDescription,
                additionsSlotDescription,
                baseSlotEmptyIcons,
                additionalSlotEmptyIcons
        );
    }

    public static List<ResourceLocation> iconsEquipment() {
        return List.of(
                SmithingIcon.HELMET.location(),
                SmithingIcon.CHESTPLATE.location(),
                SmithingIcon.LEGGINGS.location(),
                SmithingIcon.BOOTS.location(),
                SmithingIcon.HOE.location(),
                SmithingIcon.AXE.location(),
                SmithingIcon.PICKAXE.location(),
                SmithingIcon.SWORD.location(),
                SmithingIcon.SHOVEL.location()
        );
    }

    public enum SmithingIcon {
        HELMET(new ResourceLocation("item/empty_armor_slot_helmet")),
        CHESTPLATE(new ResourceLocation("item/empty_armor_slot_chestplate")),
        LEGGINGS(new ResourceLocation("item/empty_armor_slot_leggings")),
        BOOTS(new ResourceLocation("item/empty_armor_slot_boots")),
        HOE(new ResourceLocation("item/empty_slot_hoe")),
        AXE(new ResourceLocation("item/empty_slot_axe")),
        SWORD(new ResourceLocation("item/empty_slot_sword")),
        SHOVEL(new ResourceLocation("item/empty_slot_shovel")),
        PICKAXE(new ResourceLocation("item/empty_slot_pickaxe")),
        INGOT(new ResourceLocation("item/empty_slot_ingot")),
        REDSTONE_DUST(new ResourceLocation("item/empty_slot_redstone_dust")),
        QUARTZ(new ResourceLocation("item/empty_slot_quartz")),
        EMERALD(new ResourceLocation("item/empty_slot_emerald")),
        DIAMOND(new ResourceLocation("item/empty_slot_diamond")),
        LAPIS_LAZULI(new ResourceLocation("item/empty_slot_lapis_lazuli")),
        AMETHYST_SHARD(new ResourceLocation("item/empty_slot_amethyst_shard")),
        RING(new ResourceLocation(AdditionalAdditions.namespace, "item/empty_slot_ring")),
        ALLOY(new ResourceLocation(AdditionalAdditions.namespace, "item/empty_slot_alloy"));

        final ResourceLocation resourceLocation;

        SmithingIcon(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public ResourceLocation location() {
            return resourceLocation;
        }
    }
}
