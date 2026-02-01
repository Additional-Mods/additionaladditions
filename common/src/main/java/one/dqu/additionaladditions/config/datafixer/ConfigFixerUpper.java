package one.dqu.additionaladditions.config.datafixer;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import one.dqu.additionaladditions.config.datafixer.fixer.ConfigV1V2ToolItemConfigFixer;
import one.dqu.additionaladditions.config.datafixer.schema.ConfigV1Schema;
import one.dqu.additionaladditions.config.datafixer.schema.ConfigV2Schema;

public class ConfigFixerUpper implements DataFixer {
    public static final DataFixer INSTANCE = new ConfigFixerUpper();
    public static final int CURRENT_VERSION = 2;

    private final DataFixer fixer;

    private ConfigFixerUpper() {
        DataFixerBuilder builder = new DataFixerBuilder(CURRENT_VERSION);

        var V1 = builder.addSchema(1, ConfigV1Schema::new);

        var V2 = builder.addSchema(2, ConfigV2Schema::new);
        builder.addFixer(new ConfigV1V2ToolItemConfigFixer(V2));

        this.fixer = builder.build().fixer();
    }

    @Override
    public <T> Dynamic<T> update(DSL.TypeReference type, Dynamic<T> input, int version, int newVersion) {
        return fixer.update(type, input, version, newVersion);
    }

    @Override
    public Schema getSchema(int key) {
        return fixer.getSchema(key);
    }
}
