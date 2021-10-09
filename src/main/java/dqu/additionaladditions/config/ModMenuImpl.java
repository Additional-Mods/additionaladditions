package dqu.additionaladditions.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> new CottonScreen(new ConfigGui()) {
            public void onClose() {
                this.client.openScreen(screen);
            }
        };
    }
}
