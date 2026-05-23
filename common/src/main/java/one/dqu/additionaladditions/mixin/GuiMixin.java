package one.dqu.additionaladditions.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.registry.AAItems;
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
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void depthMeterMessage(GuiGraphicsExtractor extractor, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!Config.BAROMETER.get().enabled()) return;
        if (minecraft.player == null) return;
        if (minecraft.player.isHolding(AAItems.BAROMETER.get())) {
            if (Config.BAROMETER.get().displayElevationHud()) {
                String level = String.valueOf((int) minecraft.player.getY());
                minecraft.player.sendOverlayMessage(Component.translatable("additionaladditions.gui.barometer.elevation", level));
            }
        }
    }
}
