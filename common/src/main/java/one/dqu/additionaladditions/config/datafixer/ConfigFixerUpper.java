package one.dqu.additionaladditions.config.datafixer;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import one.dqu.additionaladditions.config.datafixer.fixer.*;
import one.dqu.additionaladditions.config.datafixer.schema.*;

public class ConfigFixerUpper implements DataFixer {
    public static final DataFixer INSTANCE = new ConfigFixerUpper();
    public static final int CURRENT_VERSION = 3;

    private final DataFixer fixer;

    private ConfigFixerUpper() {
        DataFixerBuilder builder = new DataFixerBuilder(CURRENT_VERSION);

        var V1 = builder.addSchema(1, ConfigV1Schema::new);

        var V2 = builder.addSchema(2, ConfigV2Schema::new);
        builder.addFixer(new ConfigV1V2ToolItemConfigFixer(V2));

        var V3 = builder.addSchema(3, ConfigV3Schema::new);
        builder.addFixer(new ConfigV2V3MaterialConfigFixer(V3));

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
