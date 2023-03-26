package dqu.additionaladditions.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;

public class ConfirmGui extends LightweightGuiDescription {
    private static final Component SCREEN_TITLE = MutableComponent.create(new LiteralContents("Additional Additions ")).withStyle(ChatFormatting.BOLD);
    private static final Component OPTION_DONE = MutableComponent.create(new TranslatableContents("gui.done", null, new String[]{}));
    private static final Component DESCRIPTION = MutableComponent.create(new TranslatableContents("additionaladditions.gui.config.restart", null, new String[]{}));

    public ConfirmGui() {
        WGridPanel root = (WGridPanel) rootPanel;

        WLabel title = new WLabel(SCREEN_TITLE).setHorizontalAlignment(HorizontalAlignment.CENTER);
        WLabel description = new WLabel(DESCRIPTION).setHorizontalAlignment(HorizontalAlignment.CENTER);
        WButton button = new WButton(OPTION_DONE).setAlignment(HorizontalAlignment.CENTER);
        button.setOnClick(() -> Minecraft.getInstance().setScreen(null));

        root.add(title, 0, 0, 16, 3);
        root.add(description, 0, 2, 16, 3);
        root.add(button, 0, 5, 16, 3);

        root.validate(this);
    }
}
