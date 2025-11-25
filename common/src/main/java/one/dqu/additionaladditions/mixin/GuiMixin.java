package one.dqu.additionaladditions.mixin;

import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.registry.AAItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Displays elevation action bar message when holding the depth meter.
 */
@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "render", at = @At("TAIL"))
    private void depthMeterMessage(CallbackInfo ci) {
        if (!Config.BAROMETER.get().enabled()) return;
        if (minecraft.player.isHolding(AAItems.BAROMETER.get())) {
            if (Config.BAROMETER.get().displayElevationAlways()) {
                String level = String.valueOf((int) minecraft.player.getY());
                minecraft.player.displayClientMessage(MutableComponent.create(new TranslatableContents("additionaladditions.gui.barometer.elevation", null, new String[]{level})), true);
            }
        }
    }
}
