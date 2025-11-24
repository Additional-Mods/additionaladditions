package one.dqu.additionaladditions.test;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

public class ExampleTest {
    @GameTest(template = "additionaladditions:empty")
    public void test(GameTestHelper ctx) {
        ctx.succeed();
    }
}
