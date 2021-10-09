package dqu.additionaladditions.config;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class ConfirmGui extends LightweightGuiDescription {
    private static final Text SCREEN_TITLE = new LiteralText("Additional Additions ").formatted(Formatting.BOLD);
    private static final Text OPTION_DONE = new TranslatableText("gui.done");
    private static final Text DESCRIPTION = new TranslatableText("additionaladditions.gui.config.restart");

    public ConfirmGui() {
        WGridPanel root = (WGridPanel) rootPanel;

        WLabel title = new WLabel(SCREEN_TITLE).setHorizontalAlignment(HorizontalAlignment.CENTER);
        WLabel description = new WLabel(DESCRIPTION).setHorizontalAlignment(HorizontalAlignment.CENTER);
        WButton button = new WButton(OPTION_DONE).setAlignment(HorizontalAlignment.CENTER);
        button.setOnClick(() -> MinecraftClient.getInstance().openScreen(null));

        root.add(title, 0, 0, 16, 3);
        root.add(description, 0, 2, 16, 3);
        root.add(button, 0, 5, 16, 3);

        root.validate(this);
    }
}
