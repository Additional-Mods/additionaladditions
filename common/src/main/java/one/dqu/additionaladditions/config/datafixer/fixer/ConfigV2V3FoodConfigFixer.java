package one.dqu.additionaladditions.config.datafixer.fixer;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.type.FoodConfig;

public class ConfigV2V3FoodConfigFixer extends DataFix {
    public ConfigV2V3FoodConfigFixer(Schema outputSchema) {
        super(outputSchema, true);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        DSL.TypeReference typeRef = ConfigProperty.typeReference(FoodConfig.class);

        return writeFixAndRead(
                "ConfigV2V3FoodConfigFixer",
                getInputSchema().getType(typeRef),
                getOutputSchema().getType(typeRef),
                dynamic -> dynamic.update("food", food -> food.remove("eat_seconds"))
        );
    }
}
