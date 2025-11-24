package one.dqu.additionaladditions.test;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import one.dqu.additionaladditions.config.ConfigLoader;

import java.util.Locale;

/**
 * Tests if the config loads correctly in Turkish locale.
 */
public class TurkishLocaleTest {
    @GameTest(template = "additionaladditions:empty")
    public void test(GameTestHelper ctx) {
        Locale locale = Locale.getDefault();
        Locale.setDefault(new Locale("tr", "TR"));
        try {
            ConfigLoader.load();
            ctx.succeed();
        } catch (Exception ex) {
            ctx.fail(ex.getMessage());
        } finally {
            Locale.setDefault(locale);
        }
    }
}
