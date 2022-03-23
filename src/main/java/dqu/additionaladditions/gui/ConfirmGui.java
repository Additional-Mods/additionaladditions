package dqu.additionaladditions.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ConfirmGui extends LightweightGuiDescription {
    private static final Component SCREEN_TITLE = new TextComponent("Additional Additions ").withStyle(ChatFormatting.BOLD);
    private static final Component OPTION_DONE = new TranslatableComponent("gui.done");
    private static final Component DESCRIPTION = new TranslatableComponent("additionaladditions.gui.config.restart");

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
