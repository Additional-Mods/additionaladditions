package dqu.additionaladditions.gui;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.value.ConfigValueType;
import dqu.additionaladditions.config.ConfigValues;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;

public class ConfigGui extends LightweightGuiDescription {
    private static final Component OPTION_DONE = MutableComponent.create(new TranslatableContents("gui.done"));
    private static final Component SCREEN_TITLE = MutableComponent.create(new LiteralContents("Additional Additions ")).withStyle(ChatFormatting.BOLD).append(MutableComponent.create(new LiteralContents("options.title")));

    public ConfigGui() {
        WGridPanel root = (WGridPanel) rootPanel;
        WBox box = new WBox(Axis.VERTICAL);

        WLabel label = new WLabel(SCREEN_TITLE).setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 16, 2);

        for (ConfigValues value : ConfigValues.values()) {
            if (value.getType() == ConfigValueType.BOOLEAN) {
                WToggleButton button = new WToggleButton(MutableComponent.create(new LiteralContents(value.getProperty().key())));
                button.setToggle(Config.getBool(value));
                button.setOnToggle(on -> Config.set(value, on));
                box.add(button);
            } else {
                WLabel text = new WLabel(MutableComponent.create(new LiteralContents(value.getProperty().key() + " | Unable to manage ingame. Edit in file instead.")));
                box.add(text);
            }
        }

        root.add(new WScrollPanel(box), 0, 1, 16, 10);

        WButton doneButton = new WButton(OPTION_DONE);
        doneButton.setOnClick(() -> Minecraft.getInstance().setScreen(new CottonScreen(new ConfirmGui())));
        root.add(doneButton, 0, 12, 8, 3);

        root.validate(this);
    }
}
