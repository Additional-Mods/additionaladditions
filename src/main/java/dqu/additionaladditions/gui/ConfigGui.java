package dqu.additionaladditions.gui;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.value.ConfigValue;
import dqu.additionaladditions.config.value.ConfigValueType;
import dqu.additionaladditions.config.value.ConfigValues;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class ConfigGui extends LightweightGuiDescription {
    private static final Text OPTION_DONE = new TranslatableText("gui.done");
    private static final Text SCREEN_TITLE = new LiteralText("Additional Additions ").formatted(Formatting.BOLD).append(new TranslatableText("options.title"));

    public ConfigGui() {
        WGridPanel root = (WGridPanel) rootPanel;
        WBox box = new WBox(Axis.VERTICAL);

        WLabel label = new WLabel(SCREEN_TITLE).setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 16, 2);

        for (ConfigValues value : ConfigValues.values()) {
            if (value.getType() != ConfigValueType.BOOLEAN) continue;
            WToggleButton button = new WToggleButton(new LiteralText(value.getValue()));
            button.setToggle(Config.getBool(value));
            button.setOnToggle(on -> Config.set(value, on));
            box.add(button);
        }

        root.add(new WScrollPanel(box), 0, 1, 16, 10);

        WButton doneButton = new WButton(OPTION_DONE);
        doneButton.setOnClick(() -> MinecraftClient.getInstance().setScreen(new CottonScreen(new ConfirmGui())));
        root.add(doneButton, 0, 12, 8, 3);

        root.validate(this);
    }
}
