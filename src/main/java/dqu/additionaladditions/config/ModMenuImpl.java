//package dqu.additionaladditions.config;
//
//import com.terraformersmc.modmenu.api.ConfigScreenFactory;
//import com.terraformersmc.modmenu.api.ModMenuApi;
//import dqu.additionaladditions.gui.ConfigGui;
//import dqu.additionaladditions.gui.ConfirmGui;
//import dqu.additionaladditions.gui.CottonScreen;
//
//public class ModMenuImpl implements ModMenuApi {
//    @Override
//    public ConfigScreenFactory<?> getModConfigScreenFactory() {
//        return screen -> new CottonScreen(new ConfigGui()) {
//            public void onClose() {
//                this.minecraft.setScreen(new CottonScreen(new ConfirmGui()));
//            }
//        };
//    }
//}
