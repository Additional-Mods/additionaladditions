package one.dqu.additionaladditions.config.datafixer.fixer;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.type.ToolItemConfig;

public class ConfigV1V2ToolItemConfigFixer extends DataFix {
    public ConfigV1V2ToolItemConfigFixer(Schema outputSchema) {
        super(outputSchema, true);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        DSL.TypeReference typeRef = ConfigProperty.typeReference(ToolItemConfig.class);

        return writeFixAndRead(
                "ConfigV1V2ToolItemConfigFixer",
                getInputSchema().getType(typeRef),
                getOutputSchema().getType(typeRef),
                dynamic -> {
                    return dynamic.set("block_break_speed", dynamic.createFloat(8.0f));
                }
        );
    }
}
