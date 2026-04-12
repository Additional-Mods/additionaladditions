package one.dqu.additionaladditions.gametest.tests;

import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import one.dqu.additionaladditions.config.io.ConfigLoader;

import java.util.Locale;

public class MiscTests {
    public static void example(GameTestHelper ctx) {
        ctx.succeed();
    }

    public static void turkishLocale(GameTestHelper ctx) {
        Locale locale = Locale.getDefault();
        Locale.setDefault(new Locale("tr", "TR"));
        try {
            ConfigLoader.load();
            ctx.succeed();
        } catch (Exception ex) {
            ctx.fail(Component.literal(ex.getMessage()));
        } finally {
            Locale.setDefault(locale);
        }
    }
}
