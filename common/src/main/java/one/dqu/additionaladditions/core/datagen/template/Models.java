package one.dqu.additionaladditions.core.datagen.template;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import one.dqu.additionaladditions.core.datagen.AABlockDatagen;
import one.dqu.additionaladditions.core.datagen.AAItemDatagen;
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

    public static void customModel(Item item) {
        AAItemDatagen.currentGen().declareCustomModelItem(item);
    }
}
