package one.dqu.additionaladditions.core.datagen.template;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.level.block.Block;
import one.dqu.additionaladditions.core.datagen.AABlockDatagen;
import one.dqu.additionaladditions.core.datagen.AAItemDatagen;
import one.dqu.additionaladditions.core.datagen.template.model.AlbumModelTemplate;
import one.dqu.additionaladditions.core.datagen.template.model.BarometerModelTemplate;
import one.dqu.additionaladditions.core.datagen.template.model.TallCrossModelTemplate;

/**
 * Datagen template presets for blocks and items.
 * To be used with with block/item builders. Generated in AABlockDatagen / AAItemDatagen.
 */
public final class Models {
    private Models() {
    }

    // Blocks

    public static void cube(Block block) {
        AABlockDatagen.currentGen().createTrivialCube(block);
    }

    public static void cross(Block block) {
        AABlockDatagen.currentGen().createCrossBlock(block, BlockModelGenerators.PlantType.NOT_TINTED);
    }

    public static void doublePlant(Block block) {
        AABlockDatagen.currentGen().createDoublePlant(block, BlockModelGenerators.PlantType.NOT_TINTED);
    }

    public static void tallCross(Block block) {
        TallCrossModelTemplate.createTallCrossBlock(AABlockDatagen.currentGen(), block);
    }

    // Items

    public static void flat(Item item) {
        AAItemDatagen.currentGen().generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    public static void handheld(Item item) {
        AAItemDatagen.currentGen().generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    public static void spear(Item item) {
        AAItemDatagen.currentGen().generateSpear(item);
    }

    public static void customModel(Item item) {
        AAItemDatagen.currentGen().declareCustomModelItem(item);
    }

    public static void album(Item item) {
        AlbumModelTemplate.createAlbum(AAItemDatagen.currentGen(), item);
    }

    public static void barometer(Item item) {
        BarometerModelTemplate.createBarometer(AAItemDatagen.currentGen(), item);
    }

    // armor with a trim_material dispatch
    public static void armorTrim(Item item, ResourceKey<EquipmentAsset> asset, ArmorType armorType) {
        Identifier slotTrimPrefix = switch (armorType) {
            case HELMET -> ItemModelGenerators.TRIM_PREFIX_HELMET;
            case CHESTPLATE -> ItemModelGenerators.TRIM_PREFIX_CHESTPLATE;
            case LEGGINGS -> ItemModelGenerators.TRIM_PREFIX_LEGGINGS;
            case BOOTS -> ItemModelGenerators.TRIM_PREFIX_BOOTS;
            default -> throw new IllegalArgumentException("Invalid armor slot: " + armorType);
        };
        AAItemDatagen.currentGen().generateTrimmableItem(item, asset, slotTrimPrefix, false);
    }
}
