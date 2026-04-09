package one.dqu.additionaladditions.config.datafixer.fixer;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.tags.BlockTags;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.type.MaterialConfig;

public class ConfigV2V3MaterialConfigFixer extends DataFix {
    public ConfigV2V3MaterialConfigFixer(Schema outputSchema) {
        super(outputSchema, true);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        DSL.TypeReference typeRef = ConfigProperty.typeReference(MaterialConfig.class);

        return writeFixAndRead(
                "ConfigV2V3MaterialConfigFixer",
                getInputSchema().getType(typeRef),
                getOutputSchema().getType(typeRef),
                dynamic -> {
                    // Remove repair_ingredient field
                    dynamic = dynamic.remove("repair_ingredient");

                    // Add incorrect_blocks_for_drops with default
                    dynamic = dynamic.set("incorrect_blocks_for_drops",
                            dynamic.emptyMap()
                                    .set("block_tag", dynamic.createString("#" + BlockTags.INCORRECT_FOR_IRON_TOOL.location()))
                    );

                    return dynamic;
                }
        );
    }
}
