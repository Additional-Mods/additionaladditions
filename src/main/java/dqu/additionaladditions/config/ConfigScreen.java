package dqu.additionaladditions.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class ConfigScreen extends Screen {
    private static final Text SCREEN_TITLE = new LiteralText("Additional Additions ").formatted(Formatting.BOLD).append(new TranslatableText("options.title"));
    private static final Text RESTART_GAME = new TranslatableText("additionaladditions.gui.config.restart").formatted(Formatting.YELLOW);
    private static final Text OPTION_ON = new TranslatableText("options.on").formatted(Formatting.GREEN);
    private static final Text OPTION_OFF = new TranslatableText("options.off").formatted(Formatting.RED);
    private static final Text OPTION_DONE = new TranslatableText("gui.done").formatted(Formatting.YELLOW);
    private final Screen parent;
    private static int scroll = 0;
    private static int maxScroll = 0;
    public ConfigScreen(Screen parent) {
        super(SCREEN_TITLE);
        this.parent = parent;
    }

    @Override
    public void init() {
        int counter = 0;
        this.clearChildren();
        for (String option : Config.properties.keySet()) {
            int y = getY(counter);
            counter++;
            this.addDrawableChild(new ButtonWidget(this.width/2, getY(counter), 200, 20, getName(option), button -> {
                Config.set(option, !Config.get(option));
                button.setMessage(getName(option));
            }));
        }
        maxScroll = getY(counter+1);
        this.addDrawableChild(new ButtonWidget(this.width/2-100, maxScroll, 200, 20, OPTION_DONE, button -> {
            this.client.openScreen(parent);
        }));
        maxScroll += 200;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        scroll += (int) (-amount * 2);
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;
        init();
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    private Text getName(String option) {
        Text value = Config.get(option) ? OPTION_ON : OPTION_OFF;
        return new LiteralText(option.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2")).append(": ").append(value);
    }

    private int getY(int counter) {
        return (50 * counter+1) + 10 - scroll;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width/5, 50, 0xFFFFFF);
        drawCenteredText(matrices, this.textRenderer, RESTART_GAME, this.width/5, 60, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
