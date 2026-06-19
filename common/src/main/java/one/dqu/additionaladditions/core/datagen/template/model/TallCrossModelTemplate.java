package one.dqu.additionaladditions.core.datagen.template.model;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import one.dqu.additionaladditions.AdditionalAdditions;

import java.util.Optional;

public class TallCrossModelTemplate {
    private static final TextureSlot BOTTOM = TextureSlot.create("0");
    private static final TextureSlot TOP = TextureSlot.create("1");

    private static final ModelTemplate TALL_CROSS = new ModelTemplate(
            Optional.of(Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "block/tall_cross")),
            Optional.empty(),
            BOTTOM, TOP
    );

    public static void createTallCrossBlock(BlockModelGenerators gen, Block block) {
        String id = block.builtInRegistryHolder().key().identifier().getPath();

        TextureMapping textures = new TextureMapping()
                .put(BOTTOM, new Material(Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "block/" + id + "_bottom")))
                .put(TOP, new Material(Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "block/" + id + "_top")));

        Identifier model = TALL_CROSS.create(block, textures, gen.modelOutput);

        gen.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(model))
        );
    }
}
